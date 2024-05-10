<template>
<div v-if="tasks != null && tasks.data?.size && tasks.data?.size > 0">
      <h2>{{ props.header }}</h2>
      <task-card v-if="tasks != null" v-for="task in tasks.data?.list" :task="task">
      </task-card>
</div>
</template>
<script setup lang="ts">
import { ref, type Ref } from "vue";
import TaskService from '@/services/task-service'
import type Result from "@/services/remote/result";
import type List from "@/services/remote/list";
import type TaskAssign from "@/services/remote/taskassign";
import TaskCard from '../components/TaskCard.vue'
var props = defineProps<{header: string, type: string}>();
const emit = defineEmits<{
  (e: 'update', value: List<TaskAssign>|null): void
}>()
var tasks : Ref<Result<List<TaskAssign>>|null> = ref(null);
TaskService.byStatus(props.type, 0).then(t => {
  tasks.value = t;
  emit('update', t.data)
})
</script>