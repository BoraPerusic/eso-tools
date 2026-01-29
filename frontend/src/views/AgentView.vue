<script setup lang="ts">
import BaseCard from '../components/ui/BaseCard.vue'
import BaseInput from '../components/ui/BaseInput.vue'
import BaseButton from '../components/ui/BaseButton.vue'
import { ref } from 'vue'

const prompt = ref('')
</script>

<template>
  <div class="h-[calc(100vh-8rem)] flex flex-col space-y-4">
    <h1 class="text-2xl font-bold text-surface-900">AI Agent</h1>
    
    <BaseCard class="flex-1 flex flex-col" no-padding>
      <!-- Chat Interface Placeholder -->
      <div class="flex-1 p-6 bg-surface-50/50">
        <div class="flex justify-start">
          <div class="bg-white p-3 rounded-lg rounded-tl-none shadow-sm max-w-lg border border-surface-200">
            <p class="text-sm">Hello! I am the ESO Agent. Ask me about stock, orders, or return status.</p>
          </div>
        </div>
      </div>

      <!-- Input Area -->
      <div class="p-4 bg-white border-t border-surface-100 flex gap-4">
        <div class="flex-1">
          <BaseInput 
            id="chat-input" 
            v-model="prompt" 
            placeholder="Ask a question..." 
          />
        </div>
        <BaseButton>Send</BaseButton>
      </div>
    </BaseCard>
  </div>
</template>
