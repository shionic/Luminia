<script setup lang="ts">
import type { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';


const emit = defineEmits<{
  (e: 'click'): void
}>()
const props = defineProps({
  type: { type: String, required: true },
  icon: { required: true }
})
var iconDef = props.icon as IconDefinition;
const buttonClasses = "icon icon-"+props.type
</script>
<template>
    <button :class="buttonClasses" @click="$emit('click')"><FontAwesomeIcon :icon='iconDef'></FontAwesomeIcon><slot></slot></button>
</template>
<style>
.icon {
  background-color: transparent;
  border: none;
  cursor: pointer;
  color: var(--color-black);
  transition: 0.2s;
}
.icon:hover {
  color: var(--color-darker-gray);
}
</style>