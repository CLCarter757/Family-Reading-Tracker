import axios from 'axios';

export default {

    createPrize(prize) {
        return axios.post('/prize', prize);
    },

    listPrizesByFamilyId() {
        return axios.get('/prize')
    }
    
}