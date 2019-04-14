package me.saro.ldap.jum.props

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PropsService {
    @Autowired lateinit var repository: PropsRepository

    fun all() = repository.findAll()

    fun has(key: String) = repository.findById(key).isPresent

    fun get(key: String) = repository.findById(key).map(Props::value).orElse(null)

    fun set(key: String, value: String) = repository.save(Props(key, value))
}