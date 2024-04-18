<script setup lang="ts">
import TaskCard from '../components/TaskCard.vue'
import TaskService from '@/services/task-service'
import Result from '@/services/remote/result';
import List from '@/services/remote/list';
import TaskAssign from '@/services/remote/taskassign';
import { ref, type Ref } from 'vue';
var notStartedTasks : Ref<Result<List<TaskAssign>>|null> = ref(null);
TaskService.byStatus("NOT_STARTED", 0).then(t => {
  notStartedTasks.value = t;
})
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
    <task-card v-if="notStartedTasks != null" v-for="task in notStartedTasks.data?.list" :task="task">
    </task-card>
  </main>
</template>
<style>
.welcome-username {
}
.welcome-active {
}
</style>