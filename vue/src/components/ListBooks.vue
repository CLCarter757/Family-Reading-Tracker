<template>
    <section>
        <h2 class ="subtitle has-text-weight-semibold is-underlined">Currently Reading</h2>
        <div class="reading-list">
            <book-card v-for="book in reading()" v-bind:key="book.isbn" :book="book"/>
        </div>
        <h2 class ="subtitle has-text-weight-semibold is-underlined">Wish List</h2>
        <div class="reading-list">
            <book-card v-for="book in wishList()" v-bind:key="book.isbn" :book="book"/>
        </div>        
        <h2 class ="subtitle has-text-weight-semibold is-underlined">Finished Reading</h2>
        <div class="reading-list">
            <book-card v-for="book in completed()" v-bind:key="book.isbn" :book="book"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';
import BookService from '../services/BookService';

export default {
    name: 'list-books',
    components: {
        BookCard
    },
    data() {
        return {
            
        }
    },

    methods: {
        retrieveBooks() {
            BookService.listMyBooks()
                .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("SET_USER_BOOKS", response.data);
                    }
                });
        },
        wishList() {
            return this.$store.state.userBooks.filter(
                book => book.minutes === 0 && book.completed === false
            );
        },
        completed() {
            return this.$store.state.userBooks.filter(
                book => book.completed == true
            );
        },
        reading() {
            return this.$store.state.userBooks.filter(
                book => book.minutes > 0 && book.completed == false
            );
        }
    },
    created() {
        this.retrieveBooks();
    }
}
</script>

<style>
.reading-list {
    display:flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
</style>
