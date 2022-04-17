<template>
    <div>
        <h1>What do you want to read?</h1>
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
