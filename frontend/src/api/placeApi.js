import client from './client'

export function fetchPlacesInBounds(bounds) {
  return client.get('/places', { params: bounds }).then((res) => res.data)
}

export function fetchPlace(id) {
  return client.get(`/places/${id}`).then((res) => res.data)
}

export function createPlace(place) {
  return client.post('/places', place).then((res) => res.data)
}
