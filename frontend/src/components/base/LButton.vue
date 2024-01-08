<script setup lang="ts">
const emit = defineEmits<{
  (e: 'click'): void
}>()
const props = defineProps({
  type: { type: String, required: true }
})
const buttonClasses = "button button-"+props.type
</script>
<template>
    <button :class="buttonClasses" @click="$emit('click')"><slot></slot></button>
</template>