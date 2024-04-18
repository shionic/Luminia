<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Accordion from '../utils/animation.js'
var iframe = ref(null);
var mydetails = ref(null);
onMounted(() => {
    new Accordion(mydetails.value, "fileview-content");
})
</script>
<template>
    <details ref="mydetails" class="fileview-details">
        <summary>Файл 1</summary>
        <div class="fileview-content">
        <iframe ref="iframe" src="https://lyceum.urfu.ru/fileadmin/user_upload/docs/GOST_7.0.97-2016.pdf" v-resize="{ log: true }"></iframe>
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