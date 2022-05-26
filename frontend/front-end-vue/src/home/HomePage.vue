<template>
    <div>
        <h1> &#x1F32D; Welcome back, {{account.user}}!  &#x1F32D; </h1>
        <br>
        <h3>&#127790; Place your order: &#127790;</h3>
        <vue-good-table :columns="columns" :rows="rows"
        :ref="rows" 
         max-height="500px" :fixed-headerr="true" 
        :line-numbers="true"   :search-options="{ enabled: true }"
         :sort-options="{ enabled: true, initialSortBy: {field: 'name', type: 'asc'}}"
          :pagination-options="{
        enabled: true,
        mode: 'records',
        perPage: 10,
        position: 'bottom',
        dropdownAllowAll: false,
        setCurrentPage: 1,
        jumpFirstOrLast : true,
        firstLabel : 'First Page',
        lastLabel : 'Last Page',
        nextLabel: 'next',
        prevLabel: 'prev',
        rowsPerPageLabel: 'Rows per page',
        ofLabel: 'of',
        pageLabel: 'page',
        allLabel: 'All',
  }"
  @on-selected-rows-change="selectionChanged"
  :select-options="{ enabled: true }">
  >
</vue-good-table><br>
        <form @submit.prevent="createOrder">
            <button type="submit" class="btn btn-success">Order meal</button> <br> <br>
        </form>

        <h3>Want to buy a whole menu instead? 😋</h3> 

        <vue-good-table :columns="compColumns" :rows="compRows"
        :ref="compRows" 
        max-height="500px" :fixed-headerr="true" 
        :line-numbers="true"   :search-options="{ enabled: true }"
        :sort-options="{ enabled: true, initialSortBy: {field: 'name', type: 'asc'}}"
        @on-selected-rows-change="compositeSelectionChanged"   
        :select-options="{ enabled: true }">>
        </vue-good-table>
        <br>
        <form @submit.prevent="createCompositeOrder">
        <button type="submit" class="btn btn-success">Order menu</button> <br> <br>
        </form>

        <p>
            <router-link to="/login">Logout</router-link>
        </p>
    </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import config from 'config'
import axios from 'axios'

var select = []
var dataToSend = []
var compSelect = []
var compDataToSend = []

export default {
    data(){
    return {
        columns: [
        {
            label: 'Name',
            field: 'name',
        },
        {
            label: 'Rating',
            field: 'rating',
            type: 'number',
        },
        {
            label: 'Price',
            field: 'price',
            type: 'number',
        },
        {
            label: 'Calories',
            field: 'calories',
            type: 'number',
        },
        {
            label: 'Protein',
            field: 'protein',
            type: 'number',
        },
        {
            label: 'Fat',
            field: 'fat',
            type: 'number',
        },
        {
            label: 'Sodium',
            field: 'sodium',
            type: 'number',
        },
        ],
        rows: null,
        compRows : null,
        compColumns : [
        {
            label: 'Menu name',
            field: 'title',
        },
        {
            label: 'Dish1',
            field: 'dish1',
            type: 'text',
        },
        {
            label: 'Dish2',
            field: 'dish2',
            type: 'text',
        },
        {
            label: 'Dish3',
            field: 'dish3',
            type: 'text',
        },
        {
            label: 'Dish4',
            field: 'dish4',
            type: 'text',
        },
        ],
        };
    },
    computed: {
        ...mapState({
            account: state => state.account,
        })
    },
    methods: {
        createOrder(){
            console.log("A intrat");

            // Filter data so we send only the things that are necessary
            for( let iterator = 0; iterator < select.length; iterator++){
                dataToSend[iterator+1] = { name: select[iterator].name, rating: select[iterator].rating, 
                                  price: select[iterator].price, calories: select[iterator].calories,
                                  protein: select[iterator].protein, fat: select[iterator].fat, 
                                  sodium: select[iterator].sodium  }
            }

            dataToSend[0] = this.account.user;

            // console.log(dataToSend
        
            // const data = dataToSend.join();

            console.log(JSON.stringify( dataToSend ));

            axios.post(`${config.apiUrl}/simpleOrder`, {
                data : JSON.stringify( dataToSend )
            })
            .then(function (response) {
                alert("Order was placed!", "Success!", "success");
                console.log(response);
            })
            .catch(function (error) {
                alert("Something went wrong..");
                console.log(error);
                return;
            });
        },
        selectionChanged(params){
            select = params.selectedRows;
            console.log(select);
        },  
        compositeSelectionChanged(params){
            compSelect = params.selectedRows;
            console.log(compSelect);
        },
    createCompositeOrder(){
            console.log("A intrat");

            if ( compSelect.length === 0 ){
                this.$alert("Report 4 was generated!")
            }

            // Filter data so we send only the things that are necessary
            for( let iterator = 0; iterator < compSelect.length; iterator++){
                compDataToSend[iterator+1] = { title:compSelect[iterator].title, 
                dish1: compSelect[iterator].dish1, dish2: compSelect[iterator].dish2,
                dish3: compSelect[iterator].dish3, dish4: compSelect[iterator].dish4};
            }

            compDataToSend[0] = this.account.user;

            // console.log(dataToSend)
        
            // const data = dataToSend.join();

            console.log(JSON.stringify( compDataToSend ));

            axios.post(`${config.apiUrl}/compositeOrder`, {
                data : JSON.stringify( compDataToSend )
            })
            .then(function (response) {
                alert("Order was placed!", "Success!", "success");
                console.log(response);
            })
            .catch(function (error) {
                alert("Something went wrong..");
                console.log(error);
                return;
            });
    },
//     watch: {
//     question: {
//       handler(question) {
//             fetch(`${config.apiUrl}/products?request=True`).then(items => items.json().then( jsonItems => this.rows = jsonItems ) );
//             },
//       // force eager callback execution
//       immediate: true
//     }
//   
    },
    mounted(){
        axios.get(`${config.apiUrl}/products?request=True`)
        .then( resp => ( this.rows = resp.data ) )

        axios.get(`${config.apiUrl}/compProducts?request=True`)
        .then( resp => ( this.compRows = resp.data ) )
    }

};
</script>