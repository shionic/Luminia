<script setup lang="ts">
import User from '@/services/remote/user';
import UserService from '@/services/user-service';
import type { PropType } from 'vue';
var props = defineProps({user: {
    type: Object as PropType<User|null|undefined>,
    required: true
}})
</script>
<template>
    <RouterLink :to="'/user/'+user?.id">{{ UserService.getName(props.user) }}</RouterLink>
</template>
<style>

</style>