export function boundsToQuery(bounds) {
  const sw = bounds.getSouthWest()
  const ne = bounds.getNorthEast()
  return {
    swLat: sw.lat,
    swLng: sw.lng,
    neLat: ne.lat,
    neLng: ne.lng,
  }
}
