<template>
  <div id="app">
    <div id="nav" class="navbar is-info">      
      <router-link class="nav button is-info is-focused" v-bind:to="{ name: 'home' }" tag="button">My Library</router-link>&nbsp;&nbsp;
      <router-link class="nav button is-info is-focused" v-bind:to="{ name: 'search' }" tag="button" v-if="$store.state.token != ''">Find Books</router-link>
      <router-link class="nav button is-info is-focused" v-bind:to="{ name: 'family' }" tag="button" v-if="$store.state.token != ''">Family</router-link>&nbsp;&nbsp;
      <router-link class="nav button is-info is-focused" v-bind:to="{ name: 'prizes' }" tag="button" v-if="$store.state.token != ''">Prizes</router-link>&nbsp;&nbsp;
      <router-link class="nav button is-primary is-info" v-bind:to="{ name: 'logout' }" tag="button" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Kanit', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0px;
}

#nav {
  display: flex;
  justify-content: center;
  gap: 6%;
  margin-bottom: 50px;
}

#nav > .nav {
  margin: 2px;
}
</style>
