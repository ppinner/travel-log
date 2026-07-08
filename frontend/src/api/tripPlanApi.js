import client from './client'

export function fetchTripPlan(status) {
  return client.get('/tripplan', { params: status ? { status } : {} }).then((res) => res.data)
}

export function savePlaceToTripPlan(payload) {
  return client.post('/tripplan', payload).then((res) => res.data)
}

export function updateSavedPlace(id, payload) {
  return client.put(`/tripplan/${id}`, payload).then((res) => res.data)
}

export function removeSavedPlace(id) {
  return client.delete(`/tripplan/${id}`)
}
