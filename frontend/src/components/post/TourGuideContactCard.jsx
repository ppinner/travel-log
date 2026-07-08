import './TourGuideContactCard.css'

const METHOD_LABELS = {
  PHONE: 'Phone',
  WHATSAPP: 'WhatsApp',
  EMAIL: 'Email',
  OTHER: 'Contact',
}

export default function TourGuideContactCard({ tourGuide }) {
  if (!tourGuide) return null

  return (
    <div className="tour-guide-card">
      <p className="tour-guide-card-title">Recommended local guide</p>
      <p>
        <strong>{tourGuide.name}</strong> — {METHOD_LABELS[tourGuide.contactMethod] ?? tourGuide.contactMethod}:{' '}
        {tourGuide.contactValue}
      </p>
      {tourGuide.note && <p className="tour-guide-card-note">{tourGuide.note}</p>}
    </div>
  )
}
