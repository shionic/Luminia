<script setup lang="ts">
import TaskAssign from '@/services/remote/taskassign';
var props = defineProps({task: {
    type: TaskAssign,
    required: true
}})
const name = props.task.task.displayName;
const description = props.task.variant
const author = props.task.task.course.teacher.id;
const category = props.task.task.course.name;
</script>
<template>
    <div class="card task-card">
        <div class="card-statusbar">
            Срочно
        </div>
        <div class="card-name">
            {{ name }}
        </div>
        <div class="card-description">
            <span class="card-comment">Ваш вариант:</span> {{ description }}
        </div>
        <div class="card-bottom">
            <span class="card-comment">{{ category }}</span> | <span class="card-comment">{{ author }}</span>
        </div>
    </div>
</template>
<style>
.task-card {
    cursor: pointer;
    min-width: 700px;
}
</style>