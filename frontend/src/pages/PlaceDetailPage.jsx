import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { fetchPlace } from '../api/placeApi'
import { fetchPostsForPlace } from '../api/postApi'
import ErrorMessage from '../components/common/ErrorMessage'
import LoadingSpinner from '../components/common/LoadingSpinner'
import PostCard from '../components/post/PostCard'
import RatingDisplay from '../components/ratings/RatingDisplay'
import SaveToTripPlanButton from '../components/tripplan/SaveToTripPlanButton'
import { useAuth } from '../auth/useAuth'
import './PlaceDetailPage.css'

export default function PlaceDetailPage() {
  const { id } = useParams()
  const { isAuthenticated } = useAuth()
  const [place, setPlace] = useState(null)
  const [posts, setPosts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    setLoading(true)
    Promise.all([fetchPlace(id), fetchPostsForPlace(id)])
      .then(([placeData, postsData]) => {
        setPlace(placeData)
        setPosts(postsData)
      })
      .catch(() => setError('Could not load this place'))
      .finally(() => setLoading(false))
  }, [id])

  if (loading) return <LoadingSpinner />
  if (error) return <ErrorMessage>{error}</ErrorMessage>
  if (!place) return null

  return (
    <div className="place-detail">
      <h1>{place.name}</h1>
      <p className="place-detail-meta">
        {place.category} {place.city && `· ${place.city}`} {place.country && `· ${place.country}`}
      </p>
      {place.description && <p>{place.description}</p>}

      {posts.length === 0 ? (
        <p>No reviews yet — be the first to share your experience.</p>
      ) : (
        place.ratingSummary.count > 0 && <RatingDisplay ratings={place.ratingSummary} />
      )}

      {isAuthenticated && (
        <div className="place-detail-actions">
          <SaveToTripPlanButton placeId={id} />
          <Link to={`/places/${id}/review/new`}>Write a review</Link>
        </div>
      )}

      <h2>Reviews</h2>
      {posts.length === 0 && <p>No reviews yet.</p>}
      {posts.map((post) => (
        <PostCard key={post.id} post={post} />
      ))}
    </div>
  )
}
