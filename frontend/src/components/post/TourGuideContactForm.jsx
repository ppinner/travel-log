import { useState } from 'react'
import './TourGuideContactForm.css'

const CONTACT_METHODS = ['PHONE', 'WHATSAPP', 'EMAIL', 'OTHER']

export default function TourGuideContactForm({ onChange }) {
  const [enabled, setEnabled] = useState(false)
  const [name, setName] = useState('')
  const [contactMethod, setContactMethod] = useState(CONTACT_METHODS[0])
  const [contactValue, setContactValue] = useState('')
  const [note, setNote] = useState('')

  function toggle(checked) {
    setEnabled(checked)
    onChange(checked ? { name, contactMethod, contactValue, note } : null)
  }

  function update(field, value) {
    const next = { name, contactMethod, contactValue, note, [field]: value }
    if (field === 'name') setName(value)
    if (field === 'contactMethod') setContactMethod(value)
    if (field === 'contactValue') setContactValue(value)
    if (field === 'note') setNote(value)
    onChange(next)
  }

  return (
    <div className="tour-guide-form">
      <label className="tour-guide-form-toggle">
        <input type="checkbox" checked={enabled} onChange={(e) => toggle(e.target.checked)} />
        Recommend a local guide
      </label>

      {enabled && (
        <div className="tour-guide-form-fields">
          <label>
            Guide name
            <input type="text" value={name} onChange={(e) => update('name', e.target.value)} required />
          </label>
          <label>
            Contact method
            <select value={contactMethod} onChange={(e) => update('contactMethod', e.target.value)}>
              {CONTACT_METHODS.map((m) => (
                <option key={m} value={m}>
                  {m}
                </option>
              ))}
            </select>
          </label>
          <label>
            Contact info
            <input
              type="text"
              placeholder="Phone, WhatsApp number, or email"
              value={contactValue}
              onChange={(e) => update('contactValue', e.target.value)}
              required
            />
          </label>
          <label>
            Note (optional)
            <input type="text" value={note} onChange={(e) => update('note', e.target.value)} />
          </label>
        </div>
      )}
    </div>
  )
}
