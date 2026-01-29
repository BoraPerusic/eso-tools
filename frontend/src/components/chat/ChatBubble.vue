<script setup lang="ts">
import { computed } from 'vue'
import MarkdownIt from 'markdown-it'

interface Props {
  role: 'user' | 'assistant'
  content: string
}

const props = defineProps<Props>()

const md = new MarkdownIt({
  html: false,
  linkify: true,
  breaks: true
})

const renderedContent = computed(() => {
  return md.render(props.content)
})

const containerClasses = computed(() => {
  return props.role === 'user' 
    ? 'justify-end' 
    : 'justify-start'
})

const bubbleClasses = computed(() => {
  return props.role === 'user'
    ? 'bg-primary-600 text-white rounded-br-none'
    : 'bg-white text-surface-900 shadow-sm border border-surface-200 rounded-bl-none'
})
</script>

<template>
  <div class="flex w-full mb-4" :class="containerClasses">
    <div 
      class="max-w-[80%] px-4 py-3 rounded-2xl text-sm leading-relaxed prose prose-sm"
      :class="[
        bubbleClasses,
        role === 'assistant' ? 'prose-headings:text-surface-900 prose-a:text-primary-600' : 'prose-invert'
      ]"
    >
      <!-- eslint-disable-next-line vue/no-v-html -->
      <div v-html="renderedContent"></div>
    </div>
  </div>
</template>

<style>
/* Scoped styles for prose overrides if needed */
.prose p {
  margin-bottom: 0.5em;
  margin-top: 0.5em;
}
.prose p:first-child { margin-top: 0; }
.prose p:last-child { margin-bottom: 0; }
.prose table { font-size: 0.9em; }
</style>
