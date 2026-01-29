<script setup lang="ts">
import { ref, nextTick, watch } from 'vue'
import BaseCard from '../components/ui/BaseCard.vue'
import BaseInput from '../components/ui/BaseInput.vue'
import BaseButton from '../components/ui/BaseButton.vue'
import ChatBubble from '../components/chat/ChatBubble.vue'
import ThinkingIndicator from '../components/chat/ThinkingIndicator.vue'
import { agentService, type ChatMessage } from '../services/agentService'
// uuid removed in favor of simple keyGen

const messages = ref<ChatMessage[]>([
  {
    id: 'init-1',
    role: 'assistant',
    content: 'Hello! I am the ESO Agent. Ask me about **stock**, **orders**, or **return** status.',
    timestamp: new Date()
  }
])

const prompt = ref('')
const loading = ref(false)
const chatContainer = ref<HTMLElement | null>(null)

const scrollToBottom = async () => {
  await nextTick()
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight
  }
}

const sendMessage = async () => {
  if (!prompt.value.trim() || loading.value) return

  const userText = prompt.value
  prompt.value = ''

  // Add User Message
  messages.value.push({
    id: keyGen(),
    role: 'user',
    content: userText,
    timestamp: new Date()
  })
  scrollToBottom()

  loading.value = true

  // Prepare Assistant Message Placeholder
  const assistantMsgId = keyGen()
  const assistantMsgIndex = messages.value.length
  
  // Create empty assistant message
  messages.value.push({
    id: assistantMsgId,
    role: 'assistant',
    content: '',
    timestamp: new Date()
  })

  try {
    await agentService.sendMessageStream(
      userText,
      (chunk) => {
        // Append chunk to current message
        const msg = messages.value[assistantMsgIndex]
        if (msg) msg.content += chunk
        scrollToBottom()
      },
      () => {
        loading.value = false
      }
    )
  } catch (e) {
    const msg = messages.value[assistantMsgIndex]
    if (msg) msg.content += "\n\n_Error: Failed to get response from agent._"
    loading.value = false
  }
}

const keyGen = () => Math.random().toString(36).substring(7)

// Watch for manual scroll adjustments if needed ???
</script>

<template>
  <div class="h-[calc(100vh-8rem)] flex flex-col space-y-4">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold text-surface-900">AI Agent</h1>
    </div>
    
    <BaseCard class="flex-1 flex flex-col overflow-hidden" no-padding>
      <!-- Chat Area -->
      <div ref="chatContainer" class="flex-1 p-6 bg-surface-50/50 overflow-y-auto scroll-smooth">
        <ChatBubble 
          v-for="msg in messages" 
          :key="msg.id" 
          :role="msg.role" 
          :content="msg.content" 
        />
        
        <ThinkingIndicator v-if="loading && messages.length > 0 && messages[messages.length-1]?.content.length === 0" />
      </div>

      <!-- Input Area -->
      <div class="p-4 bg-white border-t border-surface-100">
        <form @submit.prevent="sendMessage" class="flex gap-4">
          <div class="flex-1">
            <input
              id="chat-input" 
              v-model="prompt" 
              placeholder="Ask a question..." 
              class="block w-full rounded-lg border-surface-300 bg-surface-50 text-surface-900 shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-base py-3 px-4 transition-colors duration-200"
              :disabled="loading"
              autocomplete="off"
            />
          </div>
          <BaseButton type="submit" :loading="loading" :disabled="!prompt.trim()">
            Send
          </BaseButton>
        </form>
      </div>
    </BaseCard>
  </div>
</template>
