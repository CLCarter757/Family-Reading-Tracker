<template>
  <div class="card">
      <h2 class="subtitle is-3">{{prize.name}}</h2>
      <img class="img" src="/book-badge.png" alt="">
      <p>{{ prize.description }}</p>
      <p class="has-text-weight-semibold">Number of minutes: {{prize.milestone}}</p>
      <p class="has-text-weight-semibold">{{prize.startDate}} - {{prize.endDate}}</p>
      <p class="has-text-weight-semibold">Number Available: {{prize.maxPrizes}}</p>
      <p class="has-text-weight-semibold">Available to: {{prize.userGroup}}</p>
      <i class="fa-solid fa-square-plus" v-if="this.$store.user.familyRole == 'ROLE_PARENT'"></i>
  </div>
</template>

<script>
export default {
    name: "prize-card",
    prop: {
        prize: Object,
    }

}
</script>

<style>
.card {
    border: 2px solid black;
    border-radius: 10px;
    width: 250px;
    height: 250px;
    margin: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
}
.fa-square-plus {
  color: green;
  height: 100px;
  cursor: pointer;
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