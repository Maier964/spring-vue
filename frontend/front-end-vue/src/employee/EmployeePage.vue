<template>
    <div>
        <h1>Welcome, {{account.user}}. Have a good day working!</h1>
        <h4> On-going orders... </h4>
        <vue-good-table :columns="columns" :rows="rows"
         max-height="400px" :fixed-headerr="true" 
      @on-selected-rows-change="selectionChanged"
        :select-options="{ enabled: true }">
  >
</vue-good-table><br>
            <router-link to="/login">Logout (only when you finished your shift)</router-link>
    </div>
</template>

<script>
import axios from 'axios';
import { mapState, mapActions } from 'vuex'
import config from 'config'

export default {
    data(){
        return {
           columns: [
        {
            label: 'OrderID',
            field: 'id',
            type: 'text',
        },
        {
            label: 'Client',
            field: 'client',
            type: 'text',
        },
        {
            label: 'products',
            field: 'products',
            type: 'text',
        },
        {
            label: 'date',
            field: 'date',
            type: 'text',
        },
        ],
        rows: null, 
        };
    },
    computed: {
        ...mapState({
            account: state => state.account,
        })
    },
    mounted() {
        axios.get(`${config.apiUrl}/orders?request=True`)
        .then( resp => ( this.rows = resp.data ) )
    },
    methods:{
    selectionChanged(params){
    select = params.selectedRows;
    console.log(select);
    }
},  
};
</script>