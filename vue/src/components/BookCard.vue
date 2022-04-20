<template>
  <div class="card" v-bind:class="{ favorited: book.favorited }">
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    
    <div class="button-container">
        <button class="mark-read button is-primary" v-on:click.prevent="setCompleted" v-if="(this.$store.state.user.familyRole == 'ROLE_PARENT' || this.$store.state.user.id == book.userId) && !book.completed">Mark Read</button>
        <div class="rate" v-if="book.completed">
            <input :checked="book.rating == 5" :class="{'star-color':book.rating >= 5}" type="radio" id="star5" name="rate" value="5"/>
            <label :class="{'star-color':book.rating >= 5}" for="star5" title="text" @click.prevent="setRating(5)">5 stars</label>
            <input :checked="book.rating == 4" :class="{'star-color':book.rating >= 4}" type="radio" id="star4" name="rate" value="4"/>
            <label :class="{'star-color':book.rating >= 4}" for="star4" title="text" @click.prevent="setRating(4)">4 stars</label>
            <input :checked="book.rating == 3" :class="{'star-color':book.rating >= 3}" type="radio" id="star3" name="rate" value="3"/>
            <label :class="{'star-color':book.rating >= 3}" for="star3" title="text" @click.prevent="setRating(3)">3 stars</label>
            <input :checked="book.rating == 2" :class="{'star-color':book.rating >= 2}" type="radio" id="star2" name="rate" value="2"/>
            <label :class="{'star-color':book.rating >= 2}" for="star2" title="text" @click.prevent="setRating(2)">2 stars</label>
            <input :checked="book.rating == 1" :class="{'star-color':book.rating >= 1}" type="radio" id="star1" name="rate" value="1"/>
            <label :class="{'star-color':book.rating >= 1}" for="star1" title="text" @click.prevent="setRating(1)">1 star</label>
        </div>
    </div>
    <div class="icons" v-if="this.$store.state.user.familyRole == 'ROLE_PARENT' || this.$store.state.user.id == book.userId">
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
    border: 2px solid black;
    border-radius: 10px;
    width: 250px;
    height: 450px;
    margin: 20px;
}

.favorited {
    background-color: rgb(255, 221, 227);
}

.book-title {
    font-size: 1.5rem;
}

.book-author {
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
    height: 60px;
    padding: 10px;
    cursor: pointer;
}
.icons{
    display: flex;
    justify-content: space-between;
}
button, input {
    cursor: pointer;
}
.button-container{
    display: flex;
    justify-content: center;
}

.rate {
    float: left;
    height: 46px;
    padding: 0 10px;
}
.rate:not(:checked) > input {
    position:absolute;
    top:-9999px;
}
.rate:not(:checked) > label {
    float:right;
    width:1em;
    overflow:hidden;
    white-space:nowrap;
    cursor:pointer;
    font-size:30px;
    color:#ccc;
}
.rate:not(:checked) > label:before {
    content: '★ ';
}
.rate > input:checked ~ label {
    color: gold;    
}
.rate:not(:checked) > label:hover,
.rate:not(:checked) > label:hover ~ label {
    color: gold;  
}
.rate > input:checked + label:hover,
.rate > input:checked + label:hover ~ label,
.rate > input:checked ~ label:hover,
.rate > input:checked ~ label:hover ~ label,
.rate > label:hover ~ input:checked ~ label {
    color: gold;
}
.star-colored {
    color: gold;
}
</style>