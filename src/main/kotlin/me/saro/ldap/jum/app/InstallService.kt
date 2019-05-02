package me.saro.ldap.jum.app

import me.saro.ldap.jum.ldap.LdapService
import org.springframework.stereotype.Service

@Service
class InstallService {

    lateinit var ldapService: LdapService;

    fun install() {
        ldapService.bindDn
    }
}