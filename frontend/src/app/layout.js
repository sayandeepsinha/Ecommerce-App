import '../styles/globals.css'
import Navbar from '../components/navbar'
import Footer from '../components/footer'

export const metadata = {
  title: 'E-commerce App',
  description: 'Minimal e-commerce frontend',
}

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body className="min-h-screen flex flex-col">
        <Navbar />
        <main className="flex-grow p-4 max-w-5xl mx-auto">{children}</main>
        <Footer />
      </body>
    </html>
  )
}
