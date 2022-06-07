import Vue from 'vue'
import Router from 'vue-router'
import LoginScreen from '../components/login/LoginScreen.vue'

Vue.use(Router)

export default new Router({
    routes:[
        {path: '/',
        component: LoginScreen },
    ]
})

