# Stage 3: Dashboard Features

- [ ] API Abstraction Layer
    - [ ] Install `axios` (or use fetch) <!-- id: 0 -->
    - [ ] Create `src/services/api.ts` (Base client with interceptors) <!-- id: 1 -->
    - [ ] Create `src/services/stockService.ts` with Mock data fallback <!-- id: 2 -->
    - [ ] Create `src/services/orderService.ts` with Mock data fallback <!-- id: 3 -->
    - [ ] Create `src/services/returnService.ts` with Mock data fallback <!-- id: 4 -->
- [ ] Data Visualization Components
    - [ ] Create `components/ui/BaseTable.vue` (Simple responsive table) <!-- id: 5 -->
    - [ ] Create `components/ui/StatusBadge.vue` (Visual indicator for Order/Return status) <!-- id: 6 -->
- [ ] Implement Feature Views
    - [ ] Create `views/StockView.vue` (List of products + stock levels) <!-- id: 7 -->
    - [ ] Update `views/OrdersView.vue` (List of orders + status) <!-- id: 8 -->
    - [ ] Update `views/ReturnsView.vue` (List of returns + status) <!-- id: 9 -->
    - [ ] Update `router/index.ts` to link `StockView.vue` <!-- id: 10 -->
- [ ] Integration & Wiring
    - [ ] Wire up `DashboardView.vue` with aggregate summary data (mocked) <!-- id: 11 -->
