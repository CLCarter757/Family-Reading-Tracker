<template>
  <div class = "card">
       <h2 class="d">{{prize.name}}</h2> 
       <img class="img" v-bind:src="selectedImage" alt="Great Job!">
      <p>{{ prize.description }}</p>
      <p class="has-text-weight-semibold">Number of minutes: {{prize.milestone}}</p>
      <p class="has-text-weight-semibold">{{prize.startDate}} - {{prize.endDate}}</p>
      <p class="has-text-weight-semibold">Number Available: {{prize.maxPrizes}}</p>
      <p class="has-text-weight-semibold">Available to: {{prize.userGroup}}</p>
      
  </div>
</template>

<script>
export default {
    name: "prize-card",
    props: {
        prize: Object
    },
    data() {
        return {
            images: [
                'https://cdn-icons-png.flaticon.com/512/1903/1903162.png',
                'https://cdn.pixabay.com/photo/2020/12/15/16/06/win-5834110_960_720.png',
                'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1SbNCmACkTwZDA0SvnTWLX2gGe7-3TVtFIA&usqp=CAU',
                'https://media.istockphoto.com/vectors/award-ribbon-icon-on-white-stock-vector-illustration-vector-id978978230?k=20&m=978978230&s=170667a&w=0&h=pqCL66f_jB7SLzmoD-8PEWerOyfcrpZQtzZT2hSwgLw=',
                'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsGGZtd9mdr80H75Uyt-xE7Q0SJwuvy-JyqA&usqp=CAU'
            ],
            selectedImage: '',
        }
    },
    methods: {
        randomItem (items) {
      return items[Math.floor(Math.random()*items.length)];
    }
  },
  created() {
    this.selectedImage = this.randomItem(this.images)
  }


}
</script>

<style>
.card {
    border: 2px solid black;
    border-radius: 10px;
    width: 250px;
    height: 400px;
    margin: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}

.book-title {
    font-size: 1.5rem;
}

.book-author {
    font-size: 1rem;
}

.img {
    height: 150px;
    padding: 10px;
}
</style>