import axios from 'axios'

export default {
    getFamily() {
        return axios.get('/myfamily');
    },
    addFamilyMember(familyId, user) {
        return axios.put(`/myfamily/${ familyId }`, user)
    },
    addFamily(family) {
        return axios.post('/myfamily', family)
    }
}