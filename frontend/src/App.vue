<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="header-wrapper">
      <div class="logo">Luminia</div>
      <nav>
        <RouterLink to="/">Главная</RouterLink>
        <RouterLink to="/about">О проекте</RouterLink>
      </nav>
    </div>
  </header>
  <main>
    <RouterView />
  </main>
</template>