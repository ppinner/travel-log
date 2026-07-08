import './RatingInput.css'

export default function RatingInput({ label, value, onChange }) {
  return (
    <div className="rating-input">
      <span className="rating-input-label">{label}</span>
      <div className="rating-input-scale" role="radiogroup" aria-label={label}>
        {[1, 2, 3, 4, 5].map((score) => (
          <button
            key={score}
            type="button"
            role="radio"
            aria-checked={value === score}
            className={score === value ? 'selected' : score < value ? 'filled' : ''}
            onClick={() => onChange(score)}
          >
            {score}
          </button>
        ))}
      </div>
    </div>
  )
}
