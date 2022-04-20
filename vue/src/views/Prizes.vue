<template>
    <div>
        <h1 class="title">Prizes</h1>
        <h2 class="subtitle">Prizes Available</h2>
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
        }

    },
    created() {
        this.retrievePrizes();
    }
})
</script>