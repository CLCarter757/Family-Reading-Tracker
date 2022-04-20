<template>
    <div>
        <div class="is-size-1">Prizes</div>
        <button class="mark-read button is-primary is-small is-fullwidth">Add Prize</button>
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
        }
        

        //addPrize() {
            // const newPrize = {
            //     familyId: 1,
            //     name: "",
            //     description: "",
            //     milestone: "",
            //     userGroup: "",
            //     maxPrizes: 2,
            //     startDate: "",
            //     endDate: "2"
            //     // userId: Number(this.$store.state.user.id),
            //     // username: this.$store.state.user.username,
            //     // title: this.book.title,
            //     // authors: this.book.authors,
            //     // isbn: this.book.isbn,
            //     // description: this.book.description,
            //     // bookCoverUrl: this.book.bookCoverUrl
            // };
            // const length = this.$store.state.userBooks.filter(
            //     book => book.isbn === newPrize.isbn).length;
            // if(length > 0){
            //     alert(`${ newPrize.title } is already in your Wish List.`)
            // } else {
            // PrizeService.addToWishList(newPrize)
            //     .then(response => {
            //         if(response.status === 200) {
            //             alert(`${newPrize.title} has been added to your Wish List!`)
            //             // this.$router.go();
            //             this.inWishList = true;
            //         }
            //     })
            // }
       // }

    },
    // created() {
    //     this.retrievePrizes();
    // }
})
</script>

<style>
.prize-list {
    display:flex;
    justify-content: space-evenly;
    flex-wrap: wrap;
}
</style>