import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', () => {
    const user = ref<{ name: string; role: string } | null>(null)

    // Initialize with dummy user for dev speed
    user.value = { name: 'John Doe', role: 'admin' }

    const isAuthenticated = computed(() => !!user.value)

    function login() {
        user.value = { name: 'John Doe', role: 'admin' }
    }

    function logout() {
        user.value = null
    }

    return { user, isAuthenticated, login, logout }
})
