# Stage 4: Agent Chat Interface

- [ ] Agent Service
    - [ ] Create `src/services/agentService.ts` <!-- id: 0 -->
        - [ ] Define `ChatMessage` and `AgentResponse` types
        - [ ] Implement `sendMessage(prompt)` with mock delay and streaming simulation
- [ ] Dependencies
    - [ ] Install `markdown-it` for rendering bot responses <!-- id: 1 -->
    - [ ] Install `@types/markdown-it` <!-- id: 2 -->
- [ ] Chat UI Components
    - [ ] Create `components/chat/ChatBubble.vue` <!-- id: 3 -->
        - [ ] Support User vs Agent styling (primary vs surface color)
        - [ ] Render Markdown content
    - [ ] Create `components/chat/ThinkingIndicator.vue` <!-- id: 4 -->
- [ ] Implement Agent View
    - [ ] Update `views/AgentView.vue` <!-- id: 5 -->
        - [ ] Integrate `useAgentStore` or local state for message history
        - [ ] Auto-scroll to bottom logic
        - [ ] Handle loading states
