import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/views/Layout'
import Login from '@/views/Login'
import GradRequirementEdit from '@/views/gradRequirement/GradRequirementEdit'
import Target from '@/views/target/Target'
import Course from '@/views/course/Course'
import CheckLinks from '@/views/course/CheckLinks'

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
          path: 'Target',
          name: 'Target',
          component: Target
        },
        {
          path: 'Course',
          name: 'Course',
          component: Course
        },
        {
          path: 'CheckLinks',
          name: 'CheckLinks',
          component: CheckLinks
        },
      ]
    },

  ]
})
