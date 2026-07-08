import { useAuth } from '../auth/useAuth'

export default function ProfilePage() {
  const { user } = useAuth()

  return (
    <div>
      <h1>Profile</h1>
      <p>Display name: {user?.displayName}</p>
      <p>Email: {user?.email}</p>
    </div>
  )
}
