<template>
    <div>
        <div class="is-size-1">Prizes</div>
        <button class="mark-read button is-primary is-small is-fullwidth is-rounded" v-on:click="goToNewPrize">Add Prize</button>
        <h2>Prizes Available</h2>
        <div class="prize-list">
            <prize-card 
            v-for="prize in prizesAvailable" 
            v-bind:key="prize.prizeId" 
            :prize = "prize"
            />   
        </div>
        
        <h2>My Prizes</h2>
        <div class="prize-list">
            <prize-card 
            v-for="prize in prizesWon" 
            v-bind:key="prize.prizeId" 
            :prize = "prize"
            />   
        </div>
        <h2>Recent Family Prizes Won</h2>
        
    </div>
</template>

<script>
import PrizeService from '../services/PrizeService';
import PrizeCard from '../components/PrizeCard.vue';

export default ({
    components: {
        PrizeCard
    },
    computed: {
        prizesAvailable() {
            return this.$store.state.prizes.filter((prize) => {
                return prize.maxPrizes > prize.winners.length && 
                0 == prize.winners.filter((winner) => {
                    return this.$store.state.user.username == winner.username  
                }).length;
        });
        },
        prizesWon() {
            return this.$store.state.prizes.filter((prize) => {
                return 1 == prize.winners.filter((winner) => {
                    return this.$store.state.user.username == winner.username  
                }).length;
        });
        }

    },
    methods: {
        retrievePrizes() {
            PrizeService.listPrizesByFamilyId()
                .then(response => {
                    this.$store.commit("SET_PRIZE_LIST", response.data);
                });
        },
        goToNewPrize() {
            this.$router.push('/newprize');
        }
    
    },
    created() {
     this.retrievePrizes();
     }
})
</script>

<style>
.prize-list {
    display:flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
</style>