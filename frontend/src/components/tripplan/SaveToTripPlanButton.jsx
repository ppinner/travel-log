import { useEffect, useState } from 'react'
import { fetchTripPlan, removeSavedPlace, savePlaceToTripPlan, updateSavedPlace } from '../../api/tripPlanApi'
import './SaveToTripPlanButton.css'

export default function SaveToTripPlanButton({ placeId }) {
  const [savedEntry, setSavedEntry] = useState(null)
  const [loading, setLoading] = useState(true)
  const [busy, setBusy] = useState(false)

  useEffect(() => {
    fetchTripPlan()
      .then((entries) => setSavedEntry(entries.find((entry) => entry.place.id === placeId) ?? null))
      .finally(() => setLoading(false))
  }, [placeId])

  async function handleSave() {
    setBusy(true)
    try {
      const entry = await savePlaceToTripPlan({ placeId, status: 'WANT_TO_GO' })
      setSavedEntry(entry)
    } finally {
      setBusy(false)
    }
  }

  async function handleMarkVisited() {
    setBusy(true)
    try {
      const entry = await updateSavedPlace(savedEntry.id, { status: 'VISITED' })
      setSavedEntry(entry)
    } finally {
      setBusy(false)
    }
  }

  async function handleRemove() {
    setBusy(true)
    try {
      await removeSavedPlace(savedEntry.id)
      setSavedEntry(null)
    } finally {
      setBusy(false)
    }
  }

  if (loading) return null

  if (!savedEntry) {
    return (
      <button type="button" className="trip-plan-button" onClick={handleSave} disabled={busy}>
        Save to trip plan
      </button>
    )
  }

  if (savedEntry.status === 'WANT_TO_GO') {
    return (
      <div className="trip-plan-status">
        <span className="trip-plan-badge">Want to go</span>
        <button type="button" className="trip-plan-button" onClick={handleMarkVisited} disabled={busy}>
          Mark as visited
        </button>
        <button type="button" className="trip-plan-button-text" onClick={handleRemove} disabled={busy}>
          Remove
        </button>
      </div>
    )
  }

  return (
    <div className="trip-plan-status">
      <span className="trip-plan-badge trip-plan-badge-visited">Visited</span>
      <button type="button" className="trip-plan-button-text" onClick={handleRemove} disabled={busy}>
        Remove
      </button>
    </div>
  )
}
