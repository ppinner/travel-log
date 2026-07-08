import { useEffect, useState } from 'react'
import { fetchCurrentUser, login as loginRequest, signup as signupRequest } from '../api/authApi'
import { AuthContext } from './context'

const TOKEN_KEY = 'travelLogToken'

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(() => localStorage.getItem(TOKEN_KEY))
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    if (!token) {
      setLoading(false)
      return
    }
    fetchCurrentUser()
      .then(setUser)
      .catch(() => {
        localStorage.removeItem(TOKEN_KEY)
        setToken(null)
        setUser(null)
      })
      .finally(() => setLoading(false))
  }, [token])

  async function login(credentials) {
    const data = await loginRequest(credentials)
    localStorage.setItem(TOKEN_KEY, data.token)
    setToken(data.token)
    setUser(data.user)
  }

  async function signup(details) {
    const data = await signupRequest(details)
    localStorage.setItem(TOKEN_KEY, data.token)
    setToken(data.token)
    setUser(data.user)
  }

  function logout() {
    localStorage.removeItem(TOKEN_KEY)
    setToken(null)
    setUser(null)
  }

  const value = { user, token, loading, isAuthenticated: Boolean(token), login, signup, logout }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
