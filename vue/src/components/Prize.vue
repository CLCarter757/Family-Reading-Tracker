<template>
  <div>
    <prize-card></prize-card>
    <prize-card-2></prize-card-2>
  </div>
</template>

<script >
import PrizeCard from '../components/PrizeCard.vue'
import PrizeCard2 from '../components/PrizeCard2.vue'

export default {
    name: 'prize',
    components: {
      PrizeCard, 
      PrizeCard2
    }
}
</script>

<style>

</style>