import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../../auth/useAuth'
import './NavBar.css'

export default function NavBar() {
  const { isAuthenticated, logout } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/')
  }

  return (
    <nav className="navbar" aria-label="Primary">
      <Link to="/" className="navbar-brand">
        Travel Log
      </Link>
      <div className="navbar-links">
        {isAuthenticated ? (
          <>
            <Link to="/trip-plan">Trip Plan</Link>
            <Link to="/profile">Profile</Link>
            <button type="button" onClick={handleLogout}>
              Log out
            </button>
          </>
        ) : (
          <>
            <Link to="/login">Log in</Link>
            <Link to="/signup">Sign up</Link>
          </>
        )}
      </div>
    </nav>
  )
}
