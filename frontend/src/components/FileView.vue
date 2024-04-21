<script setup lang="ts">
import { onMounted, ref, type PropType } from 'vue';
import Accordion from '../utils/animation.js'
import Attachment from '@/services/remote/attachment.js';
import LButton from './base/LButton.vue';
var props = defineProps({attachment: {
    type: Object as PropType<Attachment>,
    required: true
}})
var ext = props.attachment.fileName.substring(props.attachment.fileName.lastIndexOf('.'))
var isBrowserViewable = ext == '.pdf';
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
        <summary>{{ $props.attachment.fileName }}</summary>
        <div v-if="isBrowserViewable" class="fileview-content">
            <iframe ref="iframe" :src="$props.attachment.path" v-resize="{ }"></iframe>
        </div>
        <div v-if="!isBrowserViewable" class="fileview-content">
            <LButton type="primary" @click="downloadFile($props.attachment.path, $props.attachment.fileName)">Скачать</LButton>
        </div>
    </details>
</template>
<style>
.fileview-details {
    transition: 0.4s;
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