import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/views/Layout'
import Login from '@/views/Login'
import GradRequirementEdit from '@/views/gradRequirement/GradRequirementEdit'
import Targrt from '@/views/gradRequirement/Targrt'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Layout',
      name: 'Layout',
      component: Layout,
      children: [
        {
          path: '/Layout',
          redirect: 'GradRequirementEdit',
        },
        {
          path: 'GradRequirementEdit',
          name: 'GradRequirementEdit',
          component: GradRequirementEdit
        },
        {
          path: 'Targrt',
          name: 'Targrt',
          component: Targrt
        }
      ]
    },

  ]
})
