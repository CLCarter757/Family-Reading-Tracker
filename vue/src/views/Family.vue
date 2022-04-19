<template>
    <div>
        <h1>Family Page</h1>
        <family-list></family-list>
        <h2>Recent Family Activity</h2>
    </div>
</template>

<script>
import FamilyList from '../components/FamilyList.vue';

export default {
    components: {
        FamilyList
    },
    created() {

    },
    methods: {
        retrieveFamilyBooks() {
            
        }
    }
}
</script>