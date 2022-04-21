import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    searchBook: [],
    userBooks: [],
    familyBooks: [],
    family: [],
    readingActivity: []

  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    ADD_SEARCH_BOOK(state, data){
      state.searchBook = data;
    },
    SET_USER_BOOKS(state, data) {
      state.userBooks = data;
    },
    UPDATE_BOOK_RATING(state, data){
      const bookToUpdate = state.userBooks.find(book => book.isbn == data.isbn);
      bookToUpdate.rating = data.rating;
    },
    UPDATE_BOOK_COMPLETED(state, data){
      const bookToUpdate = state.userBooks.find(book => book.userBookId === data.userBookId);
      bookToUpdate.completed = data.completed;
    },
    UPDATE_BOOK_FAVORITED(state, data){
      const bookToUpdate = state.userBooks.find(book => book.userBookId === data.userBookId);
      bookToUpdate.favorited = data.favorited;
    },
    SET_FAMILY(state, data) {
      state.family = data;
    },
    SET_FAMILY_BOOKS(state, data) {
      state.familyBooks = data;
    },
    ADD_MEMBER(state, data) {
      state.family.push(data);
    },
    SET_PRIZE_LIST(state, data) {
      state.prizes = data;
    },
    UPDATE_USER_FAMILY(state, data){
      state.user.familyId = data.familyId;
      localStorage.setItem('user',JSON.stringify(state.user));
    },
    REMOVE_FAMILY_MEMBER(state, data){
      const personToUpdate = state.family.find(person => person.id === data.id);
      const index = state.family.indexOf(personToUpdate);
      state.family.splice(index);
    },
    SET_READING_ACTIVITY(state, data) {
      state.readingActivity = data;
    }

  }
})
