<template>
  <div class="card" v-bind:class="{ favorited: book.favorited }">
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    <div class="button-container">
        <router-link :to="{ path:'/form', params:{ userBookId:this.book.userBookId, reader:this.book.userId } }" tag="button">Log Reading</router-link>
    </div>
    <div class="button-container">
        <button class="mark-read" v-on:click.prevent="setCompleted(true)" v-if=" ! book.completed">Mark Read</button>
        <button class="mark-unread" v-on:click.prevent="setCompleted(false)" v-if="book.completed">Mark Unread</button>
    </div>
    <div class="icons">
        <input class="fav" type="image" v-on:click.prevent="setFavorited(false)" v-if="book.favorited" 
            src="https://icons.iconarchive.com/icons/paomedia/small-n-flat/256/star-icon.png"/>
        <input class="fav" type="image" v-on:click.prevent="setFavorited(true)" v-if="! book.favorited" 
            src="https://cdn-icons-png.flaticon.com/512/1828/1828970.png"/>
        <input class="deleted" type="image" v-on:click.prevent="deleteBook" 
            src="https://cdn-icons.flaticon.com/png/512/484/premium/484662.png?token=exp=1650248121~hmac=a35d89fcfa081919e0a644fa2a9ff467"/>
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
                favorited: this.book.favorited,
                completed: value
            };
            BookService.updateBook(updatedBook)
                .then(response => {
                    if(response.status === 200) {
                        this.$forceUpdate();
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, "updating");
                    this.$router.go();
                });
        },
        setFavorited(value) {
            const updatedBook = {
                userId: Number(this.$store.state.user.id),
                username: this.$store.state.user.username,
                title: this.book.title,
                authors: this.book.authors,
                isbn: this.book.isbn,
                minutes: this.book.minutes,
                description: this.book.description,
                bookCoverUrl: this.book.bookCoverUrl,
                completed: this.book.completed,
                favorited: value
            };
            BookService.updateBook(updatedBook)
                .then(response => {
                    if(response.status === 200) {
                        this.$forceUpdate();
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, "updating");
                    this.$router.go();
                });
        },
        deleteBook() {
            BookService.deleteBook(this.book.isbn)
                .then(response => {
                    if(response.status === 200) {
                        alert(`${this.book.title} has been removed from your library.`)
                        this.$router.go();
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, "deleting");
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

.card.favorited {
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
.fav {
    height: 30px;
    padding: 10px;
}
.deleted {
    height: 30px;
    padding: 10px;
}
.icons{
    display: flex;
    justify-content: space-between;
}
</style>