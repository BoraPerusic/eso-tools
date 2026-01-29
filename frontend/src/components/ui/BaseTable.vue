<script setup lang="ts">
interface Column {
  key: string
  label: string
  class?: string
}

interface Props {
  columns: Column[]
  data: any[]
  loading?: boolean
}

defineProps<Props>()
</script>

<template>
  <div class="overflow-hidden rounded-lg border border-surface-200 shadow-sm">
    <div class="overflow-x-auto">
      <table class="min-w-full divide-y divide-surface-200 bg-white">
        <thead class="bg-surface-50">
          <tr>
            <th
              v-for="col in columns"
              :key="col.key"
              scope="col"
              class="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-surface-500"
              :class="col.class"
            >
              {{ col.label }}
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-surface-200 bg-white">
          <tr v-if="loading">
            <td :colspan="columns.length" class="px-6 py-4 text-center text-sm text-surface-500">
              Loading data...
            </td>
          </tr>
          <tr v-else-if="data.length === 0">
            <td :colspan="columns.length" class="px-6 py-4 text-center text-sm text-surface-500">
              No results found.
            </td>
          </tr>
          <tr 
            v-for="(row, idx) in data" 
            :key="idx"
            class="hover:bg-surface-50 transition-colors"
          >
            <td
              v-for="col in columns"
              :key="col.key"
              class="whitespace-nowrap px-6 py-4 text-sm text-surface-900"
            >
              <slot :name="col.key" :row="row" :value="row[col.key]">
                {{ row[col.key] }}
              </slot>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
