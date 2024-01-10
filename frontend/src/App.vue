<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="header-wrapper">
      <div class="logo"><RouterLink to="/">Luminia</RouterLink></div>
      <nav>
        <RouterLink to="/courses">Курсы</RouterLink>
        <RouterLink to="/teachers">Преподаватели</RouterLink>
        <RouterLink to="/about">О проекте</RouterLink>
      </nav>
    </div>
  </header>
    <RouterView />
</template>