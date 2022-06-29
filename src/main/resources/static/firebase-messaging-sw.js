
importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/5.9.2/firebase-messaging.js');

const firebaseConfig = {
  apiKey: "AIzaSyABWgTeUqxhuw5yJOg_682hNLQnfV9M3A4",
  authDomain: "auth-rush.firebaseapp.com",
  projectId: "auth-rush",
  storageBucket: "auth-rush.appspot.com",
  messagingSenderId: "259820255243",
  appId: "1:259820255243:web:c50b4d65b32eadbe3d2f0e",
  measurementId: "G-PWBNKSD5VY"
};

//// Initialize Firebase
//const app = initializeApp(firebaseConfig);
//const analytics = getAnalytics(app);

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();