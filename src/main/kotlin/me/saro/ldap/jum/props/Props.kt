package me.saro.ldap.jum.props



import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table
class Props {

    // key
    @Id
    lateinit var key: String

    // val
    lateinit var value: String
}