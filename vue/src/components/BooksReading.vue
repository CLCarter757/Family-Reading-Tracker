<template>
    <section>
        <h2>Currently Reading</h2>
        <div class="reading-list">
            <book-card v-for="book in $store.state.reading" v-bind:key="book.isbn" :book="book"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';

export default {
    name: 'reading-list',
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