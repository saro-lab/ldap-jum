package me.saro.ldap.jum.config



import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "ldap_prop")
class Props {
    constructor()

    constructor(key: String, value: String) {
        this.key = key
        this.value = value
    }

    // key
    @Id
    lateinit var key: String

    // val
    lateinit var value: String
}