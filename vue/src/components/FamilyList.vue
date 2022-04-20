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
    <div class = "table" v-if="this.$store.state.user.familyId && this.$store.state.user.familyRole =='ROLE_CHILD'">
        <tr>
            <th>Name</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        <tr v-for="person in this.$store.state.family" :key="person.username">
            <td>
              <router-link :to="`/family/${person.id} `">
              {{ person.name }} </router-link>
              </td>
            <td>{{ person.username }}</td>
            <td>{{ person.familyRole == 'ROLE_PARENT' ? 'Parent' : 'Child' }}</td>
        </tr>
      </div>
      <div v-if="this.$store.state.user.familyId && this.$store.state.user.familyRole =='ROLE_PARENT'">
        <tr>
            <th>Name</th>
            <th>Username</th>
            <th>Role</th>
            <th>Add Reading</th>
            <th>Remove</th>
        </tr>
        <tr v-for="person in this.$store.state.family" :key="person.username">
            <td @click="setRouteId(person)">
              <router-link  :to="`/family/${person.id}`">
              {{ person.name }} </router-link>
              </td>
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
      routeId: '',
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
                alert(`${ this.newMember.username } has been added to your family.`)
                this.$router.go();
              }
            })
      },
      deleteMember(person) {
        if(confirm(`Remove this person from your family?`))
        FamilyService.deleteMember(this.$store.state.user.familyId, person.id)
          .then(response => {
            if(response.status === 200) {
              this.$store.commit("REMOVE_FAMILY_MEMBER", response.data)
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
            if(response.status === 201) {
              this.$store.commit("UPDATE_USER_FAMILY", response.data);
              this.retrieveFamily();
            }
          });
      },
      setRouteId(person){
        this.routeId = person.id;
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