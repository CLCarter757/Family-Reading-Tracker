<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }" tag="button">My Library</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'search' }" tag="button" v-if="$store.state.token != ''">Find Books</router-link>
      <router-link v-bind:to="{ name: 'family' }" tag="button" v-if="$store.state.token != ''">Family</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'prizes' }" tag="button" v-if="$store.state.token != ''">Prizes</router-link>&nbsp;&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" tag="button" v-if="$store.state.token != ''">Logout</router-link>
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
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 60px;
}
</style>
