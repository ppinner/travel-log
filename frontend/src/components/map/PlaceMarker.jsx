import { Marker, Popup } from 'react-leaflet'
import { Link } from 'react-router-dom'
import RatingDisplay from '../ratings/RatingDisplay'

export default function PlaceMarker({ place }) {
  return (
    <Marker position={[place.lat, place.lng]}>
      <Popup>
        <strong>{place.name}</strong>
        <p>{place.category}</p>
        {place.ratingSummary?.count > 0 ? (
          <RatingDisplay ratings={place.ratingSummary} />
        ) : (
          <p>No reviews yet</p>
        )}
        <Link to={`/places/${place.id}`}>View place</Link>
      </Popup>
    </Marker>
  )
}
