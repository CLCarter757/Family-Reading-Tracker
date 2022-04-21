<template>
  <div>
    <table class="table">
        
        <thead>
            <tr>
            <th>Title</th>
            <th>Date</th>
            <!-- <th>Format</th> -->
            <th>Minutes</th>
            <th>Notes</th>
        </tr>
        </thead>
        <tbody>
            <tr v-for="readingActivity in this.$store.state.readingActivity" :key="readingActivity.recordId">
                <td  v-bind:bookTitle="getBookTitle(readingActivity)">{{ bookTitle }}</td>
                <td>{{readingActivity.dateCreated}}</td>
                <!-- <td>{{readingActivity.format}}</td> -->
                <td>{{readingActivity.time}}</td>
                <td>{{readingActivity.notes}}</td>
            </tr>
        </tbody>
    </table>
  </div>
</template>

<script>
import BookService from '../services/BookService.js';

export default {
    name: "reading-activity-list",
    data() {
        return {
            bookTitle: '',
        }
    },
    methods: {
        getBookTitle(activity) {
            const activityBook = this.$store.state.userBooks.find(book => {
                return book.userBookId == activity.userBookId
            });
            this.bookTitle = activityBook.title;
        },
        setReadingActivity() {
            console.log("test");
            BookService.getReadingActivities(this.$store.state.user.id)
                .then(response => {
                    
                    if(response.status === 200) {
                        this.$store.commit("SET_READING_ACTIVITY", response.data)
                    }
                })
        } 
    },
    created() {
        this.setReadingActivity();
    }
}
</script>

<style>

</style>