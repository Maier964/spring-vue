<template>
    <div>
        <h1>ADMIN DASHBOARD</h1>
        <p>
            <br>
            <h3> Actions: </h3>

            <router-link to="/emplogin"> <h4> 1. Add new employee </h4> </router-link> 
            <router-link to="/prod"><h4> 2. Manage Products </h4> </router-link> <br> 

            <h3> Reports: </h3>
            <div>
                <h6>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</h6>

                <form @submit.prevent="handleReport1">
                    <input type="number" placeholder="Start hour"  v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  v-model="startHour"  name="startHour" class="form-control" />
                    <input type="number" placeholder="End hour"  v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  v-model="endHour"  name="endHour" class="form-control" />
                    <br>
                    <button type="submit" class="btn btn-success">Generate report 1</button> <br> <br>
                </form>
                <h6>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</h6>

                <form @submit.prevent="handleReport2">
                    <input type="number" placeholder="Times ordered"  v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  v-model="timesOrdered"  name="startHour" class="form-control" />
                    <br>
                    <button type="submit" class="btn btn-success">Generate report 2</button> <br> <br>
                </form>

                <h6>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</h6>

                <form @submit.prevent="handleReport3">
                    <input type="number" placeholder="Times ordered"  v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  v-model="timesOrdered3"  name="timesOrdered3" class="form-control" />
                    <input type="number" placeholder="Price"  v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  v-model="price"  name="price" class="form-control" />
                    <br>
                    <button type="submit" class="btn btn-success">Generate report 3</button> <br> <br>

                </form>

                <h6>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</h6>

                <form @submit.prevent="handleReport4">
                    <input type="number" placeholder="Day"  v-autowidth="{maxWidth: '960px', minWidth: '50px', comfortZone: 20}"  v-model="day"  name="day" class="form-control" />
                    <br>    
                    <button type="submit" class="btn btn-success">Generate report 4</button> <br> <br>

                </form>

                <h6>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~</h6>
            </div>
            
            <br>
            <router-link to="/login">Logout</router-link>
        </p>
    </div>
</template>

<script>

import config from 'config'


export default {

    data () {
        return {
            startHour:"",
            endHour:"",
            timesOrdered:"",
            timesOrdered3:"",
            price:"",
            day:""
        }
    },
    methods: {
        handleReport1(e){

            // console.log(this.startHour)
            // console.log(this.endHour)
            
            // Small sanitisation
            if ( ( this.startHour === "" )  || ( this.endHour === "")  || 
            ( parseInt(this.startHour) > 24 )|| ( parseInt(this.endHour) > 24 ) ||
            parseInt(this.startHour) > parseInt(this.endHour) ){

                this.$alert("Admin, please input valid numbers...")
                return;
            }

            var url = `${config.apiUrl}/report1?` + `startHour=` + this.startHour + "&endHour=" + this.endHour;

            // Make request to backend
            fetch(url).
                then(data => this.$alert("Report 1 was generated!", "Success!", "success"));

        },

        handleReport2(e){
            
            if ( ( this.timesOrdered == "" || parseInt(this.timesOrdered) <= 0 ) ){
                this.$alert("Admin, please input valid numbers...")
                return;
            }

            var url = `${config.apiUrl}/report2?` + `timesOrdered=` + this.timesOrdered;

            fetch(url).then( data=> this.$alert("Report 2 was generated!", "Success!", "success") )

        },

        handleReport3(e){

            if ( ( this.timesOrdered == "" || parseInt(this.timesOrdered) <= 0 ) 
                   || this.price == "" || parseInt(this.price) < 0 ){

                this.$alert("Admin, please input valid numbers...")
                return;
            }

            var url = `${config.apiUrl}/report3?` + `timesOrdered=` + this.timesOrdered + "&pricer=" + this.price;

            // Make request to backend
            fetch(url).
                then(data => this.$alert("Report 3 was generated!", "Success!", "success"));

        },

        handleReport4(e){

            if ( ( this.day == "" || parseInt(this.day) > 31 ) ){
                this.$alert("Admin, please input valid numbers...")
                return;
            }

            var url = `${config.apiUrl}/report4?` + `day=` + this.day;

            // Make request to backend
            fetch(url).
                then(data => this.$alert("Report 4 was generated!", "Success!", "success"));

        }

    }
};
</script>