package me.saro.ldap.jum.ldap

import me.saro.commons.Converter
import me.saro.commons.Valids
import me.saro.ldap.jum.props.PropsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.InitialLdapContext


@Service
class LdapService {

    @Autowired lateinit var propsService: PropsService
    lateinit var status: LdapStatus
    var context: InitialLdapContext? = null

    @PostConstruct
    fun load() {

        var host = propsService.get("LDAP_HOST")
        var port = propsService.get("LDAP_PORT")
        var bindDn = propsService.get("LDAP_BIND_DN")
        var bindPassword = propsService.get("LDAP_BIND_PASSWORD")
        var baseDn = propsService.get("LDAP_BASE_DN")

        if (!Valids.isNotNull(host, port, bindDn, bindPassword, baseDn)) {
            status = LdapStatus.NOT_INSTALLED
            return
        }

        val env = Hashtable<String, String>()
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
        env.put(Context.PROVIDER_URL, "ldap://$host:$port/$baseDn")
        env.put(Context.SECURITY_PRINCIPAL, bindDn)
        env.put(Context.SECURITY_CREDENTIALS, bindPassword)

        close()
        context = InitialLdapContext(env, null)

        try {
            val ctls = SearchControls()
            ctls.searchScope = SearchControls.ONELEVEL_SCOPE
            context!!.search("", "($bindDn)", ctls)
            status = LdapStatus.ACTIVE
        } catch (e: Exception) {
            status = LdapStatus.CONNECTION_FAILURE
        }


        for (user in getUsers()) {
            println(user)
        }
    }

    fun getGroups(): List<SearchResult> {
        var ctx = context!!
        val ctls = SearchControls()
        ctls.searchScope = SearchControls.SUBTREE_SCOPE
        return Converter.toList(ctx.search("", "(objectClass=posixGroup)", ctls))
    }

    fun getUsers(): List<SearchResult> {
        var ctx = context!!
        val ctls = SearchControls()
        ctls.searchScope = SearchControls.ONELEVEL_SCOPE
        return Converter.toList(ctx.search("", "(objectClass=posixAccount)", ctls))
    }

    private fun <T> toList(e: NamingEnumeration<T>): List<T> = Converter.toList(e)

    @PreDestroy
    fun close() {
        if (context != null) {
            try {
                context!!.close()
            } catch (e: Exception) {

            }
        }
    }
}