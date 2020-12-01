# FeedApp - DAT250 Advanced Software Technologies

## Setup

### Start the Web Application emulator
* Clone this repository `app-rest`
* Download `dat250-gr-2-h2020-app-firebase-adminsdk.json` and place it in the `src/main/resources/secrets` folder (where you see `place-secrets-here`).
* Follow the `Docker` instructions below.
* Follow the `Spring Boot` instructions below.
* Navigate on terminal to where the `web` directory is (in `app-rest`).
   * Run the command `firebase emulators:start`
   * It is now hosted in local server. Go to http://localhost:5000
   * Now check it out!
   
### Docker

* Download Docker Desktop https://www.docker.com/products/docker-desktop.
* Navigate on terminal to where the `app-rest`directory is.
* Then `docker-compose up` to build, (re)start and attach to containers for a service.

### Spring Boot
* To start the server
    * Using intellij; run the pre-made run configuration `Application`
    * Otherwise run `gradlew bootRun` with the environment variables `GOOGLE_APPLICATION_CREDENTIALS=./secrets/dat250-gr-2-h2020-app-firebase-adminsdk.json;FIREBASE_DATABASE=https://dat250-gr-2-h2020-app.firebaseio.com/;CORS_DOMAIN=localhost`. (Note that this assumes the working directory is `$PROJECT_DIR$/src/main/resources`)

### Postman

*[Invite link](https://app.getpostman.com/join-team?invite_code=46c782f4cfc3d20dc23d455e367a80d5) (Expires on 14 Oct, 2020)*

To use postman you need to find the `refresh token` and give it to postman.
<!---
1. Go to <https://dat250-gr-2-h2020-app.web.app/>
--->
1. Follow the `Start the Web Application emulator` instructions above.
2. Select a log in method (it does not really matter which). Open `Console` in `Inspect` on the browser. 
3. Sign in using the selected method.
4. Scroll down til you see something like `refreshToken: "AE0u-...mEw"`. This pops up in `Console` the second you log in.
5. Copy the value of `refreshToken`
6. Open postman and place `refreshToken` in the `current value` column of the global variable `refresh_token` and save
    * Make sure you have selected `Local` environment
    * You can find global environment by clicking on `Manage Environment` (top right of postman) then clicking on `Globals` at the bottom of the popup window
7. Start this spring boot application (f.eks with run config `Application` in IntelliJ)
8. Run the `session/Login` request, you should get a `200 OK`
    * Make sure you are logged in and have selected the `Team Workspace`
9. Run the `account/Me` request to make sure you are logged in

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

## Rabbit MQ

You have to run either run `docker-compose up` in terminal when standing in this folder, or run the `RabbitMQ` run configuration in intellij.

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
