package no.hvl.dat250.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

const val API_VERSION_1 = "/api/v1"

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
