<template>
  <!-- <div class="card" >
    <h2 class="book-title">{{ book.title }}</h2>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
    <h3 class="book-author">{{ book.authors }}</h3>
    
    <button v-if="enableAdd" v-on:click.prevent="addToReadingList(book)">Add to Reading List</button>
  </div> -->
  <div v-bind:class="{ read: book.read }">
      <vs-tooltip color="#9369a8" border-thick interactivity>
      <vs-card class="card">
          <template #title>
    <h3>{{ book.title.length > 18 ? book.title.substring(0,15) + '...' : book.title }}</h3>
          </template>
          <template #img>
    <img class="img" v-if="book.isbn" v-bind:src="book.bookCoverUrl" />
          </template>
          <template #text>
    <h4 class="book-author">{{ book.authors }}</h4>
    </template>
    <template #interactions>
    <div class="button-container">
        <vs-button size="small" to="/form">Log Reading
        </vs-button>
    </div>
    <div v-if="!enableAdd">
        <!-- <vs-button size="small" class="mark-read" v-on:click.prevent="setRead(true)" v-if=" ! book.read" >Mark Read</vs-button> 
        <vs-button class="mark-unread" v-on:click.prevent="setRead(false)" v-if="book.read">Mark Unread</vs-button> -->
    </div>
    <vs-button v-if="enableAdd" v-on:click.prevent="addToReadingList(book)">Add to My Library</vs-button>
    </template>
      </vs-card>
      <template #tooltip shadow interactivity>
          <p>
         {{book.details}}
         </p>
      </template>
      
      </vs-tooltip>
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
        // addToReadingList(book) {
        //  return book;
        //  }
    }
}
</script>

<style>


.card.read {
    background-color: lightgoldenrodyellow;
}

.card .book-title {
    font-size: 1.5rem;
}

.card .book-author {
    font-size: 0.8rem;
}

/* .img {
    height: 200px;
} */
</style>