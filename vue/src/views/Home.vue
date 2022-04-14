<template>
  <div class="home">
    <h1>My Library</h1>
    
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
