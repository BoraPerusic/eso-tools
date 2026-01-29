<script setup lang="ts">
import BaseCard from '../components/ui/BaseCard.vue'
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold text-surface-900">Dashboard</h1>
    </div>

    <div class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <BaseCard>
        <h3 class="text-lg font-medium text-surface-900">Total Orders</h3>
        <p class="mt-2 text-3xl font-bold text-primary-600">1,248</p>
      </BaseCard>
      <BaseCard>
        <h3 class="text-lg font-medium text-surface-900">Pending Returns</h3>
        <p class="mt-2 text-3xl font-bold text-primary-600">54</p>
      </BaseCard>
      <BaseCard>
        <h3 class="text-lg font-medium text-surface-900">Stock Alerts</h3>
        <p class="mt-2 text-3xl font-bold text-red-600">3</p>
      </BaseCard>
    </div>
  </div>
</template>
