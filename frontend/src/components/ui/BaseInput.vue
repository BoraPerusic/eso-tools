<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  modelValue: string | number
  label?: string
  placeholder?: string
  type?: string
  error?: string
  disabled?: boolean
  id: string
}

const props = withDefaults(defineProps<Props>(), {
  type: 'text',
  placeholder: '',
  disabled: false,
})

const emit = defineEmits(['update:modelValue'])

const inputClasses = computed(() => {
  const base = 'block w-full rounded-lg border-surface-300 bg-surface-50 text-surface-900 shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm transition-colors duration-200'
  const state = props.error 
    ? 'border-red-300 text-red-900 placeholder-red-300 focus:border-red-500 focus:ring-red-500' 
    : 'focus:border-primary-500 focus:ring-primary-500'
  
  return [base, state].join(' ')
})

const updateValue = (event: Event) => {
  const target = event.target as HTMLInputElement
  emit('update:modelValue', target.value)
}
</script>

<template>
  <div class="space-y-1">
    <label v-if="label" :for="id" class="block text-sm font-medium text-surface-700">
      {{ label }}
    </label>
    <div class="relative">
      <input
        :id="id"
        :type="type"
        :value="modelValue"
        :disabled="disabled"
        :placeholder="placeholder"
        :class="inputClasses"
        @input="updateValue"
      />
      <div v-if="error" class="absolute inset-y-0 right-0 flex items-center pr-3 pointer-events-none">
        <svg class="h-5 w-5 text-red-500" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z" clip-rule="evenodd" />
        </svg>
      </div>
    </div>
    <p v-if="error" class="mt-1 text-sm text-red-600">{{ error }}</p>
  </div>
</template>
