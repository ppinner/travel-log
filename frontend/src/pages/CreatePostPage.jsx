import { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createPost } from '../api/postApi'
import PostForm from '../components/post/PostForm'

export default function CreatePostPage() {
  const { id: placeId } = useParams()
  const navigate = useNavigate()
  const [submitting, setSubmitting] = useState(false)
  const [error, setError] = useState(null)

  async function handleSubmit(values) {
    setSubmitting(true)
    setError(null)
    try {
      await createPost({ placeId, ...values })
      navigate(`/places/${placeId}`)
    } catch (err) {
      setError(err.response?.data?.message ?? 'Could not post your review')
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div>
      <h1>Write a review</h1>
      <PostForm onSubmit={handleSubmit} submitting={submitting} error={error} />
    </div>
  )
}
