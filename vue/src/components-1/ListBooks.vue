<template>
    <div class="book-list">
        <book-card v-for="book in $store.state.completed" v-bind:key="book.isbn" :book="book"/>
    </div>
</template>

<script>
import BookCard from '@/components/BookCard.vue';

export default {
    name: 'books-completed',
    components: {
        BookCard
    },
    data() {
        return {
            books: []
        }
    },
    methods: {
        
    }
}
</script>
