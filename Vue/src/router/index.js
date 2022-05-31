import Vue from 'vue'
import Router from 'vue-router'
import LoginScreen from './../components/LoginScreen.vue'

Vue.use(Router)

export default new Router({
    routes:[
        {path: '/',
        component: LoginScreen },
    ]
})

