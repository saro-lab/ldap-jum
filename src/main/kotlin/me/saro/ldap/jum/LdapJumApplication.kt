package me.saro.ldap.jum

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LdapJumApplication

fun main(args: Array<String>) {
	runApplication<LdapJumApplication>(*args)
}
