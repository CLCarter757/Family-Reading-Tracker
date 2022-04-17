<template>

<section class="search">
    
     <h1>Search For Books</h1>
    <div class='main'>
        <book-search></book-search>
    
    </div>

    </section>
</template>

<script>
import BookSearch from '../components/BookSearch.vue';

export default {
    name: "search",
    components: {
        BookSearch
    }
};
</script>

<style scoped>
.main {
   height: 100vh;
   
}
 .search {
     left: 0;
     right: 0;
     margin: 0 auto;
   position: absolute;
   top: 60px;
 }

.img {
    background-image: url(https://cdn.pixabay.com/photo/2020/04/30/20/14/sky-5114501_960_720.jpg);
}

</style>
