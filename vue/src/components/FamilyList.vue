<template>
    <div>
        <tr>
            <th>First Name</th>
            <th>Username</th>
            <th>Role</th>
            <th v-show="this.$store.state.user.familyRole =='ROLE_PARENT'">Add Reading</th>
            <th v-show="this.$store.state.user.familyRole =='ROLE_PARENT'">Remove</th>
        </tr>
        <tr v-for="person in this.$store.state.family" :key="person.username">
            <td>{{ person.firstName }}</td>
            <td>{{person.username}}</td>
            <td>{{ person.role }}</td>
            <td><button>Add Reading</button></td>
            <td><button></button></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>

    </div>
</template>

<script>

export default {
    name: 'family-list',

}
</script>

<style>
  td, th {
    border: 1px solid #999;
    padding: 20px;
    
  }
  th {

    border-radius: 0;
    top: 0;
    padding: 10px;
  }
</style>