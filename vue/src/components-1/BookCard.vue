<template>

      <vs-tooltip color="#9369a8" border-thick interactivity>
      <vs-card class="card">
          <template #title>
    <h3>{{ book.title.length > 18 ? book.title.substring(0,15) + '...' : book.title }}</h3>
          </template>
          <template #img>
    <img class="img" v-if="book.isbn" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'" />
          </template>
          <template #text>
    <h4 class="book-author">{{ book.author }}</h4>
    </template>
    <template #interactions>
    <div class="button-container">
        <vs-button size="small" to="/form">Log Reading
        </vs-button>
    </div>
    <div v-if="! enableAdd">
        <vs-button size="small" class="mark-read" v-on:click.prevent="setRead(true)" v-if=" ! book.read" >Mark Read</vs-button> 
        <vs-button class="mark-unread" v-on:click.prevent="setRead(false)" v-if="book.read">Mark Unread</vs-button>
    </div>
    <vs-button v-if="enableAdd" v-on:click.prevent="addToReadingList(book)">Add to Reading List</vs-button>
    </template>
      </vs-card>
      <template #tooltip shadow interactivity>
          <p>
         {{book.details}}
         <i class="fa fa-thumbs-up" aria-hidden="true"></i>
         </p>
      </template>
      </vs-tooltip>
  
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
    width: 200px;
    margin: 20px;
    
}
.card.read {
    background-color: lightgoldenrodyellow;
}

.img {
    size: 50%;
}




       

</style>