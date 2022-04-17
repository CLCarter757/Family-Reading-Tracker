<template>
    <div class = "table">
        <tr>
            <th>Name</th>
            <th>Username</th>
            <th>Role</th>
            <th v-show="this.$store.state.user.familyRole =='ROLE_PARENT'">Add Reading</th>
            <th v-show="this.$store.state.user.familyRole =='ROLE_PARENT'">Remove</th>
        </tr>
        <tr v-for="person in this.$store.state.family" :key="person.username">
            <td>{{ person.firstName }}</td>
            <td>{{ person.username }}</td>
            <td>{{ person.familyRole == 'ROLE_PARENT' ? 'Parent' : 'Child' }}</td>
            <td><button>Add Reading</button></td>
            <td>
              <input class="delete" type="image" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzlT0YnCM0rL_IiBOB1Fbm2c81cr2wZZFcVw&usqp=CAU"/>
            </td>
        </tr>
        <tr>
            <input class="delete" type="image" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXViQUnsxunubmswaOUJj2BEwo0uLNiZMWZQ&usqp=CAU"/>
        </tr>

    </div>
</template>

<script>
import FamilyService from '../services/FamilyService.js';

export default {
  name: 'family-list',
  components: {
    
  },
  created() {
    this.retrieveFamily();
  },
  methods: {
    retrieveFamily() {
          FamilyService.getFamily()
              .then(response => {
                  this.$store.commit('SET_FAMILY', response.data);
              });
      },
  }


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
  .delete {
    height: 20px;
    padding: 10px;
}
</style>