<template>
    <div>
        <h1>PRODUCT DASHBOARD</h1>
        <p>
            <br>
            <h3> Reports: </h3>                
            <h6>~~~~ Simple Products ~~~~</h6>
            <div style="display: flex;">
                <input type="text" v-model="name" v-validate="'required'" name="name"  placeholder="name"/>
                <input type="number" v-model="rating" v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}" name="rating" class="form-control" placeholder="rating" />
                <input type="number" v-model="price" v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}" name="price" class="form-control" placeholder="price" />
                <input type="number" v-model="calories" v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  name="calories" class="form-control"  placeholder="calories"/>
                <input type="number" v-model="protein" v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}" name="protein" class="form-control" placeholder="protein" />
                <input type="number" v-model="fat" v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}" name="fat" class="form-control" placeholder="fat" />
                <input type="number" v-model="sodium" v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}" name="sodium" class="form-control"  placeholder="sodium"/>
            </div> <br> <br> 
            <div style="display: flex; justify-content: space-around;">

            <button class="btn btn-primary" v-on:click="addProd" >Add</button>
            <button class="btn btn-primary" v-on:click="updateProd" >Update</button>
            <button class="btn btn-primary" v-on:click="deleteProd" >Delete</button>

            </div>
            
            <br> <br>
            <h6>~~~~ Composite Products ~~~~</h6>
            <br>
            <div style="display: flex; ">
                <input type="text"  v-model="nameComp" v-validate="'required'"  name="nameComp"  placeholder="Composite name"/>
                <input type="text" v-model="dish1" v-validate="'required'" name="dish1"  placeholder="Dish 1"/>
                <input type="text" v-model="dish2"  name="dish2"  placeholder="Dish 2"/>
            </div>
            <div style="display: flex; ">
                <input type="text" v-model="dish3"  name="dish3"  placeholder="Dish 3"/>
                <input type="text" v-model="dish4"  name="dish4"  placeholder="Dish 4"/>
            </div> <br> <br>
            <div style="display: flex; justify-content: space-around;">
            <button class="btn btn-primary" v-on:click="addCompProd" >Add</button>
            <button class="btn btn-primary" v-on:click="updateCompProd" >Update</button>
            <button class="btn btn-primary" v-on:click="deleteCompProd" >Delete</button>
            </div>
            <br> <br>
            <router-link to="/admin"> <h4> Go back </h4> </router-link> 
            <br>
            <router-link to="/login">Logout</router-link>
        </p>
    </div>
</template>

<script>

import axios from 'axios';
import config from 'config'


export default {

    data () {
        return {
            name:"",
            rating:"",
            price:"",
            calories:"",
            protein:"",
            fat:"",
            sodium:"",
            nameComp:"",
            dish1:"",
            dish2:"",
            dish3:"",
            dish4:""
        }
    },
    methods: {
        addProd(){

            var url = `${config.apiUrl}/addSimple`;

            if ( this.name && this.rating && this.price && 
                 this.calories && this.protein && this.fat &&
                 this.sodium ){
                    axios.post( url, {
                        name: this.name,
                        rating: this.rating,
                        price: this.price,
                        calories: this.calories,
                        protein: this.protein,
                        fat: this.fat,
                        sodium: this.sodium
                    } ).then(function (response) {
                            alert("Product added!");
                            console.log(response);
                    })
                    .catch(function (error) {
                            alert("Something went wrong..");
                            console.log(error);
                        return;
                    });
                        }
                else{
                this.$alert("Admin, please input valid numbers...");
                }
        },
        updateProd(){
            if ( this.name && this.rating && this.price && 
                 this.calories && this.protein && this.fat &&
                 this.sodium ){
                    var url = `${config.apiUrl}/updateSimple`;
                    axios.post( url, {
                        name: this.name,
                        rating: this.rating,
                        price: this.price,
                        calories: this.calories,
                        protein: this.protein,
                        fat: this.fat,
                        sodium: this.sodium
                    } ).then(function (response) {
                        // do some checking here like if response.code === 200 do something otherwise do something else u know the deal
                            alert("Product was updated!", "Success!", "success")
                            console.log(response);
                    })
                    .catch(function (error) {
                            alert("Something went wrong..")
                            console.log(error);
                        return;
                    });
                 }
            else{
            this.$alert("Admin, please input valid numbers...");
            }
        },
        deleteProd(){
            if ( this.name ){
                var url = `${config.apiUrl}/delete?name=` + this.name;
                axios.get(url).then(function (response) {
                            alert("Product was deleted!", "Success!", "success")
                            console.log(response);
                    })
                    .catch(function (error) {
                            alert("Something went wrong..")
                            console.log(error);
                        return;
                    });
                }
            else{
            this.$alert("Admin, please input valid numbers...");
            }
        },
        addCompProd(){
            if (this.nameComp && this.dish1){
                    var url = `${config.apiUrl}/addComposite`;
                    var data = [];
                    data[0] = this.nameComp;
                    data[1] = this.dish1;
                    if ( this.dish2 ) data[2] = this.dish2;
                    if ( this.dish3 ) data[3] = this.dish3;
                    if ( this.dish4 ) data[4] = this.dish4;
                    axios.post( url, {
                        data: JSON.stringify(data)}).then(function (response) {
                            alert("Product was added!", "Success!", "success")
                            console.log(response);
                    })
                    .catch(function (error) {
                            alert("Something went wrong..")
                            console.log(error);
                        return;
                    })
                 }
            else{
            this.$alert("Admin, please input valid numbers...");
            }
        },
        updateCompProd(){
            if (this.nameComp && this.dish1){
                    var url = `${config.apiUrl}/updateComposite`;
                    var data = [];
                    data[0] = this.nameComp;
                    data[1] = this.dish1;
                    if ( this.dish2 ) data[2] = this.dish2;
                    if ( this.dish3 ) data[3] = this.dish3;
                    if ( this.dish4 ) data[4] = this.dish4;
                    axios.post( url, {
                        data: JSON.stringify(data)}).then(function (response) {
                            alert("Product was updated!", "Success!", "success")
                            console.log(response);
                    })
                    .catch(function (error) {
                            alert("Something went wrong..")
                            console.log(error);
                        return;
                    })
                 }
            else{
            this.$alert("Admin, please input valid numbers...");
            }
        },
        deleteCompProd(){
            if (this.nameComp){
                var url = `${config.apiUrl}/delete?name=` + this.nameComp;
                axios.get(url).then(function (response) {
                            alert("Product was deleted!", "Success!", "success")
                            console.log(response);
                    })
                    .catch(function (error) {
                            alert("Something went wrong..")
                            console.log(error);
                        return;
                    });
                 }
            else{
            this.$alert("Admin, please input valid numbers...");
            }
        }
    }
};
</script>