<template>
    <section>
        <h1>{{this.familyMember.name}}'s Library</h1>
        <h2>Currently Reading</h2>
        <div class="reading-list">
            <book-card v-for="book in reading()" v-bind:key="book.isbn" :book="book"/>
        </div>
        <h2>Wish List </h2>
        <div class="reading-list">
            <book-card v-for="book in wishList()" v-bind:key="book.isbn" :book="book"/>
        </div>        
        <h2>Finished Reading</h2>
        <div class="reading-list">
            <book-card v-for="book in completed()" v-bind:key="book.isbn" :book="book"/>
        </div>
    </section>
</template>

<script>
import BookCard from '@/components/BookCard.vue';
import BookService from '../services/BookService.js';
import FamilyService from '../services/FamilyService.js';

export default {
    name: 'member',
    components: {
        BookCard
    },
    data() {
        return {
            memberId: this.$route.params.id,
            familyMember: '',
        }
    },

    methods: {
        retrieveFamilyBooks() {
            BookService.listFamilyBooks()
                .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("SET_FAMILY_BOOKS", response.data)
                    }
                })
        },
        retrieveFamily() {
          FamilyService.getFamily()
              .then(response => {
                  this.$store.commit('SET_FAMILY', response.data);
              });
      },
        wishList() {
            return this.$store.state.familyBooks.filter(
                book => book.userId == this.memberId && book.minutes === 0 && book.completed === false
            );
        },
        completed() {
            return this.$store.state.familyBooks.filter(
                book => book.userId == this.memberId && book.completed == true
            );
        },
        reading() {
            return this.$store.state.familyBooks.filter(
                book => book.userId == this.memberId && book.minutes > 0 && book.completed == false
            );
        },
        setUser() {
            this.familyMember = this.$store.state.family.find(
                person => person.id == this.memberId);
        }
    },
 created() {
        this.retrieveFamilyBooks();
        this.retrieveFamily();
        this.setUser();
    },
}
</script>

<style>
.reading-list {
    display:flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
</style>
