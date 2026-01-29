<script setup lang="ts">
import { ref, onMounted } from 'vue'
import BaseTable from '../components/ui/BaseTable.vue'
import StatusBadge from '../components/ui/StatusBadge.vue'
import { returnService, type ReturnRequest } from '../services/returnService'

const returns = ref<ReturnRequest[]>([])
const loading = ref(true)

const columns = [
  { key: 'returnNumber', label: 'Return #' },
  { key: 'orderNumber', label: 'Original Order' },
  { key: 'customer', label: 'Customer' },
  { key: 'date', label: 'Date' },
  { key: 'reason', label: 'Reason' },
  { key: 'status', label: 'Status' },
]

onMounted(async () => {
  try {
    returns.value = await returnService.getReturns()
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="space-y-6">
    <h1 class="text-2xl font-bold text-surface-900">Returns</h1>
    <BaseTable :columns="columns" :data="returns" :loading="loading">
      <template #status="{ value }">
        <StatusBadge :status="value" />
      </template>
    </BaseTable>
  </div>
</template>
