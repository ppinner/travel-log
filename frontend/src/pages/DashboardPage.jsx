import { Link } from 'react-router-dom'
import MapView from '../components/map/MapView'
import { useAuth } from '../auth/useAuth'
import './DashboardPage.css'

export default function DashboardPage() {
  const { isAuthenticated } = useAuth()

  return (
    <div className="dashboard-page">
      <div className="dashboard-header">
        <h1>Explore</h1>
        {isAuthenticated && <Link to="/places/new">Add a place</Link>}
      </div>
      <MapView />
    </div>
  )
}
