import axios from 'axios'

export default {
    getFamily() {
        return axios.get('/myfamily');
    }
}