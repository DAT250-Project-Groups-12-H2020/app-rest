# App

## Setup

### Spring boot

* Download `dat250-gr-2-h2020-app-firebase-adminsdk.json` and place it in the `src/main/secrets` folder (where see `place-secrets-here`)
* To start the server
    * Using intellij; run the pre-made run configuration `Run`
    * Otherwise run `gradlew bootRun` with the environment variables `GOOGLE_APPLICATION_CREDENTIALS=./secrets/dat250-gr-2-h2020-app-firebase-adminsdk.json;FIREBASE_DATABASE=https://dat250-gr-2-h2020-app.firebaseio.com/;CORS_DOMAIN=localhost`. (Note that this assumes the working directory is `$PROJECT_DIR$/src/main/resources`)

### Postman

*[Invite link](https://app.getpostman.com/join-team?invite_code=46c782f4cfc3d20dc23d455e367a80d5) (Expires on 14 Oct, 2020)*

To use postman you need to find the `refresh token` and give it to postman.

1. Go to <https://dat250-gr-2-h2020-app.web.app/>
2. Open browser console (usually F12)
3. You should see an object `user`, open it
4. Scroll down til you see something like `refreshToken: "AE0u-...mEw"`
5. Copy the value of `refreshToken` and place it in the `current value` column of the global variable `refresh_token` and save
6. Start this spring boot application
7. Run the `session/Login` request, you should get a `200 OK`
8. Run the `account/me` request to make sure you are logged in

Expected response for `account/me` is

```json
{
  "id": "JqVyC0eA0wP8dDMQFYchS8BOymm2",
  "role": "USER",
  "polls": [],
  "name": null,
  "email": "test@test.no",
  "votes": {}
}
```

## Authentication with firebase

Based on <https://github.com/gladius/firebase-spring-boot-rest-api-authentication>, and <https://firebase.google.com/docs/admin/setup#java>

## Spotless

This project is using spotless to make sure the code format does not deviate between each user.

### Spotless errors

If spotless gives you any erros try chaning the settings in intelliJ

* Open settings (`ctrl-alt-s`)
  * For Kotlin go to `Editor > Code Style > Kotlin`
    * In the `Tabs and Indents` tab
      * Uncheck `Use tab character`
      * Set `Tab size` to `2`
      * Set `Indent` to `2`
      * Set `continuation indent` to `2`
    * In the `Import` tab
      * set `Top level symbols` and `Java statics and enum members` to `Use single import`
      * Remove all packages under `Packages to use import with '*'`
  * For Java go to `Editor > Code Style > Java`
    * In the `Tabs and Indents` tab
      * Uncheck `Use tab character`
      * Set `Tab size` to `2`
      * Set `Indent` to `2`
      * Set `continuation indent` to `2`
    * In the `Imports` tab
      * Check `Use single class import`
      * Set `Class count to use import with '*'` to `999`
      * Set `Names count to use static import with '*'` to `999`
      * Remove all packages under `Packages to use import with '*'`
