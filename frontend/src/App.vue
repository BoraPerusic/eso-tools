<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppLayout from './components/layout/AppLayout.vue'
</script>

<template>
  <AppLayout>
    <RouterView v-slot="{ Component }">
      <transition enter-active-class="transition ease-out duration-200" enter-from-class="opacity-0 translate-y-2" enter-to-class="opacity-100 translate-y-0" leave-active-class="transition ease-in duration-150" leave-from-class="opacity-100 translate-y-0" leave-to-class="opacity-0 translate-y-2" mode="out-in">
        <component :is="Component" />
      </transition>
    </RouterView>
  </AppLayout>
</template>
