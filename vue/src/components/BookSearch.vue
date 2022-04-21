<template>
    <div class="book-search">
        <form @submit.prevent="submitSearch">
            <div class="control">

            </div>
            
            <div class="control">
                
              <div class="control">
                <p>  
                    <span class="select ">
                        <select v-model="searchType">
                            <option value="title">Title</option>
                            <option value="author">Author</option>
                            <option value="isbn">ISBN</option>
                        </select>
                    </span>                        
                
                  <input  class="input" style="width: 45%" type="text" 
                     placeholder="Search . . ." v-model="searchText" required>
                  <button class="button">Submit</button>
                </p>
                
              </div>
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
.select {
    display: inline;
}
</style>
