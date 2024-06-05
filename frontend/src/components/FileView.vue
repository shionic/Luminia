<script setup lang="ts">
import { onMounted, ref, type PropType } from 'vue';
import Accordion from '../utils/animation.js'
import Attachment from '@/services/remote/attachment.js';
import LButton from './base/LButton.vue';
import LIcon from './base/LIcon.vue';
import { faDownload } from '@fortawesome/free-solid-svg-icons';
var props = defineProps({attachment: {
    type: Object as PropType<Attachment>,
    required: true
}})
var ext = props.attachment.fileName.substring(props.attachment.fileName.lastIndexOf('.'))
var isBrowserViewable = ext == '.pdf';
var isImage = [".png", ".jpg", ".jpeg"].includes(ext);
var iframe = ref(null);
var mydetails = ref(null);
onMounted(() => {
    new Accordion(mydetails.value, "fileview-content");
})
async function downloadFile(url: string, fileName: string) {
    const link = document.createElement('a'); // TODO Filename
    link.href = url;
    link.target = '_blank';
    link.click();
}
</script>
<template>
    <details ref="mydetails" class="fileview-details">
        <summary>
            <span class="fileview-filename">{{ $props.attachment.fileName }}</span>
            <div class="fileview-actions">
                <LIcon type="white" :icon="faDownload" @click="downloadFile($props.attachment.path, $props.attachment.fileName)"></LIcon>
            </div>
        </summary>
        <div v-if="isBrowserViewable" class="fileview-content">
            <iframe ref="iframe" :src="$props.attachment.path" v-resize="{ }"></iframe>
        </div>
        <div v-if="isImage" class="fileview-content">
            <img :src="$props.attachment.path">
        </div>
        <div v-if="!isImage && !isBrowserViewable" class="fileview-content">
            <LButton type="primary" @click="downloadFile($props.attachment.path, $props.attachment.fileName)">Скачать</LButton>
        </div>
    </details>
</template>
<style>
.fileview-details {
    transition: 0.4s;
}
.fileview-actions {
    /* TODO */
    display: flex;
    float: right;
}
.fileview-details summary {
    background-color: var(--color-primary-background);
    border-radius: 5px;
    padding: 10px;
    cursor: pointer;
}
.fileview-details iframe {
    width: calc(100% - 5px);
    min-height: 900px;
}
.fileview-details summary + .fileview-content {
    margin-top: 0px;
}
.fileview-details + .fileview-details {
    margin-top: 10px;
}
</style>