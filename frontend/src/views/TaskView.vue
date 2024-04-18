<script setup lang="ts">
import FileView from '../components/FileView.vue'
import { ref, type Ref } from 'vue';
import type Result from '@/services/remote/result';
import type TaskAssign from '@/services/remote/taskassign';
import TaskService from '@/services/task-service';
import AttachmentService from '@/services/attachment-service';
import type Attachment from '@/services/remote/attachment';
var props = defineProps((["id"]))
var taskAssignId : number = +props.id;
var taskAssign : Ref<Result<TaskAssign>|null> = ref(null);
TaskService.byId(taskAssignId).then(t => {
    taskAssign.value = t;
})
var uploadFiles : Ref<Array<Attachment>> = ref([]);
var uploadInput : Ref<HTMLInputElement|null> = ref(null);
async function uploadFile() {
    let files = uploadInput.value?.files;
    let fileData = files?.[0] as any;
    let fileName = files?.[0].name as string;
    let attachment = (await AttachmentService.upload(fileData, fileName)).get();
    uploadFiles.value.push(attachment);
}
</script>
<template>
    <div class="welcome">
        <div class="tv-status">Срочно</div>
        <div class="hcenter welcome-section">
            <span class="tv-name">{{ taskAssign?.data?.task.displayName }}</span>
        </div>
        <div class="hcenter welcome-section">
            <span class="tv-comment">Ваш вариант:</span> <span class="tv-description">{{ taskAssign?.data?.variant }}</span>
        </div>
        <div class="hcenter welcome-section">
            <a href="/" class="tv-welcome-link">{{ taskAssign?.data?.task.course.name }}</a> | <a href="/" class="tv-welcome-link">{{ taskAssign?.data?.task.course.teacher.id }}</a>
        </div>
    </div>
    <main>
        <p class="tv-help-text">
            Внимательно прочитайте обучающие материалы в <b>левой половине</b> экрана, подготовленные вашим преподавателем.
            В <b>правой половине</b> находится задание, которое вам необходимо выполнить.<br />
            После успешного выполнения задания сохраните отчет в формате <b>PDF</b> и загрузите его в поле ниже. Если
            требуется загрузить дополнительные материалы упакуйте их в <b>ZIP</b> архив.<br />
            Желаю удачи!
        </p>
        <div class="tv-container">
            <div class="tv-container-element">
                <div class="hcenter">
                    <div>
                        <span >Материалы задачи</span>
                    </div>
                </div>
                <FileView v-if="taskAssign != null" v-for="a in taskAssign?.data?.task.attachments" v-bind:key="a.id" :attachment="a"></FileView>
            </div>
            <div class="tv-container-element">
                <div class="hcenter">
                    <div>
                        <span >Решение 0</span>
                    </div>
                    <div>
                        <span class="card-comment">Вы еще не загрузили решение</span>
                    </div>
                </div>
                <FileView v-for="a in uploadFiles" v-bind:key="a.id" :attachment="a"></FileView>
    
        <div class="upload-container card">
            <div class="hcenter">
                    <input ref="uploadInput" type="file" id="avatar" name="avatar" accept="*" /> <l-button type="primary" @click="uploadFile">Загрузить файл</l-button>
            </div>
        </div>
            </div>
        </div>
        <div class="flex hcenter">
        </div>
    </main>
</template>
<style>
.tv-welcome-link {
    color: var(--color-darker-gray);
}

.tv-welcome-link:hover {
    color: var(--color-dark-gray);
}

.tv-comment {
    color: var(--color-darker-gray);
}

.tv-name {
    font-weight: bold;
    font-size: 24px;
}

.tv-description {
    font-weight: bold;
}

.tv-help-text {
    font-size: 18px;
    max-width: 100em;
}

.tv-container-element {
    display: flex;
    flex-direction: column;
    flex-basis: 100%;
    flex: 1;
    border: var(--color-primary-background) solid 2px;
    border-radius: 10px;
    padding: 10px;
}

.tv-container-element+.tv-container-element {
    margin-left: 20px;
}

.tv-container {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
}

.tv-status { /* TODO */
    display: flex;
    float: right;
}

.upload-container:hover {
    background-color: inherit;
}
.card:hover .upload-comment {
    color: inherit !important;
}
</style>