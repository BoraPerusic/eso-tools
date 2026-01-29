<script setup lang="ts">
import SideNavigation from './SideNavigation.vue'
</script>

<template>
  <div class="flex h-screen overflow-hidden bg-surface-50">
    <!-- Sidebar -->
    <SideNavigation />

    <!-- Main Content -->
    <div class="flex flex-col flex-1 min-w-0 overflow-hidden">
      <!-- Top header area (optional, good for breadcrumbs or mobile toggle) -->
      
      <!-- Main Scrollable Area -->
      <main class="flex-1 overflow-y-auto focus:outline-none scroll-smooth">
        <div class="py-6">
          <div class="mx-auto max-w-7xl px-4 sm:px-6 md:px-8">
            <slot />
          </div>
        </div>
      </main>
    </div>
  </div>
</template>
