<template>
    <div>
        <form @submit.prevent="submitSearch">
            <select name="search" id="drop-search" v-model="searchType">
                <option value="title">Title</option>
                <option value="author">Author</option>
                <option value="isbn">ISBN</option>
            </select>
            <input type="text" v-model="searchText"/>
            <button id="clear">Clear</button>
            <input id="search" type=submit value="Search"/>            
        </form>

        <h2>Results</h2>
        <div class="reading-list">
            <search-book-card v-for="book in $store.state.searchBook" v-bind:key="book.isbn" :book="book"/>
        </div>
        
    </div>

    
</template>

<script>
import BookService from '../services/BookService.js';
import SearchBookCard from '../components/SearchBookCard.vue';

export default {
    name: 'book-search',
    components: {
         SearchBookCard
      
    },
    data() {
        return {
           searchType: '',
           searchText: '', 
        }
    },
    methods: {
        submitSearch() {
            BookService
                .searchBooks(this.searchType, this.searchText)
                .then(response => {
                    this.$store.commit("ADD_SEARCH_BOOK", response.data);
                })
            console.log("test");
        }
    }
    
}

</script>

<style>

</style>
