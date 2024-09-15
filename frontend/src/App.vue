<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="header-wrapper">
      <div class="logo"><RouterLink to="/">Luminia</RouterLink></div>
      <nav>
        <RouterLink to="/about">О проекте</RouterLink>
        <RouterLink to="/courses">Курсы</RouterLink>
        <RouterLink to="/users">Рейтинг учащихся</RouterLink>
        <RouterLink to="/profile">Мой профиль</RouterLink>
      </nav>
    </div>
  </header>
    <RouterView />
</template>