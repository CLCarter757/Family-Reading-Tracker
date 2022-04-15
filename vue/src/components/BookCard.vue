<template>
  <div class="card" v-bind:class="{ read: book.read }">
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'" />
    <h3 class="book-author">{{ book.author }}</h3>
    <div class="button-container">
        <router-link to="/form" tag="button">Log Reading</router-link>
    </div>
    <div class="button-container" v-if="! enableAdd">
        <button class="mark-read" v-on:click.prevent="setRead(true)" v-if=" ! book.read">Mark Read</button>
        <button class="mark-unread" v-on:click.prevent="setRead(false)" v-if="book.read">Mark Unread</button>
    </div>
    <button v-if="enableAdd" v-on:click.prevent="addToReadingList(book)">Add to Reading List</button>
  </div>
</template>

<script>
export default {
    name: 'book-card',
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
  background: linear-gradient(to right, rgb(60, 13, 20) 3px, rgba(255, 255, 255, 0.5) 5px, rgba(255, 255, 255, 0.25) 7px, rgba(255, 255, 255, 0.25) 10px, transparent 12px, transparent 16px, rgba(255, 255, 255, 0.25) 17px, transparent 22px);
  box-shadow: 0 0 5px -1px black, inset -1px 1px 2px rgba(255, 255, 255, 0.5);
  margin: auto;
  border-radius: 0px;
  width: 389px;
  height: 500px;
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