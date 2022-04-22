<template>
  <div class="card  ">

    <h2 class="book-title">{{ book.title }}</h2>
    
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />

    <h3 class="book-author">{{ book.authors }}</h3>
    
    <div class="button-container">
        <button class="mark-read button is-success" v-on:click.prevent="setCompleted" v-if="(this.$store.state.user.familyRole == 'ROLE_PARENT' || this.$store.state.user.id == book.userId) && !book.completed">Mark Read</button>
        <div v-if="book.completed">
            <span  v-for="n in 5" :key="n">
                <i
                class="fa-star fa-2x ml-1"
                :class="{ fas: book.rating >= n, far: book.rating < n }"
                @click="setRating(n)"
                ></i>
            </span>
        </div>
    </div>
    <div class="iconz" v-if="this.$store.state.user.familyRole == 'ROLE_PARENT' || this.$store.state.user.id == book.userId">
        <input class="fav" type="image" v-on:click.prevent="setFavorited(false)" v-if="book.favorited" 
            src="/RedHeart.png"/>
        <input class="fav" type="image" v-on:click.prevent="setFavorited(true)" v-if="! book.favorited" 
            src="/EmptyHeart.png"/>
        <input v-if="book.minutes === 0" class="deleted" type="image" v-on:click.prevent="deleteBook" src="/DeleteIcon.png"/>
        <router-link class="deleted" :to="{ name:'form', params: {book} }" 
            tag="img" src="/LogBook.png">
        </router-link>
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
    data() {
        return {
            rating: null,
            isChecked: true
        }
    },
    
    methods: {
        setCompleted() {
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
                rating: this.book.rating,
                completed: true
            };
            if(confirm(`Click 'OK' to add ${ updatedBook.title } to your list of completed books? `)){
                BookService.updateBook(updatedBook)
                    .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("UPDATE_BOOK_COMPLETED", response.data);
                    }
                    })
                    .catch(error => {
                        this.handleErrorResponse(error, "updating");
                    })
            }
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
                rating: this.book.rating,
                favorited: value
            };
            BookService.updateBook(updatedBook)
                .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("UPDATE_BOOK_FAVORITED", response.data)
                    }
                })
                .catch(error => {
                    this.handleErrorResponse(error, "updating");
                });
        },
        setRating(value) {
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
                favorited: this.book.favorited,
                rating: value
            };
            BookService.updateBook(updatedBook)
                .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("UPDATE_BOOK_RATING", response.data)
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
        },
        awardCompletedPrize(){
            
        }
    }
}
</script>

<style>

.card {
    border-radius: 10px;
    backdrop-filter: hue-rotate(30px);
    background-color: rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 20px rgba(0, 0, 0, 0.25);
    border: 1px solid rgba(255, 255, 255, 0.3);
    width: 250px;
    height: 500px;
    margin: 30px;
}

.favorited {
    background-color: rgb(255, 221, 227);
}

.book-title {
    font-size: 1.5rem;
    display: -webkit-box;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    margin-bottom: 10px;
}

.book-author {
    font-size: 1rem;
    margin-bottom: 10px;
}

.img {
    height: 200px;
}
.fav {
    height: 50px;
    padding: 10px;
}
.deleted {
    height: 50px;
    padding: 10px;
    cursor: pointer;
}
.iconz{
    display: flex !important;
    justify-content: space-between !important;
    height: 50px;
    padding: 10px;
    cursor: pointer;
}
.fa-star {
    color: rgb(243, 210, 23);
    cursor: pointer;
}
.tooltip {
  min-width: 700px; 
}
</style>