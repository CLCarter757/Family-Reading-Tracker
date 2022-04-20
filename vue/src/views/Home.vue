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
