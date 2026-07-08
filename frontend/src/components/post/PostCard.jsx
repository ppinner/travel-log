import RatingDisplay from '../ratings/RatingDisplay'
import TourGuideContactCard from './TourGuideContactCard'
import './PostCard.css'

export default function PostCard({ post }) {
  return (
    <article className="post-card">
      {post.title && <h3>{post.title}</h3>}
      <p>{post.body}</p>
      <RatingDisplay ratings={post.ratings} />
      {post.visitDate && (
        <p className="post-card-visit-date">Visited {new Date(post.visitDate).toLocaleDateString()}</p>
      )}
      <TourGuideContactCard tourGuide={post.tourGuide} />
    </article>
  )
}
