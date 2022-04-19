<template>
  <div>
    <div class="create-family" v-if="this.$store.state.user.familyId < 1">
        <h2>Create Your Family</h2>
          <input
          type="text"
          id="family"
          class="form-control"
          placeholder="Name Your Family"
          v-model="newFamily.familyName"
          required
          autofocus
        />
        <button type="submit" v-on:click.prevent="createFamily">Submit</button>
    </div>
    <div class = "table" v-if="this.$store.state.user.familyId">
        <tr>
            <th>Name</th>
            <th>Username</th>
            <th>Role</th>
            <th v-show="this.$store.state.user.familyRole =='ROLE_PARENT'">Add Reading</th>
            <th v-show="this.$store.state.user.familyRole =='ROLE_PARENT'">Remove</th>
        </tr>
        <tr v-for="person in this.$store.state.family" :key="person.username">
            <td>{{ person.name }}</td>
            <td>{{ person.username }}</td>
            <td>{{ person.familyRole == 'ROLE_PARENT' ? 'Parent' : 'Child' }}</td>
            <td><button>Log Reading</button></td>
            <td>
              <input class="icon" type="image" @click="deleteMember(person)"
                src="https://cdn-icons-png.flaticon.com/512/1828/1828843.png"/>
            </td>
        </tr>
        <tr>
          <div class="add-member">
              <input class="icon" type="image" src="https://cdn-icons-png.flaticon.com/512/148/148764.png"
                @click="toggleForm"/>
              <input
                  v-show="showForm"
                  type="text"
                  id="name"
                  class="form-control"
                  placeholder="USERNAME"
                  v-model="newMember.username"
                  required
                  autofocus
                />
                <button type="submit" v-show="showForm" v-on:click.prevent="addMember">Submit</button>
            </div>
        </tr>
    </div>
  </div>  
</template>

<script>
import FamilyService from '../services/FamilyService.js';

export default {
  name: 'family-list',
  data() {
    return {
      showForm: false,
      newFamily: {
        familyName: '',
      },
      newMember: {
        username: ''
      },
      memberToDelete: {
        username: 'Terminator',
      }
    }
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
      addMember() {
          FamilyService.addFamilyMember(this.$store.state.user.familyId, this.newMember)
            .then(response => {
              if(response.status == 200) {
                alert(`${ this.newMember } has been added to your family.`)
                this.$router.go();
              }
            })
      },
      deleteMember(person) {
        console.log(person);
        FamilyService.deleteMember(this.$store.state.user.familyId, person.id)
          .then(response => {
            if(response.status === 200) {
              console.log(person)
              // this.$router.go();
            }
          })
      },
      toggleForm() {
        this.showForm = !this.showForm;
      },
      createFamily() {
        FamilyService.addFamily(this.newFamily)
          .then(response => {
            if(response.status === 200) {
              this.$router.go();
            }
          });
          this.retrieveFamily();
      }
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
  .icon {
    height: 30px;
    padding: 10px;
}
.add-member {
  display: flex
}
</style>