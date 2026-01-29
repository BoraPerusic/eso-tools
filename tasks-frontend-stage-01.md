# Stage 1: Initialization & Scaffolding

- [ ] Project Initialization
    - [ ] Run `npm create vue@latest` in `frontend/` directory <!-- id: 0 -->
        - Logic: TypeScript, JSX: No, Router: Yes, Pinia: Yes, Vitest: Yes, E2E: No, ESLint: Yes
    - [ ] Clean up default "Hello World" boilerplate <!-- id: 1 -->
- [ ] Styling System
    - [ ] Install TailwindCSS, PostCSS, Autoprefixer <!-- id: 2 -->
    - [ ] Initialize `tailwind.config.js` <!-- id: 3 -->
    - [ ] Configure `index.css` with Tailwind directives <!-- id: 4 -->
    - [ ] Verify Tailwind works by adding a styled element to `App.vue` <!-- id: 5 -->
- [ ] Configuration
    - [ ] Configure path aliases (`@/`) in `vite.config.ts` and `tsconfig.json` (if not done by scaffold) <!-- id: 6 -->
    - [ ] Ensure build passes (`npm run build`) <!-- id: 7 -->
    - [ ] Ensure tests pass (`npm run test:unit`) <!-- id: 8 -->
