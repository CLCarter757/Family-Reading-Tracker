<template>
<section>
  <form class="log-reading-form" > 
      
    <div class="center content-inputs">
        <vs-input primary placeholder="Title" v-model="value" />
        <br>
        <vs-input success placeholder="Author" v-model="value2" />
        <br>
        <vs-input danger placeholder="ISBN" v-model="value3" />
        <br>
        <vs-input warn placeholder="Minutes" v-model="value4" />
        <br>
        <vs-input dark placeholder="Note" v-model="value5" />
        <br>
        <vs-input type="date" label="Start Date" v-model="value11" />
        <br>
        <vs-input type="date"  label="End Date" v-model="value12" />
        <!-- <input primary state="primary"  type="text" placeholder="Title" v-model="book.title" /> -->
    </div>  
<br>
    <div class="center">
        <vs-select placeholder="select" label="Format" v-model="value">
        <vs-option label="paper" value="6">
          Paper
        </vs-option>
        <vs-option label="digital" value="7">
          Digital
        </vs-option>
        <vs-option label="audiobook" value="8">
          Audio Book
        </vs-option>
        <vs-option label="read aloud" value="9">
          Read Aloud(Reader)
        </vs-option>
        <vs-option label="read aloud" value="10">
          Read Aloud(Listener)
        </vs-option>
        </vs-select>
    </div>
    <!-- <div>
        
        <input class="author-input" type="text" placeholder="Author" v-model="book.author" />
    </div> 
    <div>
        <input class="isbn-input" type="text" placeholder="ISBN" v-model="book.isbn" />
    </div>  
    
    <div>
        <input class="minutes-input" type="text" placeholder="Minutes" v-model="book.minutes" />
    </div>     -->
<!-- <div>
        <label for="format">Format: </label>
        <select name="format" id="format">
            
            <option value="paper">Paper</option>
            <option value="digital">Digital</option>
            <option value="audiobook">Audio book</option>
            <option value="read-aloud">Read-Aloud(Reader)</option>
            <option value="read-aloud">Read-Aloud(Listener)</option>
            <option value="other">Other</option>
        </select>
        <input class="format-input" type="text" placeholder="Format" v-model="book.format" />
    </div>      
    <div>
        <label for="start">Start Date:</label>
        <input type="date" id="start" name="reading-start"/>
        <label for="from-time">Start Time:</label>
        <input type="time" id="from-time" name="from-time"/>
        <br>
    
        <label for="start">End Date:</label>
        <input type="date" id="end" name="reading-end"/>
        <label for="end-time">End Time:</label>
        <input type="time" id="end" name="end-time"/>
    </div> -->

    <!-- <div>
        <input class="notes-input" type="text" placeholder="Notes" v-model="book.notes" />
    </div>     -->
    <div class="container">
        <vs-button color="#4361ee" class="submit center">Submit</vs-button>
        <!-- <vs-button color="danger" :active="currentRoute == 'home'" to="/"  id="my-library">My Library </vs-button> -->
      
    </div> 
  </form>
  </section>
</template>

<script>
export default {
    name: "log-form",
    // data() {
    //     return {
            // book: {
            //     title: '',
            //     author: '',
            //     isbn: '',
            //     format: '',
            //     minutes: '',
            //     date:'',
            //     notes: ''
            // }
            
        // }
    // },
    data:() => ({
        value: '',
        value2: '',
        value3: '',
        value4: '',
        value5: '',
        value6: '',
        value7: '',
        value8: '',
        value9: '',
        value10: '',
        value11:'',
        value12:''
      }),
    
    methods: {
        
    }
}
</script>

<style scoped>
.log-reading-form {
    margin: 20px;
   
}

.log-reading-form input, .log-reading-form button {
    margin: 10px;
    font-size: 1rem;
}

.log-reading-form.static{
    position: static;

}

form {
    display: inline-block;
}


section {
    background-image: url(https://images.unsplash.com/photo-1591951425600-d09958978584?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80);
    height: 100vh;
 width: 100vw;
}

.container {
    position: relative;
    justify-content: center;
    align-content: center;
}

</style>