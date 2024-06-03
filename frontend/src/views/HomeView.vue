<script setup lang="ts">
import TasksView from '@/components/TasksView.vue';
import type List from '@/services/remote/list';
import type SelfUser from '@/services/remote/selfuser';
import type TaskAssign from '@/services/remote/taskassign';
import UserService from '@/services/user-service';
import { ref, type Ref } from 'vue';
import { useRouter } from 'vue-router';
var router = useRouter();
var user : Ref<SelfUser|null> = ref(null);
UserService.current().then((u) => {
  if(u.isOk()) {
    user.value = u.data;
  }
})
var uncompleted : Ref<number> = ref(0);
var uncompletedId : Ref<number> = ref(-1);
var uncompletedPriority = ref(0);
function addUncompleted(value : List<TaskAssign>|null, priority : number) {
  if(!value) {
    return;
  }
  uncompleted.value += value.size;
  if(value.size >= 1) {
    if(uncompletedPriority.value < priority) {
      uncompletedId.value = value.list[0].id;
      uncompletedPriority.value = priority;
    }
  }
}
function go() {
  if(uncompletedId.value < 0) {
    return;
  }
  router.push('/task/'+uncompletedId.value)
}
</script>

<template>
  <div class="welcome">
    <div class="hcenter welcome-section">
      Добро пожаловать, <span class="welcome-active">{{ user?.user.fullName ?? user?.user.username }}</span>
    </div>
    <div class="hcenter welcome-section">
      У вас <span class="welcome-active">{{ uncompleted }}</span> невыполненных заданий
    </div>
    <div v-if="uncompleted > 0" class="hcenter welcome-section">
      <l-button type="black" @click="go">Начать работу</l-button>
    </div>
  </div>
  <main>
    <TasksView header="Решение отклонено преподавателем" type="REJECTED" :course-id="null" @update="(e) => addUncompleted(e, 10)"></TasksView>
    <TasksView header="Начато" type="STARTED" :course-id="null" @update="(e) => addUncompleted(e, 5)"></TasksView>
    <TasksView header="Не начато" type="NOT_STARTED" :course-id="null" @update="(e) => addUncompleted(e, 1)"></TasksView>
    <TasksView header="Ожидает проверки преподавателем" :course-id="null" type="WAIT"></TasksView>
  </main>
</template>
<style>
.welcome-username {
}
.welcome-active {
}
</style>