<template>
    <div>
        <h1>Prizes</h1>
        <h2>Prizes Available</h2>
        <p>Add Prize Button</p>
        <h2>My Prizes</h2>
        <h2>Recent Family Prizes Won</h2>
        <prize></prize>
    </div>
</template>

<script lang="ts">
import Prize from '../components/Prize.vue'

export default ({
    components: {
        Prize
    }
})
</script>