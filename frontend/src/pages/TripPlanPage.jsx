import { useEffect, useState } from 'react'
import { fetchTripPlan } from '../api/tripPlanApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import SavedPlaceCard from '../components/tripplan/SavedPlaceCard'
import './TripPlanPage.css'

export default function TripPlanPage() {
  const [savedPlaces, setSavedPlaces] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchTripPlan()
      .then(setSavedPlaces)
      .catch(() => setError('Could not load your trip plan'))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <LoadingSpinner />
  if (error) return <ErrorMessage>{error}</ErrorMessage>

  const wantToGo = savedPlaces.filter((entry) => entry.status === 'WANT_TO_GO')
  const visited = savedPlaces.filter((entry) => entry.status === 'VISITED')

  return (
    <div className="trip-plan-page">
      <h1>Trip Plan</h1>

      <h2>Want to go</h2>
      {wantToGo.length === 0 && <p>Nothing saved yet — explore the map and save places you want to visit.</p>}
      {wantToGo.map((entry) => (
        <SavedPlaceCard key={entry.id} savedPlace={entry} />
      ))}

      <h2>Visited</h2>
      {visited.length === 0 && <p>No visited places yet.</p>}
      {visited.map((entry) => (
        <SavedPlaceCard key={entry.id} savedPlace={entry} />
      ))}
    </div>
  )
}
