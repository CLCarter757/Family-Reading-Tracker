<template>
  <form class="log-reading-form">
    <!-- <div>
        <input class="title-input" type="text" placeholder="Title" v-model="readingActivity.title" />
    </div>  
    <div>
        
        <input class="author-input" type="text" placeholder="Author" v-model="readingActivity.author" />
    </div> 
    <div>
        <input class="isbn-input" type="text" placeholder="ISBN" v-model="readingActivity.isbn" />
    </div>   -->
    <div>
        <input class="format-input" type="text" placeholder="Format" v-model="readingActivity.format" />
    </div>      
    <div>
        <input class="minutes-input" type="text" placeholder="Minutes" v-model="readingActivity.minutes" />
    </div>    
    <div>
        <input class="notes-input" type="text" placeholder="Notes" v-model="readingActivity.notes" />
    </div>    
    <div>
        <button>Save</button>
    </div>
  </form>
</template>

<script>
export default {
    name: "log-form",
    data() {
        return {
            book: {
                title: '',
                author: '',
                isbn: '',
                format: '',
                minutes: '',
                notes: ''
            },
            readingActivity: {
                userBookId: '',
                dateCreated: '',
                reader: '',
                format: '',
                time: '',
                notes: ''
            }
        }
    },
    methods: {
        
    }
}
</script>

<style>
.new-book-form {
    margin: 20px;
}

.new-book-form input, .new-book-form button {
    margin: 10px;
    font-size: 1rem;
}
</style>