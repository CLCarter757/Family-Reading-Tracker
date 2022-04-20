<template>
  <div class="home">
    <span class="icon"><i class="fas fa-train" ></i></span>
    <h1>{{ this.$store.state.user.name }}'s Library</h1>
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
