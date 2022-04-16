<template>
  <div class="card" >
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    <button @click="addToWishList">Add to Wish List</button>
    <!-- working -->
  </div>
</template>

<script>
import BookService from '../services/BookService';
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
        addToWishList() {
            const newBook = {
                userId: Number(this.$store.state.user.id),
                username: this.$store.state.user.username,
                title: this.book.title,
                authors: this.book.authors,
                isbn: this.book.isbn,
                description: this.book.description,
                bookCoverUrl: this.book.bookCoverUrl
            };
            BookService.addToWishList(newBook)
                .then(response => {
                    if(response.status === 201) {
                        this.$router.push('/mybooks')
                    }
                })
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