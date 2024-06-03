<script setup lang="ts">
import FileView from '../components/FileView.vue'
import { computed, ref, type Ref } from 'vue';
import type Result from '@/services/remote/result';
import type TaskAssign from '@/services/remote/taskassign';
import TaskService from '@/services/task-service';
import AttachmentService from '@/services/attachment-service';
import type Attachment from '@/services/remote/attachment';
import type TaskResult from '@/services/remote/taskresult';
import UserLink from '@/components/UserLink.vue';
import UploadFile from '@/components/UploadFile.vue';
// Task
var props = defineProps((["id"]))
var taskAssignId: number = +props.id;
var taskAssign: Ref<Result<TaskAssign> | null> = ref(null);
TaskService.byId(taskAssignId).then(t => {
    taskAssign.value = t;
})
// User TaskResult
var taskResult: Ref<Result<TaskResult> | null> = ref(null);
var uploadMode: Ref<boolean> = ref(true);
TaskService.getResultById(taskAssignId, true).then(t => {
    taskResult.value = t;
    uploadMode.value = false;
})
var resultStatusText = computed(e => {
    switch (taskResult.value?.data?.status) {
        case "REJECTED": {
            return "Преподаватель отклонил ваше решение"
        }
        case "STARTED": {
            return "Вы еще не отправили решение преподавателю"
        }
        case "NOT_STARTED": {
            return "Вы не начали задание"
        }
        case "WAIT": {
            return "Преподаватель еще не проверил решение"
        }
        case "ACCEPT": {
            return "Преподаватель принял ваше решение"
        }
        case "ABSORBED": {
            return "Вы отправили более новое решение"
        }
    }
})
// Opponent TaskResult
var oppTaskResult: Ref<Result<TaskResult> | null> = ref(null);
TaskService.getResultById(taskAssignId, false).then(t => {
    oppTaskResult.value = t;
})
// Upload files
var uploadFiles: Ref<Array<Attachment>> = ref([]);
async function uploadedFile(attachment : Attachment) {
    uploadFiles.value.push(attachment);
    uploadMode.value = true
}
async function uploadResult() {
    await TaskService.upload(taskAssignId, uploadFiles.value.map(t => t.id));
}
</script>
<template>
    <div class="welcome">
        <div class="hcenter welcome-section">
            <span class="welcome-header">{{ taskAssign?.data?.task.displayName }}</span>
        </div>
        <div class="hcenter welcome-section">
            <span class="tv-comment">Ваш вариант:</span> <span class="tv-description">{{ taskAssign?.data?.variant
                }}</span>
        </div>
        <div class="hcenter welcome-section">
            <RouterLink :to="'/course/'+taskAssign?.data?.task.course.id" class="tv-welcome-link">{{ taskAssign?.data?.task.course.name }}</RouterLink> | <user-link :user="taskAssign?.data?.task.course.teacher"></user-link>
        </div>
    </div>
    <main>
        <p class="tv-help-text">
            Внимательно прочитайте обучающие материалы в <span class="text-accent">левой половине</span> экрана, подготовленные вашим
            преподавателем.
            В <span class="text-accent">правой половине</span> находятся <span class="text-accent">загруженные вами</span> работы.<br />
            После ответа преподавателя вы <span class="text-accent">пересдать</span> ваши работы
        </p>
        <div class="tv-container">
            <div class="tv-container-element">
                <div v-if="taskAssign != null">
                    <div class="hcenter">
                        <div>
                            <span class="tv-instruct-text">Задание</span>
                        </div>
                    </div>
                    <FileView v-for="a in taskAssign?.data?.task.attachments"
                        v-bind:key="a.id" :attachment="a"></FileView>
                </div>
                <div v-if="oppTaskResult != null">
                    <div class="hcenter">
                        <div>
                            <span class="tv-instruct-text">Материалы преподавателя</span>
                        </div>
                    </div>
                    <FileView v-for="a in oppTaskResult?.data?.attachments"
                        v-bind:key="a.id" :attachment="a"></FileView>
                </div>
            </div>
            <div class="tv-container-element">
                <div v-if="taskResult == null || !taskResult.isOk()" class="hcenter">
                    <div>
                        <span class="tv-instruct-text">Ваше решение</span>
                    </div>
                    <div>
                        <span class="card-comment">Вы еще не загрузили решение</span>
                    </div>
                </div>
                <div v-if="taskResult != null && taskResult.isOk()" class="hcenter">
                    <div>
                        <span>Решение {{ taskResult.data?.id }}</span>
                    </div>
                    <div>
                        <span class="card-comment">{{ resultStatusText }}</span>
                    </div>
                </div>
                <div class="hcenter">
                    <FileView v-if="!uploadMode && taskResult?.isOk()" v-for="a in taskResult?.data?.attachments"
                    v-bind:key="a.id" :attachment="a"></FileView>
                <FileView v-for="a in uploadFiles" v-bind:key="a.id" :attachment="a"></FileView>
                <upload-file @uploaded="uploadedFile"></upload-file>
                <l-button
                            v-if="uploadFiles.length > 0" type="primary" @click="uploadResult">Загрузить
                            решение</l-button>
                </div>
            </div>
        </div>
        <div class="flex hcenter">
        </div>
    </main>
</template>
<style>
.tv-container-element > .hcenter + .hcenter {
    margin-top: 20px;
}
.tv-welcome-link {
    color: var(--color-darker-gray);
}

.tv-welcome-link:hover {
    color: var(--color-dark-gray);
}

.tv-comment {
    color: var(--color-darker-gray);
}

.tv-description {
    font-weight: 550;
    color: var(--color-primary);
}

.tv-help-text {
    font-size: 18px;
    max-width: 100em;
}

.tv-instruct-text {
    font-size: 20px;
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

.tv-status {
    /* TODO */
    display: flex;
    float: right;
}

.card:hover .upload-comment {
    color: inherit !important;
}
</style>