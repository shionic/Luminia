import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginService from "@/services/login-service";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/task/:id',
      name: 'task',
      component: () => import('../views/TaskView.vue'),
      props: true
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.path === '/code' && to.query.code != null) {
      LoginService.getTokens(to.query.code as string).then(() => {
          next({name: 'home'});
      });
  } else {
      next()
  }
});

export default router
