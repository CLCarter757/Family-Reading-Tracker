<template>
  <div class="home">
    <h1 class="title">{{ this.$store.state.user.name }}'s Library</h1>
    <list-books/>
  </div>
</template>

<script>
import ListBooks from '../components/ListBooks.vue';

export default {
  name: "home",
  components: {
    ListBooks
  }
};
</script>

<style>
body {
    background-image: url(https://images.unsplash.com/photo-1586603328377-f23a9d85fa35?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1974&q=80);
    height:500vh;
    width: 100vw;
    background-repeat: repeat-x;
    
}

h1 {
  font-size: 100px;
  font-weight: 600;
  color: #028090;
}
</style>