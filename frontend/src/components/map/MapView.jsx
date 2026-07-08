import 'leaflet/dist/leaflet.css'
import 'leaflet.markercluster/dist/MarkerCluster.css'
import 'leaflet.markercluster/dist/MarkerCluster.Default.css'
import './leafletIconFix'
import { useEffect, useState } from 'react'
import { MapContainer, TileLayer, useMapEvents } from 'react-leaflet'
import MarkerClusterGroup from 'react-leaflet-cluster'
import { fetchPlacesInBounds } from '../../api/placeApi'
import { useDebounce } from '../../hooks/useDebounce'
import { boundsToQuery } from '../../utils/boundsToQuery'
import PlaceMarker from './PlaceMarker'
import './MapView.css'

function ViewportWatcher({ onBoundsChange }) {
  const map = useMapEvents({
    moveend: () => onBoundsChange(map.getBounds()),
  })

  useEffect(() => {
    onBoundsChange(map.getBounds())
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return null
}

export default function MapView() {
  const [bounds, setBounds] = useState(null)
  const [places, setPlaces] = useState([])
  const [error, setError] = useState(null)
  const debouncedBounds = useDebounce(bounds, 400)

  useEffect(() => {
    if (!debouncedBounds) return
    fetchPlacesInBounds(boundsToQuery(debouncedBounds))
      .then((data) => {
        setPlaces(data)
        setError(null)
      })
      .catch(() => setError('Could not load places for this area'))
  }, [debouncedBounds])

  return (
    <div className="map-view">
      {error && (
        <p className="map-view-error" role="alert">
          {error}
        </p>
      )}
      <MapContainer center={[20, 0]} zoom={3} scrollWheelZoom>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="&copy; OpenStreetMap contributors"
        />
        <ViewportWatcher onBoundsChange={setBounds} />
        <MarkerClusterGroup chunkedLoading>
          {places.map((place) => (
            <PlaceMarker key={place.id} place={place} />
          ))}
        </MarkerClusterGroup>
      </MapContainer>
    </div>
  )
}
