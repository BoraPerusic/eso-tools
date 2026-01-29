<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  status: string
  type?: 'success' | 'warning' | 'danger' | 'info' | 'neutral'
}

const props = withDefaults(defineProps<Props>(), {
  type: 'neutral',
})

const classes = computed(() => {
  const base = 'inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-medium'
  
  // Dynamic mapping if type not explicitly provided
  let inferredType = props.type
  if (props.type === 'neutral') {
    const s = props.status.toLowerCase()
    if (s === 'completed' || s === 'in stock' || s === 'approved') inferredType = 'success'
    else if (s === 'pending' || s === 'processing' || s === 'low stock') inferredType = 'warning'
    else if (s === 'cancelled' || s === 'rejected' || s === 'out of stock') inferredType = 'danger'
    else if (s === 'refunded') inferredType = 'info'
  }

  const variants = {
    success: 'bg-green-100 text-green-800',
    warning: 'bg-yellow-100 text-yellow-800',
    danger: 'bg-red-100 text-red-800',
    info: 'bg-blue-100 text-blue-800',
    neutral: 'bg-surface-100 text-surface-800',
  }

  return [base, variants[inferredType]].join(' ')
})
</script>

<template>
  <span :class="classes">
    {{ status }}
  </span>
</template>
