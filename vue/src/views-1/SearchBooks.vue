<template>

<section class="search">
    
     <h1>Search For Books</h1>
    <div class='main'>
        <book-search></book-search>
        
<div class="center con-pagination">
      <vs-pagination not-arrows v-model="page" :length="10" />
      <!-- 如果想要不能点击，那么在里面加一个disabled  -->
    </div>
    
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

section {
    background-image: url(https://images.unsplash.com/photo-1527176930608-09cb256ab504?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2074&q=80);
    height:100vh;
    width:100vw;
}

</style>
