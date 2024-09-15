<script setup lang="ts">
import UserLink from '@/components/UserLink.vue'
import type Course from '@/services/remote/course';
import type { PropType } from 'vue';
import { useRouter } from 'vue-router';
var props = defineProps({course: {
    type: Object as PropType<Course>,
    required: true
}})
var $router = useRouter()
const name = props.course.name;
const description = props.course.description;
const author = props.course.teacher;
var status = '???';
switch(props.course.status) {
    case 'UNKNOWN': {
        status = 'Статус неизвестен'
        break;
    }
    case 'ACCESS': {
        status = 'Не начато'
        break;
    }
    case 'IN_WORK': {
        status = 'Начато'
        break;
    }
    case 'COMPLETED': {
        status = 'Завершено'
        break;
    }
    case 'OWNING': {
        status = 'Преподаватель'
        break;
    }
}
function go() {
    $router.push("/course/"+props.course.id)
}
</script>
<template>
    <div class="card course-card" @click="go">
        <div class="card-name">
            {{ name }}
        </div>
        <div class="card-description">
            {{ status }}
        </div>
        <div class="card-bottom">
            <user-link :user="author"></user-link>
        </div>
    </div>
</template>
<style>
.course-card {
    cursor: pointer;
    min-width: 700px;
}
</style>