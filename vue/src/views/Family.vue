<template>
    <div>
        <h1 class="title">My Family</h1>
        <family-list></family-list>
        <h2 class="subtitle">Recent Family Activity</h2>
    </div>
</template>

<script>
import FamilyList from '../components/FamilyList.vue';
import BookService from '../services/BookService';

export default {
    components: {
        FamilyList
    },
    created() {
        this.retrieveFamilyBooks();
    },
    methods: {
        retrieveFamilyBooks() {
            BookService.listFamilyBooks()
                .then(response => {
                    if(response.status === 200) {
                        this.$store.commit("SET_FAMILY_BOOKS", response.data)
                    }
                })
        }
    }
}
</script>