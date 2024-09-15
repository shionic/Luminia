<script setup lang="ts">
import CourseService from '@/services/course-service';
import LButton from '@/components/base/LButton.vue';
import { ref, type Ref } from 'vue';
import { useRouter } from 'vue-router';

var router = useRouter();

var name : Ref<string> = ref('');
var description : Ref<string> = ref('');

async function createAndGo() {
    var course = await CourseService.create(name.value, description.value);
    router.push('/course/'+course.data?.id);
}
</script>
<template>
    <div class="welcome">
        <div class="welcome-section hcenter">
            <span class="welcome-header">Создать новый курс</span>
        </div>
    </div>
    <main>
        <div class="form">
            <div class="form-field">
                <input v-model="name" type="text" placeholder="Название курса">
            </div>
            <div class="form-field">
                <textarea v-model="description" placeholder="Описание курса">
                </textarea>
            </div>
            <div class="form-field">
                <l-button type="primary" @click="createAndGo">Создать новый курс</l-button>
            </div>
        </div>
    </main>
</template>