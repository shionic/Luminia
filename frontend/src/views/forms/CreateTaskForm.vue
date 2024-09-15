<script setup lang="ts">
import CourseService from '@/services/course-service';
import LButton from '@/components/base/LButton.vue';
import { ref, type Ref } from 'vue';
import { useRouter } from 'vue-router';
import type Course from '@/services/remote/course';
import UploadFile from '@/components/UploadFile.vue';
import type Attachment from '@/services/remote/attachment';

var router = useRouter();
var props = defineProps((["id"]));
var course : Ref<Course|null> = ref(null);
CourseService.byId(props.id).then(t => {
    course.value = t.data;
})

var name : Ref<string> = ref('');
var description : Ref<string> = ref('');
var attachments : Ref<Array<Attachment>> = ref([]);
var privateAttachments : Ref<Array<Attachment>> = ref([]);
async function createAndGo() {
    if(!course.value) {
        return;
    }
    var task = await CourseService.createTask(course.value.id, name.value, description.value,
        attachments.value.map(e => e.id), privateAttachments.value.map(e => e.id));
    //router.push('/task/'+task.data?.id); // TODO
}
async function uploadedAttachment(attachment: Attachment) {
    attachments.value.push(attachment);
}
async function uploadedPrivateAttachment(attachment: Attachment) {
    privateAttachments.value.push(attachment);
}
</script>
<template>
    <div class="welcome">
        <div class="welcome-section hcenter">
            <span class="welcome-header">Создать новую задачу</span>
        </div>
        <div class="welcome-section hcenter">
            <RouterLink :to="'/course/'+course?.id" class="tv-welcome-link">{{ course?.name }}</RouterLink>
        </div>
    </div>
    <main>
        <div class="fit-content form">
            <div class="form-field">
                <input v-model="name" type="text" placeholder="Название задачи">
            </div>
            <div class="form-field">
                <textarea v-model="description" placeholder="Краткое описание задачи">
                </textarea>
            </div>
            <div class="form-field">
                <UploadFile @uploaded="uploadedAttachment"></UploadFile>
            </div>
            <div class="form-field">
                <UploadFile @uploaded="uploadedPrivateAttachment"></UploadFile>
            </div>
            <div class="form-field">
                <l-button type="primary" @click="createAndGo">Создать новую задачу</l-button>
            </div>
        </div>
    </main>
</template>