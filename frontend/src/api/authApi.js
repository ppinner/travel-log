import client from './client'

export function signup({ email, password, displayName }) {
  return client.post('/auth/signup', { email, password, displayName }).then((res) => res.data)
}

export function login({ email, password }) {
  return client.post('/auth/login', { email, password }).then((res) => res.data)
}

export function fetchCurrentUser() {
  return client.get('/users/me').then((res) => res.data)
}
