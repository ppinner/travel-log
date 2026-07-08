import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { createPlace } from '../api/placeApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LocationPicker from '../components/map/LocationPicker'
import './CreatePlacePage.css'

const CATEGORIES = ['HOSTEL', 'TOUR', 'ACTIVITY', 'RESTAURANT', 'ATTRACTION', 'TRANSPORT', 'OTHER']

export default function CreatePlacePage() {
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [description, setDescription] = useState('')
  const [category, setCategory] = useState(CATEGORIES[0])
  const [country, setCountry] = useState('')
  const [city, setCity] = useState('')
  const [location, setLocation] = useState(null)
  const [error, setError] = useState(null)
  const [submitting, setSubmitting] = useState(false)

  async function handleSubmit(e) {
    e.preventDefault()
    if (!location) {
      setError('Pick a location on the map first')
      return
    }
    setError(null)
    setSubmitting(true)
    try {
      const place = await createPlace({
        name,
        description,
        category,
        country,
        city,
        lat: location.lat,
        lng: location.lng,
      })
      navigate(`/places/${place.id}`)
    } catch (err) {
      setError(err.response?.data?.message ?? 'Could not create place')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <form className="create-place-form" onSubmit={handleSubmit}>
      <h1>Add a place</h1>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      <label>
        Name
        <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
      </label>
      <label>
        Description
        <textarea value={description} onChange={(e) => setDescription(e.target.value)} rows={3} />
      </label>
      <label>
        Category
        <select value={category} onChange={(e) => setCategory(e.target.value)}>
          {CATEGORIES.map((c) => (
            <option key={c} value={c}>
              {c}
            </option>
          ))}
        </select>
      </label>
      <div className="create-place-row">
        <label>
          Country
          <input type="text" value={country} onChange={(e) => setCountry(e.target.value)} />
        </label>
        <label>
          City
          <input type="text" value={city} onChange={(e) => setCity(e.target.value)} />
        </label>
      </div>
      <LocationPicker value={location} onChange={setLocation} />
      <button type="submit" disabled={submitting}>
        {submitting ? 'Saving...' : 'Save place'}
      </button>
    </form>
  )
}
