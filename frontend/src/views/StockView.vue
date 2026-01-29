<script setup lang="ts">
import { ref, onMounted } from 'vue'
import BaseTable from '../components/ui/BaseTable.vue'
import StatusBadge from '../components/ui/StatusBadge.vue'
import { stockService, type ProductStock } from '../services/stockService'

const items = ref<ProductStock[]>([])
const loading = ref(true)

const columns = [
  { key: 'name', label: 'Product Name', class: 'w-1/3' },
  { key: 'sku', label: 'SKU' },
  { key: 'quantity', label: 'Stock Level' },
  { key: 'status', label: 'Status' },
  { key: 'restockDate', label: 'Est. Restock' },
]

onMounted(async () => {
  try {
    items.value = await stockService.getStock()
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="space-y-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold text-surface-900">Product Stock</h1>
    </div>

    <BaseTable :columns="columns" :data="items" :loading="loading">
      <template #status="{ value }">
        <StatusBadge :status="value" />
      </template>
      <template #restockDate="{ value }">
        <span v-if="value" class="text-surface-500">{{ value }}</span>
        <span v-else class="text-surface-400 italic">-</span>
      </template>
    </BaseTable>
  </div>
</template>
