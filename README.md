# App

## Setup

* Download `dat250-gr-2-h2020-app-firebase-adminsdk.json` and place it in the `src/main/secrets` folder (where see `place-secrets-here`)
* To start the server
    * Using intellij; run the pre-made run configuration `Run`
    * Otherwise run `gradlew bootRun` with the environment variables `GOOGLE_APPLICATION_CREDENTIALS=./secrets/dat250-gr-2-h2020-app-firebase-adminsdk.json;FIREBASE_DATABASE=https://dat250-gr-2-h2020-app.firebaseio.com/;CORS_DOMAIN=localhost`. (Note that this assumes the working directory is `$PROJECT_DIR$/src/main/resources`)

## Authentication with firebase

Based on <https://github.com/gladius/firebase-spring-boot-rest-api-authentication>, and <https://firebase.google.com/docs/admin/setup#java>

## Postman

Invite link <https://app.getpostman.com/join-team?invite_code=46c782f4cfc3d20dc23d455e367a80d5> (Expires on 14 Oct, 2020)

