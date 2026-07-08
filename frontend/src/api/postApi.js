import client from './client'

export function fetchPostsForPlace(placeId) {
  return client.get(`/places/${placeId}/posts`).then((res) => res.data)
}

export function createPost(post) {
  return client.post('/posts', post).then((res) => res.data)
}
