<script setup lang="ts">
import type User from '@/services/remote/user';
import UserService from '@/services/user-service';
import { ref, type Ref } from 'vue';

var props = defineProps((["id"]));
var user : Ref<User|null> = ref(null);
UserService.byId(props.id).then(t => {
    user.value = t.data;
})
</script>
<template>

</template>