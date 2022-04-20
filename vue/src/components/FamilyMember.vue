<template>
  <div>
    <div class="center list flex-column">
      <div class="card flex-row open">
        <div class="flex-column info">
          <div class="title">{{ user.name }}</div>
          <div class="author">{{ }}</div>
          <div class="author">
            {{ user.familyRole == "ROLE_PARENT" ? "Parent" : "Child" }}
          </div>
          <div class="hidden bottom summary"></div>
        </div>
        <div class="flex-column group">
          <div class="members">
            <span class="current">14</span> /
            <span class="max">30</span>
          </div>
          <div class="hidden bottom">
            <button class="simple" >Remove</button>
          </div>
        </div>
        <img
          src="http://i.harperapps.com/covers/9780008108298/y450-293.jpg"
          class="book"
        />
      </div>
    </div>
  </div>
</template>

<script>
import BookService from '../services/BookService';
export default {
  props: ["user"],
  data(){
      return{
          readingActivity:[]
      }
  },
   created() {
    this.getReadingActivity();
  },

  methods:{
      getReadingActivity(){
          BookService.getReadingActivities(this.user.id).then(response=>{

              if(response.status === 200) {
                  this.readingActivity = response.data
                  console.log('hello')
              }
          })
      }
  }
}  
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
}

.list {
  border-radius: 3px;
  overflow: hidden;
}
.card {
  background-color: silver;
  cursor: pointer;
  min-width: 700px;
  margin-bottom: 10px;
  perspective: 600px;
  transition: all 0.1s;
  background-color: lighten(8%);
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  height: 90px;
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
  font-size: 3em;
  color: #fff;
  letter-spacing: 1px;
}
.author {
  font-size: 20px;
  font-weight: normal;
  color: #888;
}

.group {
  margin-left: auto;
}
.members {
  transition: all 0.1s;
  padding: 40px;
  font-family: "Montserrat";
  color: #ccc;
  background-color: lighten(5%);
}
.current {
  font-weight: bold;
  margin-right: 10px;
}
.max {
  opacity: 0.5;
  margin-left: 10px;
}
</style>