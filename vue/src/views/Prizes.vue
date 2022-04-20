<template>
    <div>
        <div class="is-size-1">Prizes</div>
        <button class="mark-read button is-primary is-small is-fullwidth">Add Prize</button>
        <h2>Prizes Available</h2>
        <div class="prize">
            <div v-for="prize in prizesAvailable()" v-bind:key="prize.prizeId" >{{prize.name}}
                
            </div>
        </div>
        
        <h2>My Prizes</h2>
        <h2>Recent Family Prizes Won</h2>
        <prize></prize>
    </div>
</template>

<script>
import Prize from '../components/Prize.vue';
import PrizeService from '../services/PrizeService';

export default ({
    components: {
        Prize
    },
    methods: {
        retrievePrizes() {
            PrizeService.listPrizesByFamilyId()
                .then(response => {
                    this.$store.commit("SET_PRIZE_LIST", response.data);
                });
        },
        prizesAvailable() {
            return this.$store.state.prizes.filter((prize) => {
                return 0 == prize.winners.filter((winner) => {
                    return this.$store.state.user.username == winner.username &&
                    prize.maxPrizes > prize.winners.length;
                })
        });
        },

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