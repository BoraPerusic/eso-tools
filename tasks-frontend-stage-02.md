# Stage 2: Foundation & Layout

- [ ] Design System & Tokens
    - [ ] Update `tailwind.config.js` with primary/secondary colors and font families <!-- id: 0 -->
    - [ ] Create `src/assets/base.css` with CSS variables for dynamic theming <!-- id: 1 -->
- [ ] Atomic Components (Premium Styling)
    - [ ] Create `BaseButton.vue` (variants: primary, secondary, ghost, daanger) <!-- id: 2 -->
    - [ ] Create `BaseCard.vue` (glassmorphism support) <!-- id: 3 -->
    - [ ] Create `BaseInput.vue` (label, error state, icon support) <!-- id: 4 -->
- [ ] Layout Architecture
    - [ ] Create `AppLayout.vue` with Sidebar and Header <!-- id: 5 -->
    - [ ] Create `SideNavigation.vue` with links (Dashboard, Orders, Returns, Agent) <!-- id: 6 -->
    - [ ] Update `App.vue` to use `AppLayout` <!-- id: 7 -->
- [ ] Routing
    - [ ] Create placeholder views: `DashboardView`, `OrdersView`, `ReturnsView`, `AgentView` <!-- id: 8 -->
    - [ ] Configure routes in `router/index.ts` <!-- id: 9 -->
- [ ] Authentication (Mock)
    - [ ] Create `useAuth` store (Pinia) with `login` / `logout` actions (mocked) <!-- id: 10 -->
    - [ ] Add explicit Login page `LoginView.vue` <!-- id: 11 -->
    - [ ] Add navigation guard for protected routes <!-- id: 12 -->
