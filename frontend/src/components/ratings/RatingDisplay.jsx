import './RatingDisplay.css'

const LABELS = [
  ['cost', 'Cost'],
  ['authenticity', 'Authenticity'],
  ['enjoyment', 'Enjoyment'],
  ['fitnessRequired', 'Fitness'],
]

export default function RatingDisplay({ ratings }) {
  if (!ratings) return null

  return (
    <dl className="rating-display">
      {LABELS.map(([key, label]) => (
        <div key={key} className="rating-display-item">
          <dt>{label}</dt>
          <dd>{formatValue(ratings, key)}</dd>
        </div>
      ))}
    </dl>
  )
}

function formatValue(ratings, key) {
  const avgKey = `avg${key.charAt(0).toUpperCase()}${key.slice(1)}`
  const value = ratings[avgKey] ?? ratings[key]
  return typeof value === 'number' ? value.toFixed(1) : value
}
