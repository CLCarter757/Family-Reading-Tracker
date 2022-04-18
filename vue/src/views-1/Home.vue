<template>

  <div id="padding-scroll-content" class="home img child" >
    <books-reading />
    <books-completed />
    <wish-list />
  </div>
</template>

<script>
import BooksReading from '../components/BooksReading.vue';
import BooksCompleted from '../components/BooksCompleted.vue';
import WishList from '../components/WishList.vue';

export default {
  name: "home",
  components: {
    BooksReading,
    BooksCompleted,
    WishList
  }
};
</script>

<style scoped>
 .img {
 background-image: url(https://images.unsplash.com/photo-1586603328377-f23a9d85fa35?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1974&q=80);
 height: 200vh;
 width: 100vw;
 }

 .home {
   position: absolute;
   top: 60px;
 }
</style>