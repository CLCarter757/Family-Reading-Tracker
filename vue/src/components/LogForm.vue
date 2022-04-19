<template>

    <div>
        <p>Reading Activity for {{ this.$route.params.title }}</p>

    
  <form class="log-reading-form" v-on:submit.prevent = "logReading">
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
        <input class="date-input" type="date" v-model="readingActivity.date" />
    </div>
    <div>
        <form>
        <select name = "format" class="format-input" placeholder="Format" v-model="readingActivity.format" >
            <option value="" disabled selected hidden>Format</option>
            <option value="Paper">Paper</option>
            <option value="Digital">Digital</option>
            <option value="Audio Book">Audio Book</option>
            <option value="Read-Aloud (Reader)">Read-Aloud (Reader)</option>
            <option value="Read-Aloud (Listener)">Read-Aloud (Listener)</option>
            <option value="Other">Other</option>
        </select>
        </form>
    </div>      
    <div>
        <input class="time-input" type="number" placeholder="Minutes" v-model="readingActivity.time" />
    </div>    
    <div>
        <input class="notes-input" type="text" placeholder="Notes" v-model="readingActivity.notes" />
    </div>    
    <div>
        <button>Save</button>
    </div>
  </form>
    </div>
</template>

<script>
import BookService from '../services/BookService';
export default {
    name: "log-form",
    props: {
        book: Object
    },
    data() {
        return {
            readingActivity: {
                date: '',
                format: '',
                time: '',
                notes: ''
            }
        }
    },
    methods: {
        logReading() {
            const newReadingActivity = {
                userBookId: this.$route.params.userBookId,
                dateCreated: this.readingActivity.date,
                reader: this.$route.params.userId,
                format: this.readingActivity.format,
                time: Number(this.readingActivity.time),
                notes: this.readingActivity.notes
            }


            
            BookService.submitReadingActivity(this.$route.params.userId, newReadingActivity).then((response) => {
                if(response.status === 201) {
                    alert('Reading Activity Logged!')

                    this.readingActivity = {
                    date: '',
                    format: '',
                    time: '',
                    notes: ''
                    };
                    this.$router.push('/');
                    }
                    this.$router.go();
            })
            
            

        }
        
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