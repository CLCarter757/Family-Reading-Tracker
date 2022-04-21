<template>
  <div class="card">
      <h2 class="subtitle is-3">Read 5 Hours</h2>
      <img class="img" src="/trophy-badge.png" alt="">
      <p>Read for 5 hours this week</p>
      <p class="has-text-weight-semibold">Number of minutes: 300</p>
      <p class="has-text-weight-semibold">4/17/22 - 4/23/22</p>
      <p class="has-text-weight-semibold">Number Available: 2</p>
      <p class="has-text-weight-semibold">Available to: Children</p>
  </div>
</template>

<script>
export default {
    name: "prizetwo",
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
    height: 350px;
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