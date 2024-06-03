<script setup lang="ts">
import CourseService from '@/services/course-service';
import type Course from '@/services/remote/course';
import type List from '@/services/remote/list';
import type Result from '@/services/remote/result';
import CourseCard from '@/components/CourseCard.vue';
import { ref, type Ref } from 'vue';
var courses : Ref<Result<List<Course>>|null> = ref(null);
CourseService.byUser(0).then((r) => {
    courses.value = r;
});
</script>
<template>
    <div class="welcome">
        <div class="welcome-section hcenter">
            <span class="welcome-header">Доступные курсы</span>
        </div>
    </div>
    <main>
        <course-card v-for="course in courses?.data?.list" :course="course"></course-card>
    </main>
</template>