<script setup lang="ts">
import { ref, onMounted } from 'vue'
import BaseTable from '../components/ui/BaseTable.vue'
import StatusBadge from '../components/ui/StatusBadge.vue'
import { orderService, type Order } from '../services/orderService'

const orders = ref<Order[]>([])
const loading = ref(true)

const columns = [
  { key: 'orderNumber', label: 'Order #' },
  { key: 'customer', label: 'Customer' },
  { key: 'date', label: 'Date' },
  { key: 'amount', label: 'Amount' },
  { key: 'status', label: 'Status' },
]

onMounted(async () => {
  try {
    orders.value = await orderService.getOrders()
  } finally {
    loading.value = false
  }
})

const formatCurrency = (value: number) => {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value)
}
</script>

<template>
  <div class="space-y-6">
    <h1 class="text-2xl font-bold text-surface-900">Orders</h1>
    <BaseTable :columns="columns" :data="orders" :loading="loading">
      <template #amount="{ value }">
        <span class="font-medium font-mono">{{ formatCurrency(value) }}</span>
      </template>
      <template #status="{ value }">
        <StatusBadge :status="value" />
      </template>
    </BaseTable>
  </div>
</template>
