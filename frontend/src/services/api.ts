import axios from 'axios'

// Create axios instance with base URL
// In dev, this might target localhost:8080 or a mock server
export const apiClient = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL || '/api/v1',
    headers: {
        'Content-Type': 'application/json',
    },
})

// Add interceptors for auth token (placeholder for now)
apiClient.interceptors.request.use(config => {
    // TODO: Get token from Auth Store
    const token = localStorage.getItem('token') // Mock
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})
