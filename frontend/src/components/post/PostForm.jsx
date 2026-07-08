import { useState } from 'react'
import ErrorMessage from '../common/ErrorMessage'
import RatingInput from '../ratings/RatingInput'
import TourGuideContactForm from './TourGuideContactForm'
import './PostForm.css'

const RATING_FIELDS = [
  ['cost', 'Cost (1 = pricey, 5 = great value)'],
  ['authenticity', 'Authenticity'],
  ['enjoyment', 'Enjoyment'],
  ['fitnessRequired', 'Fitness required'],
]

export default function PostForm({ onSubmit, submitting, error }) {
  const [title, setTitle] = useState('')
  const [body, setBody] = useState('')
  const [visitDate, setVisitDate] = useState('')
  const [ratings, setRatings] = useState({ cost: 3, authenticity: 3, enjoyment: 3, fitnessRequired: 3 })
  const [tourGuide, setTourGuide] = useState(null)

  function handleSubmit(e) {
    e.preventDefault()
    onSubmit({
      title,
      body,
      ratings,
      tourGuide,
      visitDate: visitDate ? new Date(visitDate).toISOString() : null,
    })
  }

  return (
    <form className="post-form" onSubmit={handleSubmit}>
      {error && <ErrorMessage>{error}</ErrorMessage>}
      <label>
        Title
        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
      </label>
      <label>
        Your experience
        <textarea value={body} onChange={(e) => setBody(e.target.value)} rows={4} required />
      </label>
      <div className="post-form-ratings">
        {RATING_FIELDS.map(([key, label]) => (
          <RatingInput
            key={key}
            label={label}
            value={ratings[key]}
            onChange={(value) => setRatings((prev) => ({ ...prev, [key]: value }))}
          />
        ))}
      </div>
      <TourGuideContactForm onChange={setTourGuide} />
      <label>
        Visit date (optional)
        <input type="date" value={visitDate} onChange={(e) => setVisitDate(e.target.value)} />
      </label>
      <button type="submit" disabled={submitting}>
        {submitting ? 'Posting...' : 'Post review'}
      </button>
    </form>
  )
}
