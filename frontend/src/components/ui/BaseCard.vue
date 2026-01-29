<script setup lang="ts">
interface Props {
  noPadding?: boolean
}

withDefaults(defineProps<Props>(), {
  noPadding: false,
})
</script>

<template>
  <div class="bg-white/80 backdrop-blur-sm border border-surface-200 rounded-xl shadow-sm hover:shadow-md transition-shadow duration-300 overflow-hidden">
    <div :class="{ 'p-6': !noPadding }">
      <slot />
    </div>
  </div>
</template>
