import AdminSidebar from '../../components/adminsidebar'

export default function AdminLayout({ children }) {
  return (
    <div className="flex">
      <AdminSidebar />
      <div className="flex-grow p-4">{children}</div>
    </div>
  )
}
