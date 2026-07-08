import './LoadingSpinner.css'

export default function LoadingSpinner({ label = 'Loading...' }) {
  return (
    <p className="loading-spinner" role="status">
      <span className="loading-spinner-icon" aria-hidden="true" />
      {label}
    </p>
  )
}
