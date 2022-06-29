
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

// Show Notification
    // 메시지 기능 활성화를 알림
const messaging = firebase.messaging();

// RequestPermission 첫 어플 시작 시 알림 허용 or 불허를 사용자에게 안내합니다.
// 허용하지 않을 시 알람 메시지는 가지 않습니다.
messaging.requestPermission()
    .then(function() {
    // 알람이 허용되었을 때 토큰을 반환합니다.
    // 해당 토큰을 통해 FCM 특정 사용자에게 메시지를 보낼 수 있습니다.
        return messaging.getToken();
    })
    .then(async function(token) {
        console.log(token);
        messaging.onMessage(payload => {
            console.log('포그라운드 : ',payload);
        });
    })