import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { AuthProvider } from './auth/AuthContext'
import ProtectedRoute from './auth/ProtectedRoute'
import Layout from './components/layout/Layout'
import CreatePlacePage from './pages/CreatePlacePage'
import CreatePostPage from './pages/CreatePostPage'
import DashboardPage from './pages/DashboardPage'
import LoginPage from './pages/LoginPage'
import NotFoundPage from './pages/NotFoundPage'
import PlaceDetailPage from './pages/PlaceDetailPage'
import ProfilePage from './pages/ProfilePage'
import SignupPage from './pages/SignupPage'
import TripPlanPage from './pages/TripPlanPage'

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<Layout />}>
            <Route index element={<DashboardPage />} />
            <Route path="login" element={<LoginPage />} />
            <Route path="signup" element={<SignupPage />} />
            <Route path="places/:id" element={<PlaceDetailPage />} />
            <Route element={<ProtectedRoute />}>
              <Route path="places/new" element={<CreatePlacePage />} />
              <Route path="places/:id/review/new" element={<CreatePostPage />} />
              <Route path="trip-plan" element={<TripPlanPage />} />
              <Route path="profile" element={<ProfilePage />} />
            </Route>
            <Route path="*" element={<NotFoundPage />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
