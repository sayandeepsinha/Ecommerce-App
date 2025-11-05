package com.example.ecommerce.service;

import com.example.ecommerce.dto.CheckoutSessionResponse;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for Stripe payment integration
 * Handles creating checkout sessions and processing webhooks
 */
@Service
public class StripeService {

    private final OrderRepository orderRepository;
    @SuppressWarnings("unused")
	private final OrderService orderService;

    /**
     * Stripe API key from application.properties
     */
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    /**
     * Webhook secret for validating Stripe webhook events
     */
    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    /**
     * Success URL - where to redirect after successful payment
     */
    @Value("${stripe.success.url}")
    private String successUrl;

    /**
     * Cancel URL - where to redirect if user cancels payment
     */
    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    // Constructor for dependency injection
    public StripeService(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    /**
     * Initialize Stripe API key after bean construction
     * This runs once when the application starts
     */
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    /**
     * Create a Stripe Checkout session for an order
     * Returns a URL that frontend can redirect the user to
     */
    public CheckoutSessionResponse createCheckoutSession(Long orderId, Long userId) {
        // Get the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Verify the order belongs to this user
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Order does not belong to this user");
        }

        // Check if order is already paid
        if ("paid".equals(order.getStatus())) {
            throw new RuntimeException("Order is already paid");
        }

        try {
            // Create line items for Stripe from order items
            List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
            
            for (OrderItem item : order.getItems()) {
                // Convert price to cents (Stripe uses smallest currency unit)
                long priceInCents = item.getPrice().multiply(new java.math.BigDecimal(100)).longValue();
                
                SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                        .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(priceInCents)
                                .setProductData(
                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(item.getProductName())
                                        .build()
                                )
                                .build()
                        )
                        .setQuantity((long) item.getQuantity())
                        .build();
                
                lineItems.add(lineItem);
            }

            // Create the checkout session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl + "/" + orderId)
                    .setCancelUrl(cancelUrl)
                    .addAllLineItem(lineItems)
                    // Store order ID in metadata so we can update it in webhook
                    .putMetadata("orderId", orderId.toString())
                    .build();

            Session session = Session.create(params);

            return CheckoutSessionResponse.builder()
                    .sessionId(session.getId())
                    .checkoutUrl(session.getUrl())
                    .build();

        } catch (StripeException e) {
            throw new RuntimeException("Failed to create checkout session: " + e.getMessage());
        }
    }

    /**
     * Handle Stripe webhook events
     * Called when payment is completed, failed, etc.
     */
    public void handleWebhook(String payload, String sigHeader) {
        Event event;

        try {
            // Verify the webhook signature to ensure it's from Stripe
            event = com.stripe.net.Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            throw new RuntimeException("Webhook signature verification failed: " + e.getMessage());
        }

        // Handle the event based on type
        switch (event.getType()) {
            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event);
                break;
            case "payment_intent.succeeded":
                // Payment succeeded
                System.out.println("Payment succeeded: " + event.getId());
                break;
            case "payment_intent.payment_failed":
                // Payment failed
                System.out.println("Payment failed: " + event.getId());
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }
    }

    /**
     * Handle successful checkout session completion
     * Updates the order status to "paid"
     */
    @Transactional
    private void handleCheckoutSessionCompleted(Event event) {
        System.out.println("=== WEBHOOK: checkout.session.completed received ===");
        
        try {
            // Cast the StripeObject to Session and get the ID
            @SuppressWarnings("deprecation")
            StripeObject stripeObject = event.getData().getObject();
            Session sessionFromEvent = (Session) stripeObject;
            String sessionId = sessionFromEvent.getId();
            
            // Fetch full session from Stripe API to ensure we have all data including metadata
            Session session = Session.retrieve(sessionId);
            
            System.out.println("Session ID: " + sessionId);
            System.out.println("Session Metadata: " + session.getMetadata());

            // Get order ID from metadata
            String orderIdStr = session.getMetadata().get("orderId");
            if (orderIdStr == null || orderIdStr.isEmpty()) {
                System.err.println("ERROR: No orderId in session metadata");
                return;
            }

            System.out.println("Order ID from metadata: " + orderIdStr);
            Long orderId = Long.parseLong(orderIdStr);
            
            System.out.println("Looking for order with ID: " + orderId);
            
            // Update order status to paid
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            
            System.out.println("Order found. Current status: " + order.getStatus());
            
            order.setStatus("paid");
            Order savedOrder = orderRepository.save(order);
            
            System.out.println("✓ Order #" + orderId + " marked as PAID. New status: " + savedOrder.getStatus());
            
        } catch (Exception e) {
            System.err.println("ERROR updating order: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
