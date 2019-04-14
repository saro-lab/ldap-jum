package me.saro.ldap.jum

import lombok.extern.slf4j.Slf4j
import me.saro.ldap.jum.props.PropsService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("me.saro.ldap.jum.*")
@Slf4j
class Application : CommandLineRunner {

	val log = LoggerFactory.getLogger(this.javaClass)!!

	@Autowired lateinit var propsService: PropsService

	override fun run(vararg args: String) {
        log.info("start server")
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}