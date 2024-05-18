<script setup lang="ts">
import TasksView from '@/components/TasksView.vue';
import CourseService from '@/services/course-service';
import type Course from '@/services/remote/course';
import UserLink from '@/components/UserLink.vue';
import { ref, type Ref } from 'vue';
var props = defineProps((["id"]))
var course : Ref<Course|null> = ref(null);
CourseService.byId(props.id).then(t => {
    course.value = t.data;
})
</script>
<template>
    <div class="welcome">
        <div class="hcenter welcome-section">
            <span class="crs-name">{{ course?.name }}</span>
        </div>
        <div class="hcenter welcome-section">
            <span class="crs-description">{{ course?.description }}</span>
        </div>
        <div class="hcenter welcome-section">
            <span>Преподаватель:</span> <user-link :user="course?.teacher"></user-link>
        </div>
    </div>
    <div>
        <TasksView header="Решение отклонено преподавателем" type="REJECTED" :course-id="props.id"></TasksView>
        <TasksView header="Начато" type="STARTED" :course-id="props.id"></TasksView>
        <TasksView header="Не начато" type="NOT_STARTED" :course-id="props.id"></TasksView>
        <TasksView header="Ожидает проверки преподавателем" :course-id="props.id" type="WAIT"></TasksView>
    </div>
</template>
<style>
.crs-name {
    font-weight: bold;
    font-size: 24px;
}
.crs-description {
    font-weight: bold;
}
.crs-welcome-link {
    color: var(--color-darker-gray);
}

.crs-welcome-link:hover {
    color: var(--color-dark-gray);
}

.crs-comment {
    color: var(--color-darker-gray);
}
</style>