<template>
    <section>
        <h2>Wish List</h2>
        <div class="reading-list">
            <book-card v-for="book in $store.state.list" v-bind:key="book.isbn" :book="book"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';

export default {
    name: 'wish-list',
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
