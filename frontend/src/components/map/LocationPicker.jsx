import 'leaflet/dist/leaflet.css'
import './leafletIconFix'
import { useEffect, useState } from 'react'
import { MapContainer, Marker, TileLayer, useMap, useMapEvents } from 'react-leaflet'
import { parseGoogleMapsLocation } from '../../utils/parseGoogleMapsLocation'
import './LocationPicker.css'

function ClickHandler({ onPick }) {
  useMapEvents({
    click: (e) => onPick(e.latlng),
  })
  return null
}

function Recenter({ value }) {
  const map = useMap()

  useEffect(() => {
    if (value) {
      map.setView(value, Math.max(map.getZoom(), 12), { animate: true })
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [value])

  return null
}

export default function LocationPicker({ value, onChange }) {
  const center = value ?? { lat: 20, lng: 0 }
  const [pasteInput, setPasteInput] = useState('')
  const [pasteError, setPasteError] = useState(null)

  function handlePasteSubmit() {
    const parsed = parseGoogleMapsLocation(pasteInput)
    if (!parsed) {
      setPasteError('Could not find a location in that link or text')
      return
    }
    setPasteError(null)
    onChange(parsed)
  }

  function handlePasteKeyDown(e) {
    if (e.key === 'Enter') {
      e.preventDefault()
      handlePasteSubmit()
    }
  }

  return (
    <div className="location-picker">
      <MapContainer center={center} zoom={value ? 12 : 2} scrollWheelZoom>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="&copy; OpenStreetMap contributors"
        />
        <ClickHandler onPick={onChange} />
        <Recenter value={value} />
        {value && <Marker position={value} />}
      </MapContainer>
      <p className="location-picker-hint">Click the map to set this place&rsquo;s location.</p>

      <div className="location-picker-paste">
        <label>
          Or paste a Google Maps link / coordinates
          <input
            type="text"
            placeholder="https://maps.google.com/... or 18.7883, 98.9853"
            value={pasteInput}
            onChange={(e) => setPasteInput(e.target.value)}
            onKeyDown={handlePasteKeyDown}
          />
        </label>
        <button type="button" onClick={handlePasteSubmit}>
          Use this location
        </button>
      </div>
      {pasteError && <p className="location-picker-error">{pasteError}</p>}
    </div>
  )
}
