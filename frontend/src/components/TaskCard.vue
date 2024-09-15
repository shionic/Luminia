<script setup lang="ts">
import TaskAssign from '@/services/remote/taskassign';
import UserLink from '@/components/UserLink.vue'
import type { PropType } from 'vue';
import { useRouter } from 'vue-router';
var props = defineProps({task: {
    type: Object as PropType<TaskAssign>,
    required: true
}})
var $router = useRouter()
const name = props.task.task.displayName;
const description = props.task.variant
const author = props.task.task.course.teacher;
const category = props.task.task.course.name;
function go() {
    $router.push("/task/"+props.task.id)
}
</script>
<template>
    <div class="card task-card" @click="go">
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
            <span class="card-comment">{{ category }}</span> | <user-link :user="author"></user-link>
        </div>
    </div>
</template>
<style>
.task-card {
    cursor: pointer;
    min-width: 700px;
}
</style>