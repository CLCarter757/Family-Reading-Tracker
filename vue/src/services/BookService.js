import axios from 'axios';

export default {
    // searchBooksByTitle(title) {
    //     return axios.get(`/books?q=title:${title}`)
    // },

    // searchBooksByAuthor(author) {
    //     return axios.get(`/books?q=author:${author}`)
    // },

    // searchBooksByISBN(isbn) {
    //     return axios.get(`/books?q=isbn:${isbn}`)
    // },

    searchBooks(searchType, searchText) {
        return axios.get(`/books?q=${searchType}:${searchText}`);
    },

    addToWishList(book) {
        return axios.post('/mybooks', book);
    },
    deleteFromWishList(isbn) {
        return axios.delete(`/mybooks/${isbn}`)
    }
}