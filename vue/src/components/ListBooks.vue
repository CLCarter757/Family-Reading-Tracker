<template>
    <section>
        <h2>Wish List</h2>
        <div class="reading-list">
            <book-card v-for="book in wishList" v-bind:key="book.isbn" :book="book"/>
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
            books: []
        }
    },

    methods: {
        retrieveBooks() {
            BookService.listMyBooks()
                .then(response => {
                    this.$store.commit("SET_USER_BOOKS", response.data);
                });
        },
        wishList() {
            return this.$store.state.userBooks.filter(
                book => book.minutes === 0
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
    },
}
</script>

<style>
.reading-list {
    display:flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
</style>
