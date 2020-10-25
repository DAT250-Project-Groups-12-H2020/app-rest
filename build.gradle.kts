import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  idea
  id("org.springframework.boot") version "2.3.4.RELEASE"
  id("io.spring.dependency-management") version "1.0.10.RELEASE"
  id("com.diffplug.spotless") version "5.6.1"
  kotlin("jvm") version "1.4.10"
  kotlin("plugin.spring") version "1.4.10"
  kotlin("plugin.jpa") version "1.4.10"
}

group = "no.hvl.dat250"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  runtimeOnly("org.springframework.boot:spring-boot-devtools")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("com.google.firebase:firebase-admin:7.0.0")
  implementation("org.projectlombok:lombok")
  implementation("org.springframework.boot:spring-boot-starter-amqp")

  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.springframework.amqp:spring-rabbit-test")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "11"
  }
}

spotless {
  format("misc") {
    // define the files to apply `misc` to
    target("*.gradle", "*.md", ".gitignore")

    // define the steps to apply to those files
    trimTrailingWhitespace()
    indentWithSpaces(2) // or spaces. Takes an integer argument if you don't like 4
    endWithNewline()
  }
  java {
    // don't need to set target, it is inferred from java

    // apply a specific flavor of google-java-format
    googleJavaFormat()
  }
  kotlin {
    ktlint("0.39.0").userData(mapOf(
      "indent_size" to "2",
      "continuation_indent_size" to "2",
      "max_line_length" to "120"
    ))
  }
}

tasks.withType<Test>() {
  dependsOn("spotlessApply")
}

tasks.withType<JavaExec>() {
  dependsOn("spotlessApply")
}
