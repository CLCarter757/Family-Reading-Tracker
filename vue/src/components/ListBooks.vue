<template>
    <section class="library">
        <div class="top">
            <div class="activity">
                <h2 class ="subtitle is-3 has-text-weight-semibold is-underlined">Reading Activity</h2>
                <reading-activity-list class="activity-table"></reading-activity-list>
            </div>
            <div class="reading-list">
                <h2 class ="subtitle is-3 has-text-weight-semibold is-underlined">Currently Reading</h2>
                <book-card v-for="book in reading()" v-bind:key="book.isbn" :book="book"/>
            </div>
        </div>
        <h2 class ="subtitle is-3 has-text-weight-semibold is-underlined">Wish List</h2>
        <div class="reading-list">
            <book-card v-for="book in wishList()" v-bind:key="book.isbn" :book="book"/>
        </div>        
        <h2 class ="subtitle is-3 has-text-weight-semibold is-underlined">Finished Reading</h2>
        <div class="reading-list">
            <book-card v-for="book in completed()" v-bind:key="book.isbn" :book="book"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';
import BookService from '../services/BookService';
import ReadingActivityList from '../components/ReadingActivityList.vue'

export default {
    name: 'list-books',
    components: {
        BookCard,
        ReadingActivityList
    },
    data() {
        return {
            
        }
    },

    methods: {
        retrieveBooks() {
            BookService.listMyBooks()
                .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("SET_USER_BOOKS", response.data);
                    }
                });
        },
        wishList() {
            return this.$store.state.userBooks.filter(
                book => book.minutes === 0 && book.completed === false
            );
        },
        completed() {
            return this.$store.state.userBooks.filter(
                book => book.completed == true
            );
        },
        reading() {
            return this.$store.state.userBooks.filter(
                book => book.minutes > 0 && book.completed == false
            );
        }
    },
    created() {
        this.retrieveBooks();
    }
}
</script>

<style>
.reading-list {
    display:flex;
    justify-content: space-evenly;
    
    
}
hr.solid{
    border-top: 3px solid #bbb;
    width: 50%;
    justify-content: center;
}
.top {
    display: flex;
    align-items: center;
    margin: auto;
}
.activity {
    display: flex;
    flex-direction: column;
    /* justify-content: space-between; */
    margin: 40px;
}
.library {
    margin: 20px;
}
h2 {
    margin-top: 20px;
}
</style>
