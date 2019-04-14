package me.saro.ldap.jum

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application : CommandLineRunner {
	override fun run(vararg args: String) {
		println("start!!")
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
