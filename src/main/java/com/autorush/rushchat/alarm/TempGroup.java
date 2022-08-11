package com.autorush.rushchat.alarm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;

import java.util.Arrays;
import java.util.List;

public class TempGroup {

    public List<String> registrationTokens = Arrays.asList(
            "dGqEprQvY_g:APA91bGGgUMphkCyL0a6XiqCnt7eNAd5yVGJyOg9DHYWvwNSW9CsYcyVmu1yemPqxi-GngMRsZnU8DIJQ616rPMxjiMa6k4_9uxzqTkXwjeI5o0-VDTFrUI79fMsnsMZGNbcHpzW0gZi",
            "eFfitHyyj_g:APA91bFTUMPTeHc2mBtv1jFcIjtCyu9IIpQ1FoOMEKkxe-5QGK0ZqlRqTdP9YgFTVvSjHKegRRuSr0m4Y_bmId86QlUQ4OQbvls8iQ_Gh1PPRC5R8wkNQ_VMYuIC1Ozj5KeJSzMlIZhK",
            "fRaL8iY_lng:APA91bHP5Z8fRXLZNaPRCGd5mdNRpI3jz6OqdsIGGdHfiuyyNpqGCYotgYxinUFXpmVe6XDbdQpk1VOiHJTm0i8oP4a5PSuvCV26_6hrImm6nK0_M1OKMv577Zboxn-HVRoyuyZdA4H9"
            ,"fUP3-dR1udM:APA91bEOz32uu3z5v50H54M_F9O_6znafa3CEp66L9OP80cqdJQMS6GhH2iLKBjwRnE-dUpZHHqAbDIlnRjILiqnxtJC5dHOaDTGBxRhecMtKss5U-EDfTPuwsovDGocRQ_I-iXePWd5"
            ,"cEg07pw6Jrk:APA91bGicvDMj_ZqeMK4_cASi6mATeqr65BJ8NYEv0PvNqnaa0ZeNkOH0p1Y5gzmJSAg7ZTmKoLIo-icRRBDNWzlVH6_mXMB8JoWNmwQSQo5lukPMln7HIbkPrW2kv8WUS3BwRfbYzD1"
    );

    public TopicManagementResponse testTopic() throws Exception{
        TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
                registrationTokens, "test");
        System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
        return response;
    }


}
