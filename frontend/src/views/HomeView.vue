<script setup lang="ts">
import TaskCard from '../components/TaskCard.vue'
</script>

<template>
  <div class="welcome">
    <div class="hcenter welcome-section">
      Добро пожаловать, <span class="welcome-active">Анастасия Евгеньевна</span>
    </div>
    <div class="hcenter welcome-section">
      У вас <span class="welcome-active">2</span> невыполненных заданий
    </div>
    <div class="hcenter welcome-section">
      <l-button type="black">Начать работу</l-button>
    </div>
  </div>
  <main>
    <h2>Текущие задания</h2>
    <task-card>
    </task-card>
    <task-card>
    </task-card>
  </main>
</template>
<style>
.welcome-username {
}
.welcome-active {
}
</style>