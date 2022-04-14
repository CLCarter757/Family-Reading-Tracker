<template>
  <div class="card" >
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    
    <!-- <button v-if="enableAdd" v-on:click.prevent="addToReadingList(book)">Add to Reading List</button> -->
  </div>
</template>

<script>
export default {
    name: 'search-book-card',
    props: {
        book: Object,
        enableAdd: {
            type: Boolean,
            default: false
        }
    },
    methods: {

    }
}
</script>

<style>
.card {
    border: 2px solid black;
    border-radius: 10px;
    width: 250px;
    height: 450px;
    margin: 20px;
}

.card.read {
    background-color: lightgoldenrodyellow;
}

.card .book-title {
    font-size: 1.5rem;
}

.card .book-author {
    font-size: 1rem;
}

.img {
    height: 200px;
}
</style>