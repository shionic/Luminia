<script setup lang="ts">
import type User from '@/services/remote/user';
import UserService from '@/services/user-service';
import type { PropType } from 'vue';
import { useRouter } from 'vue-router';
var props = defineProps({user: {
    type: Object as PropType<User>,
    required: true
}, rank: {
    required: true
}})
var $router = useRouter()
const name = UserService.getName(props.user);
const rating = props.user.rating;
const rank = props.rank;
function go() {
    $router.push("/user/"+props.user.id)
}
</script>
<template>
    <div class="card user-card" @click="go">
        <div class="card-statusbar">
            #{{ rank }}
        </div>
        <div class="card-name">
            {{ name }}
        </div>
        <div class="card-description">
            Рейтинг: {{ rating }} 
        </div>
        <div class="card-bottom">
        </div>
    </div>
</template>
<style>
.user-card {
    cursor: pointer;
    min-width: 700px;
}
</style>