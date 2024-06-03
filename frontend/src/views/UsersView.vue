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
    <main>
        <input type="text" v-model="searchString">
        <l-button type="primary" @click="search(searchString)">Поиск</l-button>
        <user-card v-for="(user, idx) in users?.data?.list" :user="user" :rank="idx+1"></user-card>
    </main>
</template>