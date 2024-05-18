
<script setup lang="ts">
import { ref, type Ref } from "vue";
import TaskService from '@/services/task-service'
import type List from "@/services/remote/list";
import type TaskAssign from "@/services/remote/taskassign";
import TaskCard from '../components/TaskCard.vue'
var props = defineProps<{header: string, type: string, courseId: number|null}>();
const emit = defineEmits<{
  (e: 'update', value: List<TaskAssign>|null): void
}>()
var tasks : Ref<Array<TaskAssign>|null> = ref(null);
var pageId : Ref<number> = ref(0);
var totalSize : Ref<number> = ref(0);
TaskService.byStatus(props.type, props.courseId, 0).then(t => {
  if(t.data) {
    tasks.value = t.data.list;
    totalSize.value = t.data.size;
  }
  emit('update', t.data)
})
async function loadPage() {
  pageId.value = pageId.value + 1;
  TaskService.byStatus(props.type, props.courseId, pageId.value).then(t => {
    if(t.data) {
      if(tasks.value) {
        tasks.value = tasks.value?.concat(t.data.list);
      } else {
        tasks.value = t.data.list;
      }
      totalSize.value = t.data.size;
    }
    emit('update', t.data)
  })
}
</script>
<template>
  <div v-if="tasks != null && tasks.length && tasks.length > 0">
        <h2>{{ props.header }}</h2>
        <task-card v-if="tasks != null" v-for="task in tasks" :task="task">
        </task-card>
        <l-button v-if="tasks.length < totalSize" type="secondary" @click="loadPage">Просмотреть ещё</l-button>
  </div>
  </template>