<template>
    <div>
        <div class="title">Prizes</div>
        
        <button v-if="this.$store.state.user.familyRole == 'ROLE_PARENT'" class="mark-read button is-primary is-small is-rounded" v-on:click="goToNewPrize">Add Prize</button>
        
        <h2 class="is-size-3 is-underlined">Prizes Available</h2>
        
        <div class="prize-list">
            <prize-card 
            v-for="prize in prizesAvailable" 
            v-bind:key="prize.prizeId" 
            :prize = "prize"
            />   
        </div>
        
        <h2 class="is-size-3 is-underlined mt-5 mb-5">My Prizes</h2>
        <div class="prize-list">
            <prize-card 
            v-for="prize in prizesWon" 
            v-bind:key="prize.prizeId" 
            :prize = "prize"
            />   
        </div>
        <button class="mark-read button is-success is-small is-rounded" v-on:click="toggleAll" >View All Family Prizes</button>
        <div class="prize-list" v-if="isShowingAll">
            <prize-card 
            v-for="prize in allPrizes" 
            v-bind:key="prize.prizeId" 
            :prize = "prize"
            />   
        </div>
        
    </div>
</template>

<script>
import PrizeService from '../services/PrizeService';
import PrizeCard from '../components/PrizeCard.vue';

export default ({
    components: {
        PrizeCard
    },
    data() {
        return {
            isShowingAll: false

        }
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
        },
        allPrizes() {
            return this.$store.state.prizes;
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
        },
        toggleAll() {
            if (this.isShowingAll) {
                this.isShowingAll = false
            } else {
                this.isShowingAll = true
            }
        
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