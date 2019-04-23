package me.saro.ldap.jum.app

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class DBConfig {

    @Value("\${datadir}") lateinit var datadir: String

    @Bean
    fun dataSource(): DataSource {
        var conf = HikariDataSource()
        conf.driverClassName = "org.h2.Driver"
        conf.jdbcUrl = "jdbc:h2:$datadir/ldap"
        conf.username = "ldap-user"
        conf.password = "ldap-pass"
        return conf
    }
}

