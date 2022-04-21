<template>
  <div>
    <div class="center list flex-column">
      <div class="family-card flex-row open">
        <div class="flex-column info">
          <div class="title">{{ person.name }}</div>
          <div >{{person.username}}</div>
          <div >
            {{ person.familyRole == "ROLE_PARENT" ? "Parent" : "Child" }}
          </div><br>

           <router-link :to="`/family/${person.id}`">
              Currently Reading </router-link>

          <div  
            v-if="this.$store.state.user.familyRole == 'ROLE_PARENT' || this.$store.state.user.id == person.id" class="hidden bottom">
            <i class="fa-solid fa-x" @click="deleteMember(person)"/> 
          </div>              
        </div>
        <div class="flex-column info">
          <h3>Most Recent Reading </h3>
          <h2>Date: {{lastActivity.dateCreated}}</h2>
          <h2> {{lastActivity.format}}</h2>
            <h2> Duration:{{lastActivity.time}}</h2>
        
          <div class="hidden bottom">
            
            
            <!-- <button class="simple" >Remove</button> -->
          </div>
          
        </div>
         <div class="flex-column info">
          <h3>Notes </h3>
          <h2></h2>
          <h2> </h2>
            <h2></h2>
            
        

          
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
      lastBookRead: {}
    };
  },
  created() {
    this.getReadingActivity();
  },
  computed: {},

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
  background-color: rgba(255, 255, 255, 0.5);
  width: 720px;
  margin-bottom: 10px;
  perspective: 600px;
  transition: all 0.1s;
  background-color: lighten(8%);
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  height: 90px;
  border-radius: 10px;
}
.bottom {
  height: 0px;
  overflow: hidden;
  width: 200px;
  font-size: 12px;
  color: #777;
  font-weight: normal;
}
.open {
  padding: 30px;
  height: auto;
}
.bottom {
  margin-top: 10px;
  height: 100%;
  overflow: visible;
}
.book {
  transform: rotateY(-50deg);
  box-shadow: -10px 10px 10px 2px rgba(0, 0, 0, 0.2), -2px 0px 0px 0px #888;
  transition: all 0.5s;
  transition-delay: 0.05s;
}
.info {
  transform: translate(0, -10px);
}
.members {
  padding: 15px 20px;
  border-radius: 4px;
  align-self: flex-start;
}

button.simple {
  cursor: pointer;
  color: #ccc;
  border: none;
  outline: none;
  border-radius: 4px;
  background-color: #1ea94b;
  padding: 15px 20px;
  font-family: "Montserrat";
  font-weight: bold;
  transition: all 0.1s;
}
button.simple:hover {
  box-shadow: 0px 15px 20px -5px rgba(0, 0, 0, 0.3);
  transform: translate(0, -2px);
}

.book {
  transition: all 0.5s;
  width: 120px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.3);
  overflow: hidden;
}
.info {
  transition: all 0.2s;
  min-width: 200px;
  padding: 0px 30px;
  font-family: "Montserrat";
  font-weight: bold;
}
.title {
  font-size: 2em;
  color: black;
  letter-spacing: 1px;
}
.author {
  font-size: 20px;
  font-weight: normal;
  color: #888;
}

.group {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>