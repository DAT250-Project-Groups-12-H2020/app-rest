package no.hvl.dat250.app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import no.hvl.dat250.app.security.models.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    private final SecurityProperties secProps;

    public FirebaseConfig(@Autowired SecurityProperties secProps) {
        this.secProps = secProps;
    }

    @Primary
    @Bean
    public void firebaseInit() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.getApplicationDefault())
                                                 .setDatabaseUrl(secProps.getFirebaseProps().getDatabaseUrl()).build();
        FirebaseApp.initializeApp(options);
    }

    @Bean
    public Firestore getDatabase() throws IOException {
        FirestoreOptions firestoreOptions =
            FirestoreOptions.newBuilder().setCredentials(GoogleCredentials.getApplicationDefault()).build();
        return firestoreOptions.getService();
    }

}
