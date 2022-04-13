<template>
    <section>
        <h2>Reading List</h2>
        <div class="reading-list">
            <book-card :book="book" :key="book.isbn"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';

export default {
    name:"reading-list",
    components: {
        BookCard
    }
}
</script>

<style>
.best-seller-list {
    display:flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
</style>
