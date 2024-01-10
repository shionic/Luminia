<script setup lang="ts">
import FileView from '../components/FileView.vue'
const name = "Разработка красивого веб дизайна";
const category = "Современный дизайн";
const variant = "Сайт приемной комиссии ВУЗ\'а";
const variantNum = "11";
const author = "Маргарита Квакова Шулинова";
</script>
<template>
    <div class="welcome">
        <div class="tv-status">Срочно</div>
        <div class="hcenter welcome-section">
            <span class="tv-name">{{ name }}</span>
        </div>
        <div class="hcenter welcome-section">
            <span class="tv-comment">Ваш вариант {{ variantNum }}:</span> <span class="tv-description">{{ variant }}</span>
        </div>
        <div class="hcenter welcome-section">
            <a href="/" class="tv-welcome-link">{{ category }}</a> | <a href="/" class="tv-welcome-link">{{ author }}</a>
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
                <FileView></FileView>
            </div>
            <div class="tv-container-element">
                2
            </div>
        </div>
        <div class="flex hcenter">
    
        <div class="upload-container card">
            <div class="hcenter">
                <p>
                    <b>Загрузить отчет ( PDF )</b>
                </p>
            </div>
            <div class="hcenter">
                <input type="file" id="avatar" name="avatar" accept="application/pdf" />
            </div>
            <div class="hcenter">
                <p>
                    <b>Загрузить дополнительные файлы ( ZIP )</b>
                </p>
            </div>
            <div class="hcenter">
                <input type="file" id="avatar" name="avatar" accept="*" />
            </div>
            <div class="hcenter">
                <l-button type="primary">Загрузить решение</l-button>
            </div>
            <div class="hcenter">
                <span class="card-comment">Вы еще не загрузили решение</span>
            </div>
        </div>
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