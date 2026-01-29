<script setup lang="ts">
import { useRoute } from 'vue-router'
import { 
  HomeIcon, 
  CubeIcon, 
  ShoppingCartIcon, 
  ArrowPathIcon, 
  ChatBubbleLeftRightIcon 
} from '@heroicons/vue/24/outline'

const route = useRoute()

const navigation = [
  { name: 'Dashboard', href: '/', icon: HomeIcon },
  { name: 'Stock', href: '/stock', icon: CubeIcon },
  { name: 'Orders', href: '/orders', icon: ShoppingCartIcon },
  { name: 'Returns', href: '/returns', icon: ArrowPathIcon },
  { name: 'Agent', href: '/agent', icon: ChatBubbleLeftRightIcon },
]
</script>

<template>
  <div class="flex h-full w-64 flex-col bg-surface-900 text-white shadow-xl">
    <!-- Logo -->
    <div class="flex h-16 items-center px-6 border-b border-surface-800">
      <div class="flex items-center gap-3">
        <div class="h-8 w-8 rounded-lg bg-primary-600 flex items-center justify-center">
          <CubeIcon class="h-5 w-5 text-white" />
        </div>
        <span class="text-lg font-bold tracking-tight">ESO Tools</span>
      </div>
    </div>

    <!-- Navigation -->
    <nav class="flex-1 px-3 py-6 space-y-1">
      <router-link
        v-for="item in navigation"
        :key="item.name"
        :to="item.href"
        class="group flex items-center px-3 py-2.5 text-sm font-medium rounded-lg transition-all duration-200"
        :class="[
          route.path === item.href
            ? 'bg-primary-600 text-white shadow-lg shadow-primary-900/20'
            : 'text-surface-400 hover:bg-surface-800 hover:text-white'
        ]"
      >
        <component
          :is="item.icon"
          class="mr-3 h-5 w-5 flex-shrink-0 transition-colors"
          :class="[
            route.path === item.href ? 'text-white' : 'text-surface-500 group-hover:text-white'
          ]"
          aria-hidden="true"
        />
        {{ item.name }}
      </router-link>
    </nav>

    <!-- User Profile / Footer placeholder -->
    <div class="border-t border-surface-800 p-4">
      <div class="flex items-center">
        <div class="h-8 w-8 rounded-full bg-surface-700 flex items-center justify-center">
          <span class="text-xs font-bold text-white">JD</span>
        </div>
        <div class="ml-3">
          <p class="text-sm font-medium text-white">John Doe</p>
          <p class="text-xs text-surface-500">View Profile</p>
        </div>
      </div>
    </div>
  </div>
</template>
