<script setup lang="ts">
import type List from '@/services/remote/list';
import type Result from '@/services/remote/result';
import type User from '@/services/remote/user';
import UserService from '@/services/user-service';
import UserCard from '@/components/UserCard.vue';
import LButton from '@/components/base/LButton.vue';
import { ref, type Ref } from 'vue';

var users : Ref<Result<List<User>>|null> = ref(null);
UserService.byRating(0).then((r) => {
    users.value = r;
})
var searchString : Ref<string> = ref("");
async function search(name) {
    users.value = await UserService.search(name, 0);
}
</script>
<template>
    <div class="welcome">
        <div class="welcome-section hcenter">
            <span class="welcome-header">Рейтинг учащихся</span>
        </div>
    </div>
    <main>
        <div class="user-search-container">
            <input type="text" v-model="searchString" placeholder="Имя пользователя">
            <l-button type="primary" @click="search(searchString)">Поиск</l-button>
        </div>
        <user-card v-for="(user, idx) in users?.data?.list" :user="user" :rank="idx+1"></user-card>
    </main>
</template>
<style>
.user-search-container {
    margin-bottom: 30px;
}
.user-search-container input {
    min-width: 25%;
}
.user-search-container input + button {
    margin-left: 15px;
}
</style>