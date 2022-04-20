<template>
    <div>
        <form @submit.prevent="submitSearch">
            <div class="control">
                <div class="select">
                    <select>
                    <option value="title">Title</option>
                    <option value="author">Author</option>
                    <option value="isbn">ISBN</option>
                    </select>
                </div>
            </div>
            <p class="control has-icons-left">
                <input class="input " type="text" v-model="searchText" placeholder="Search">
                <span class="icon is-left">
                    <i class="fas fa-search" aria-hidden="true"></i>
                </span>
            </p>
            <div class="control">
                <button class="button is-primary">Submit</button>
            </div>            
        </form>

        <section v-show="searchResults">
            <h2 v-show="submitSearch">Results</h2>
            <div class="reading-list">
                <search-book-card v-for="book in $store.state.searchBook" v-bind:key="book.isbn" :book="book"/>
            </div>
        </section>
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
           searchResults: false
        }
    },
    methods: {
        submitSearch() {
            BookService
                .searchBooks(this.searchType, this.searchText)
                .then(response => {
                    this.$store.commit("ADD_SEARCH_BOOK", response.data);
                })
            this.searchResults = true;
        },
        addBookToLibrary() {
            
        }
    }
    
}

</script>

<style>

</style>
