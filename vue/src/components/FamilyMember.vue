<template>
  <div>
    <div class="center list flex-column">
      <div class="family-card flex-row open">
        <div class="user-info flex-column info">
          <div class="subtitle is-2 is-underlined" >{{ person.name }}</div>
          <div class="subtitle is-5">{{person.username}}</div>
          <div class="subtitle is-size-6">
            {{ person.familyRole == "ROLE_PARENT" ? "Parent" : "Child" }}
          </div>
           <i class="fa-solid fa-x" @click="deleteMember(person)"/>            
        </div>
        <div class="flex-column info">
          <router-link class="is-info" :to="`/family/${person.id}`">
              {{person.name}}'s Library </router-link><br>
          <div v-if="this.$store.state.user.familyRole == 'ROLE_PARENT' || this.$store.state.user.id == person.id" class="hidden bottom">
            <h2 class="subtitle is-6">Recent Reading:</h2>
            <h2>Date: {{lastActivity.dateCreated}}</h2>
            <h2> {{lastActivity.format}}</h2>
            <h2> Minutes: {{lastActivity.time}}</h2>
            
          </div>  
        </div>
         <div class="flex-column info">
          <img v-bind:bookCover="getBookCover(lastActivity)" :src="bookCover" alt="Book Cover">
        </div>
        
        <!-- <img
          src="http://i.harperapps.com/covers/9780008108298/y450-293.jpg"
          class="book"
        /> -->
      </div>
    </div>

  </div>
</template>

<script>
import BookService from "../services/BookService";
import FamilyService from '../services/FamilyService.js';
export default {
  props: ["person"],
  data() {
    return {
      readingActivity: [],
      lastActivity: {
       
      },
      lastBookRead: {},
      bookCover: '',
    };
  },
  created() {
    this.getReadingActivity();
  },
  computed: {
         
  },

  methods: {
    getReadingActivity() {
      BookService.getReadingActivities(this.person.id).then((response) => {
        if (response.status === 200) {
          this.readingActivity = response.data;
          this.lastActivity = this.readingActivity.pop();
          
        }
      });
    },
    getBookById(){
      BookService.getBookById(this.lastActivity.userBookId).then(response=>{
        this.lastBookRead = response.data
      })
    },
    
    deleteMember(person) {
        if(confirm(`Remove this person from your family?`))
        FamilyService.deleteMember(this.$store.state.user.familyId, person.id)
          .then(response => {
            if(response.status === 200) {
              this.$store.commit("REMOVE_FAMILY_MEMBER", response.data)
              // this.$router.go();
            }
          })
      },
    getBookCover(activity) {
        const cover = this.$store.state.familyBooks.find(book => {
          return book.userBookId == activity.userBookId
        });
        this.bookCover = cover.bookCoverUrl;
      } 
   },   
};
</script>

<style scoped>
.flex-row {
  display: flex;
  flex-flow: row;
  align-items: center;
}
.flex-column {
  display: flex;
  flex-flow: column;
  align-items: center;
  justify-content: center;

}

.list {
  border-radius: 3px;
  overflow: hidden;
}
.family-card {
  background-color: rgba(255, 255, 255, 0.514);
  width: 720px;
  height: 250px;
  margin-bottom: 10px;
  perspective: 600px;
  transition: all 0.1s;
  background-color: lighten(8%);
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  height: 90px;
  
}
.bottom {
  overflow: hidden;
  /* width: 200px; */
  /* margin-top: 10px; */
  height: 100%;
  overflow: visible;
}
.open {
  padding: 30px;
  height: auto;
}
.book {
  transform: rotateY(-50deg);
  box-shadow: -10px 10px 10px 2px rgba(0, 0, 0, 0.2), -2px 0px 0px 0px #888;
  transition: all 0.5s;
  transition-delay: 0.05s;
}

.book {
  width: 120px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.3);
  overflow: hidden;
}
.info {
  min-width: 200px;
  padding: 0px 30px;
  font-weight: bold;
}
.group {
  display: flex;
  justify-content: center;
  align-items: center;
}
.family-card.flex-row.open {
  min-height: 265px;
}
.hidden.bottom{
  margin-bottom: auto;
}
.user-info{
    min-width: 250px;
    align-items: center;
    margin: 10px;
}
.user-info.flex-column.info{
  align-items:center;
}
i{
  position: relative;
  right: 120px;
  bottom: 75px;
}
</style>