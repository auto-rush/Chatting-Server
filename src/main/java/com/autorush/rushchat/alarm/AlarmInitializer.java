package com.autorush.rushchat.alarm;

import com.autorush.rushchat.common.exception.CustomException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static com.autorush.rushchat.common.exception.ErrorCode.FILE_READ_ERROR;

@Service
public class AlarmInitializer {

    @Value("${alarm.sdk.json.path}")
    private String FIREBASE_SDK_JSON_PATH;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_SDK_JSON_PATH).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            CustomException customException = new CustomException(FILE_READ_ERROR);
            customException.getMessage();
        }
    }

}