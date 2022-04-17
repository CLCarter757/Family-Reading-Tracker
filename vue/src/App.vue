<template>
  <div id="app" class="center">
      <vs-navbar
      fixed
      v-model="active" >
      
       <vs-button color="danger" :active="currentRoute == 'home'" to="/"  id="my-library">My Library </vs-button>
      
     
      <vs-button color="warning" :active="currentRoute == 'search'" id="find-books" to="/search">
          Find Books
        </vs-button>
     
       
      <vs-button color="success" :active="currentRoute == 'family'" id="family" to="/family">
          Family
        </vs-button>
       
        <vs-button color="black" :active="currentRoute == 'prizes'" id="prizes" to="/prizes">
          Prizes
        </vs-button>
         
        <template #right>
          <vs-button flat warning >Logout</vs-button>
        </template>
        </vs-navbar>
    <router-view/>

      <!-- <router-link v-bind:to="{ name: 'home' }" tag="button">My Library</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'search' }" tag="button" v-if="$store.state.token != ''">Find Books</router-link>
      <router-link v-bind:to="{ name: 'family' }" tag="button" v-if="$store.state.token != ''">Family</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'prizes' }" tag="button" v-if="$store.state.token != ''">Prizes</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" tag="button" v-if="$store.state.token != ''">Logout</router-link> -->
  </div>
</template>

<script>
export default {
  name: 'app',
  data:() => ({
    active: ''
  }),
   computed: {
     currentRoute() {
       return this.$route.name;
     }
   }
}
</script>

<style>
@import "https://fonts.googleapis.com/icon?family=Material+Icons";
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap');
#app {
  font-family: 'Roboto', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}


div .vs-navbar {
  height: 70px !important;
}
</style>
