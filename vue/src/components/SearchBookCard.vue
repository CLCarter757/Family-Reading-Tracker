<template>
  <div class="card" >
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    <button v-show="inWishList" @click="removeFromWishList">Remove from Wish List</button>
    <button v-show="!inWishList" @click="addToWishList">Add to Wish List</button>

    <div>
        
    </div>
  </div>
</template>

<script>
import BookService from '../services/BookService';
export default {
    name: 'search-book-card',
    props: {
        book: Object,
    },
    data() {
        return {
            inWishList: this.checkAlreadyInWishList()
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
            const length = this.$store.state.userBooks.filter(
                book => book.isbn === newBook.isbn).length;
            if(length > 0){
                alert(`${ newBook.title } is already in your Wish List.`)
            } else {
            BookService.addToWishList(newBook)
                .then(response => {
                    if(response.status === 200) {
                        alert(`${newBook.title} has been added to your Wish List!`)
                        // this.$router.go();
                        this.inWishList = true;
                    }
                })
            }
        },
        removeFromWishList() {
            BookService.deleteFromWishList(this.book.isbn)
                .then(response => {
                    if(response.status === 200) {
                        alert(`${this.book.title} has been removed from Wish List.`);
                        this.inWishList = false;
                    }
                })
        },
        handleWishListButton() {
            if(this.inWishList==false) {
                this.addToWishList;
            } 
            else {
                this.removeFromWishList;
            }
            this.inWishList = !this.inWishList;
        },
        checkAlreadyInWishList() {
            const length = this.$store.state.userBooks.filter(
                b => b.isbn === this.book.isbn).length;
            return length > 0;
        }  
    },
    computed: {

    }
}
</script>

<style scoped>

.card {
    border-radius: 10px;
    backdrop-filter: hue-rotate(30px);
    background-color: rgba(255, 255, 255, 0.5);
    box-shadow: 0 2px 20px rgba(0, 0, 0, 0.25);
    border: 1px solid rgba(255, 255, 255, 0.3);
    width: 250px;
    height: 465px;
    margin: 30px;
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