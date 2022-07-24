
let firebaseConfig = {
    apiKey: "AIzaSyABWgTeUqxhuw5yJOg_682hNLQnfV9M3A4",
    authDomain: "auth-rush.firebaseapp.com",
    projectId: "auth-rush",
    storageBucket: "auth-rush.appspot.com",
    messagingSenderId: "259820255243",
    appId: "1:259820255243:web:c50b4d65b32eadbe3d2f0e",
    measurementId: "G-PWBNKSD5VY"
};
firebase.initializeApp(firebaseConfig);

const messaging = firebase.messaging();

messaging.requestPermission()
    .then(function() {
        return messaging.getToken();
    })
    .then(async function(token) {
        console.log(token);
        messaging.onMessage(payload => {
            console.log('포그라운드 : ',payload);
        });
    })