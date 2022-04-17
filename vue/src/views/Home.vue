<template>
  <div class="home">
    <h1>My Library</h1>
    
    <!-- <books-reading />
    <books-completed />
    <wish-list /> -->
    <list-books/>

  </div>
</template>

<script>
// import BooksReading from '../components/BooksReading.vue';
// import BooksCompleted from '../components/BooksCompleted.vue';
// import WishList from '../components/WishList.vue';
import ListBooks from '../components/ListBooks.vue';

export default {
  name: "home",
  components: {
    // BooksReading,
    // BooksCompleted,
    // WishList
    ListBooks
  }
};
</script>
