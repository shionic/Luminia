<script setup lang="ts">
import TaskCard from '../components/TaskCard.vue'
</script>

<template>
  <div class="welcome">
    <div class="hcenter">
      Добро пожаловать, <span class="welcome-active">Анастасия Евгеньевна</span>
    </div>
    <div class="hcenter">
      У вас <span class="welcome-active">2</span> невыполненных заданий
    </div>
    <div class="hcenter">
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