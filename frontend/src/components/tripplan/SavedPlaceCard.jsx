import { Link } from 'react-router-dom'
import './SavedPlaceCard.css'

export default function SavedPlaceCard({ savedPlace }) {
  const { place } = savedPlace

  return (
    <Link to={`/places/${place.id}`} className="saved-place-card">
      <div>
        <h3>{place.name}</h3>
        <p className="saved-place-card-meta">{place.category}</p>
        {savedPlace.notes && <p className="saved-place-card-notes">{savedPlace.notes}</p>}
      </div>
    </Link>
  )
}
