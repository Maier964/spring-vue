import Vue from 'vue';
import VeeValidate from 'vee-validate';
import VueInputAutoWitdh from 'vue-input-autowidth'
import VueSimpleAlert from "vue-simple-alert";
import VueGoodTablePlugin from 'vue-good-table';
import 'vue-good-table/dist/vue-good-table.css'


import { store } from './_store';
import { router } from './_helpers';
import App from './app/App';

Vue.use(VeeValidate);
Vue.use(VueInputAutoWitdh);
Vue.use(VueSimpleAlert);
Vue.use(VueGoodTablePlugin);

// // setup fake backend
// import { configureFakeBackend } from './_helpers';
// configureFakeBackend();

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
});