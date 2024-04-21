<script setup lang="ts">
import TasksView from '@/components/TasksView.vue';
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
    <TasksView header="Решение отклонено преподавателем" type="REJECTED"></TasksView>
    <TasksView header="Начато" type="STARTED"></TasksView>
    <TasksView header="Не начато" type="NOT_STARTED"></TasksView>
    <TasksView header="Ожидает проверки преподавателем" type="WAIT"></TasksView>
  </main>
</template>
<style>
.welcome-username {
}
.welcome-active {
}
</style>