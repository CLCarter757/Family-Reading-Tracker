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

    submitReadingActivity(id, readingActivity){
        return axios.post(`users/${id}/activities`, readingActivity)
    },

    listMyBooks() {
        return axios.get('/mybooks')
    },
    
    updateBook(book) {
        return axios.put(`/mybooks/${book.isbn}`, book)
    },
    deleteBook(isbn) {
        return axios.delete(`/mybooks/${isbn}`)
    }
}