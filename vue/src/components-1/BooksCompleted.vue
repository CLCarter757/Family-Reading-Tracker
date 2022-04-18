<template>
    <section>
        <h2>Books Finished</h2>
        <div class="reading-list">
            <book-card v-for="book in $store.state.completed" v-bind:key="book.isbn" :book="book"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';

export default {
    name: 'books-completed',
    components: {
        BookCard
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