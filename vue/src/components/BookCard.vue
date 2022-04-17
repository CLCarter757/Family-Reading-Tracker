<template>
  <div class="card" v-bind:class="{ read: book.completed }">
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    <div class="button-container">
        <router-link to="/form" tag="button">Log Reading</router-link>
    </div>
    <div class="button-container">
        <button class="mark-read" v-on:click.prevent="setCompleted(true)" v-if=" ! book.completed">Mark Read</button>
        <button class="mark-unread" v-on:click.prevent="setCompleted(false)" v-if="book.completed">Mark Unread</button>
    </div>
  </div>
</template>

<script>
import BookService from '../services/BookService';
export default {
    name: 'book-card',
    props: {
        book: Object,
    },
    
    methods: {
        setCompleted(value) {
            const updatedBook = {
                userId: Number(this.$store.state.user.id),
                username: this.$store.state.user.username,
                title: this.book.title,
                authors: this.book.authors,
                isbn: this.book.isbn,
                minutes: this.book.minutes,
                description: this.book.description,
                bookCoverUrl: this.book.bookCoverUrl,
                completed: value
            };
            BookService.updateBook(updatedBook)
                .then(response => {
                    if(response.status === 200) {
                        this.$forceUpdate();
                        console.log("test");
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, "updating");
                    this.$router.go();
                });
        },
        handleErrorResponse(error, verb) {
            if (error.response) {
                this.errorMsg =
                "Error " + verb + " book. Response received was '" +
                error.response.statusText +
                "'.";
            } else if (error.request) {
                this.errorMsg =
                "Error " + verb + " book. Server could not be reached.";
            } else {
                this.errorMsg =
                "Error " + verb + " book. Request could not be created.";
            }
            }
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