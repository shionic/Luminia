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
<style>
.button {
    border: none;
    border-radius: 10px;
    padding: 15px 20px 15px;
    font-size: 18px;
    cursor: pointer;
    transition: 0.2s;
    font-weight: 500;
}

.button-primary {
    background-color: var(--color-primary);
    color: var(--color-black);
}

.button-primary:hover {
    background-color: var(--color-primary-hover);
}

.button-secondary {
    background-color: var(--color-secondary);
    color: var(--color-black);
}

.button-secondary:hover {
    background-color: var(--color-secondary-hover);
}

.button-black {
    background-color: var(--color-black);
    color: var(--color-white);
}
.button-black:hover {
    background-color: var(--color-darker-gray);
    color: var(--color-white);
}
</style>