<script setup lang="ts">
import { ref, onMounted } from 'vue'
import BaseCard from '../components/ui/BaseCard.vue'
import { stockService } from '../services/stockService'
import { orderService } from '../services/orderService'
import { returnService } from '../services/returnService'

const stats = ref({
  totalOrders: 0,
  pendingReturns: 0,
  stockAlerts: 0,
  loading: true
})

onMounted(async () => {
  try {
    const [stock, orders, returns] = await Promise.all([
      stockService.getStock(),
      orderService.getOrders(),
      returnService.getReturns()
    ])

    stats.value = {
      stockAlerts: stock.filter(i => i.status !== 'In Stock').length,
      totalOrders: orders.length,
      pendingReturns: returns.filter(r => r.status === 'Pending').length,
      loading: false
    }
  } catch (e) {
    stats.value.loading = false
  }
})
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold text-surface-900">Dashboard</h1>
    </div>

    <div v-if="stats.loading" class="animate-pulse space-y-4">
      <div class="h-24 bg-surface-200 rounded-xl"></div>
    </div>

    <div v-else class="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
      <BaseCard>
        <h3 class="text-lg font-medium text-surface-900">Total Orders</h3>
        <p class="mt-2 text-3xl font-bold text-primary-600">{{ stats.totalOrders }}</p>
      </BaseCard>
      <BaseCard>
        <h3 class="text-lg font-medium text-surface-900">Pending Returns</h3>
        <p class="mt-2 text-3xl font-bold text-primary-600">{{ stats.pendingReturns }}</p>
      </BaseCard>
      <BaseCard>
        <h3 class="text-lg font-medium text-surface-900">Stock Alerts</h3>
        <p class="mt-2 text-3xl font-bold text-red-600">{{ stats.stockAlerts }}</p>
      </BaseCard>
    </div>
  </div>
</template>
