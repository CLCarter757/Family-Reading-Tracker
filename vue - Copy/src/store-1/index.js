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
    completed: [
      {
        title: "The Hunger Games",
        author: "Suzanne Collins",
        read: true,
        isbn: "9780439023481"
      },
      {
        title: "The Giver",
        author: "Lois Lowry",
        read: true,
        isbn: "0385732554"
      },
      {
        title: "A Wrinkle in Time",
        author: "Madeleine L'Engle",
        read: true,
        isbn: "1250153271"
      },
      {
        title: "Red Rising",
        author: "Pierce Brown",
        read: true,
        isbn: "0345539788"
      }
    ],
    reading: [

      {
        title: "Fantastic Beasts: The Crimes of Grindelwald",
        author: "	J. K. Rowling",
        details: "A book about wizards",
        isbn: "1338263897"
      },

      {
        title: "Harry Potter and the Half-Blood Prince",
        author: "	J. K. Rowling",
        details: "A book about wizards",
        isbn: "0545582997"
      },
      {
        title: "Charlotte's Web",
        author: "E. B. White",
        details: "A story of a livestock pig named Wilbur and his friendship with a barn spider named Charlotte. ",
        isbn: "9780062658753"
      },

      {
        title: "Atomic Habits",
        author: "James Clear",
        isbn: "9780735211308"
      },

    ],

    list: [
      {
        title: "Coraline",
        author: "Neil Gaiman",
        isbn: "	0061139378"
      },
      {
        title: "Hatchet",
        author: "Gary Paulsen",
        isbn: "	0027701301"
      },

      {
        title: "Wonder",
        author: "R. J. Palacio",
        isbn:"0375869026"
      }
    ],
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
      data = data.filter((book) => {
        for (let search of state.list) {
          if (search.isbn == book.isbn) return false;
        }

        for (let search of state.reading) {
          if (search.isbn == book.isbn) return false;
        }

        for (let search of state.completed) {
          if (search.isbn == book.isbn) return false;
        }
        return true;
      });
      //  还需要completed reading
      // 已经在wishlist了，note一下，已经在wishlist了
      // log reading activity时间

      state.searchBook = data;
    },
  }
})
