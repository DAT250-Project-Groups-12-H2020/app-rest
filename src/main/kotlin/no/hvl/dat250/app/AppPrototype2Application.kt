package no.hvl.dat250.app

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import java.io.FileInputStream


@SpringBootApplication
class AppPrototype2Application {


    @Primary
    @Bean
    fun firebaseInit() {
        val serviceAccount = FileInputStream("./secrets/dat250-gr-2-h2020-app-firebase-adminsdk.json")

        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://dat250-gr-2-h2020-app.firebaseio.com")
                .build()

        FirebaseApp.initializeApp(options)
    }
}

const val API_VERSION_1 = "/api/v1"

fun main(args: Array<String>) {
    runApplication<AppPrototype2Application>(*args)
}
