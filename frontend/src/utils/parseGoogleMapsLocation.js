const PATTERNS = [
  /!3d(-?\d+(?:\.\d+)?)!4d(-?\d+(?:\.\d+)?)/, // precise pin dropped on a Google Maps place
  /@(-?\d+(?:\.\d+)?),(-?\d+(?:\.\d+)?)/, // map center in a Google Maps URL
  /[?&]q=(-?\d+(?:\.\d+)?),(-?\d+(?:\.\d+)?)/, // ?q=lat,lng share links
  /^\s*(-?\d+(?:\.\d+)?)\s*,\s*(-?\d+(?:\.\d+)?)\s*$/, // raw "lat, lng" text
]

export function parseGoogleMapsLocation(input) {
  if (!input) return null

  for (const pattern of PATTERNS) {
    const match = input.match(pattern)
    if (match) {
      const lat = Number(match[1])
      const lng = Number(match[2])
      if (isValidLatLng(lat, lng)) {
        return { lat, lng }
      }
    }
  }

  return null
}

function isValidLatLng(lat, lng) {
  return Number.isFinite(lat) && Number.isFinite(lng) && lat >= -90 && lat <= 90 && lng >= -180 && lng <= 180
}
