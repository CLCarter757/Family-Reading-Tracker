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
    prizes: []
      // {
      //   username: 'test',
      //   firstName: 'bob',
      //   role: 'child'
      // },
      // {
      //   username: 'test',
      //   firstName: 'bob',
      //   role: 'child'
      // }
    // completed: [
    //   {
    //     title: "The Hunger Games",
    //     author: "Suzanne Collins",
    //     read: true,
    //     isbn: "9780439023481"
    //   },
    //   {
    //     title: "The Giver",
    //     author: "Lois Lowry",
    //     read: true,
    //     isbn: "0385732554"
    //   },
    //   {
    //     title: "A Wrinkle in Time",
    //     author: "Madeleine L'Engle",
    //     read: true,
    //     isbn: "1250153271"
    //   },
    //   {
    //     title: "Red Rising",
    //     author: "Pierce Brown",
    //     read: true,
    //     isbn: "0345539788"
    //   }
    // ],
    // reading: [
    //   {
    //     title: "Harry Potter and the Order of the Phoenix",
    //     author: "	J. K. Rowling",
    //     isbn: "0747551006"
    //   },
    //   {
    //     title: "Charlotte's Web",
    //     author: "E. B. White",
    //     isbn: "9780062658753"
    //   },
    // ],

    // list: [
    //   {
    //     title: "Coraline",
    //     author: "Neil Gaiman",
    //     isbn: "	0061139378"
    //   },
    //   {
    //     title: "Hatchet",
    //     author: "Gary Paulsen",
    //     isbn: "	0027701301"
    //   },
    // ],
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
    }





  }
})
