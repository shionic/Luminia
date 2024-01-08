<script setup lang="ts">
const name = 'Разработка красивого веб дизайна';
const description = 'Сайт приемной комиссии ВУЗ\'а'
const author = 'Маргарита Квакова Шулинова';
const category = 'Современный дизайн';
</script>
<template>
    <div class="card task-card">
        <div class="card-statusbar">
            Срочно
        </div>
        <div class="card-name">
            {{ name }}
        </div>
        <div class="card-description">
            <span class="card-comment">Ваш вариант:</span> {{ description }}
        </div>
        <div class="card-bottom">
            <span class="card-comment">{{ category }}</span> | <span class="card-comment">{{ author }}</span>
        </div>
    </div>
</template>
<style>
.task-card {
    cursor: pointer;
    min-width: 700px;
}
</style>