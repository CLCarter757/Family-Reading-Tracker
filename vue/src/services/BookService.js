import axios from 'axios';

export default {
    searchBooks(searchType, searchText) {
        return axios.get(`/books?q=${searchType}:${searchText}`);
    },

    addToWishList(book) {
        return axios.post('/mybooks', book);
    },

    deleteFromWishList(isbn) {
        return axios.delete(`/mybooks/${isbn}`)
    },

    listMyBooks() {
        return axios.get('/mybooks')
    }
}