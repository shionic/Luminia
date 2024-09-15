<script setup lang="ts">
import AttachmentService from '@/services/attachment-service';
import type Attachment from '@/services/remote/attachment';
import { ref, type Ref, onMounted, onUnmounted } from 'vue';
const emit = defineEmits<{
  (e: 'uploaded', id: Attachment): void
}>();

function onDrop(e) {
    uploadFile([...e.dataTransfer.files])
}

function preventDefaults(e) {
    e.preventDefault()
}

const events = ['dragenter', 'dragover', 'dragleave', 'drop']

onMounted(() => {
    events.forEach((eventName) => {
        document.body.addEventListener(eventName, preventDefaults)
    })
})

onUnmounted(() => {
    events.forEach((eventName) => {
        document.body.removeEventListener(eventName, preventDefaults)
    })
})
async function uploadFile(files) {
    let fileData = files?.[0] as any;
    let fileName = files?.[0].name as string;
    let attachment = (await AttachmentService.upload(fileData, fileName)).get();
    emit('uploaded', attachment);
}
async function manualUploadFile() {
    var input = document.createElement('input');
    input.type = 'file';
    input.accept = '*';
    input.onchange = e => {
        var file = (e.target as any).files;
        uploadFile(file);
    }
    input.click();
}
</script>
<template>
<div class="upload-container card" @drop.prevent="onDrop">
                    <div class="hcenter">
                        Выберите файл с диска или <span class="text-accent">перетащите</span> его в область загрузки
                    </div>
                    <div class="hcenter">
                        <l-button
                            type="primary" @click="manualUploadFile">Загрузить файл</l-button>
                    </div>
                </div>
</template>
<style>
.upload-container .hcenter + .hcenter {
    margin-top: 10px;
}

.upload-container:hover {
    background-color: inherit;
}
</style>