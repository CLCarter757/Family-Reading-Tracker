<template>
    <div>
        <h1>Search For Books</h1>
        <book-search></book-search>
        
        
    </div>
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
