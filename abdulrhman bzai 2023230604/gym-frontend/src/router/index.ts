import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/pages/Home.vue'
import About from '@/pages/About.vue'
import Trainers from '@/pages/trainer/Trainers.vue'
import NewTrainer from '@/pages/trainer/NewTrainer.vue'
import EditTrainer from '@/pages/trainer/EditTrainer.vue'
import Members from '@/pages/member/Members.vue'
import NewMember from '@/pages/member/NewMember.vue'
import EditMember from '@/pages/member/EditMember.vue'
import Plans from '@/pages/plan/Plans.vue'
import NewPlan from '@/pages/plan/NewPlan.vue'
import EditPlan from '@/pages/plan/EditPlan.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Home, meta: { title: 'Roster' } },
    { path: '/about', component: About, meta: { title: 'About' } },

    { path: '/members', component: Members, meta: { title: 'Members' } },
    { path: '/members/new', component: NewMember, meta: { title: 'Add member' } },
    { path: '/members/:id', component: EditMember, meta: { title: 'Edit member' } },

    { path: '/trainers', component: Trainers, meta: { title: 'Trainers' } },
    { path: '/trainers/new', component: NewTrainer, meta: { title: 'Add trainer' } },
    { path: '/trainers/:id', component: EditTrainer, meta: { title: 'Edit trainer' } },

    { path: '/plans', component: Plans, meta: { title: 'Price list' } },
    { path: '/plans/new', component: NewPlan, meta: { title: 'Add plan' } },
    { path: '/plans/:id', component: EditPlan, meta: { title: 'Edit plan' } },
  ]
})

router.afterEach((to) => {
  if (to.meta.title) {
    document.title = `${to.meta.title} - Gym Manager`
  }
})

export default router
