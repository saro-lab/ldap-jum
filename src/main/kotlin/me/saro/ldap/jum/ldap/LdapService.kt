package me.saro.ldap.jum.ldap

import me.saro.commons.Converter
import me.saro.commons.Maps
import me.saro.commons.Valids
import me.saro.ldap.jum.config.PropsService
import me.saro.ldap.jum.ldap.group.Group
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.naming.Context
import javax.naming.NamingEnumeration
import javax.naming.directory.BasicAttribute
import javax.naming.directory.BasicAttributes
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.naming.ldap.InitialLdapContext

@Service
class LdapService {

    @Autowired lateinit var propsService: PropsService

    lateinit var context: InitialLdapContext
    lateinit var status: LdapStatus
    lateinit var baseDn: String
    lateinit var bindDn: String
    lateinit var scSubTree: SearchControls
    lateinit var scOneLevel: SearchControls

    @PostConstruct
    fun load() {

        var host = propsService.get("LDAP_HOST")
        var port = propsService.get("LDAP_PORT")
        var bindPassword = propsService.get("LDAP_BIND_PASSWORD")
        bindDn = propsService.get("LDAP_BIND_DN")
        baseDn = propsService.get("LDAP_BASE_DN")

        if (!Valids.isNotNull(host, port, bindDn, bindPassword, baseDn)) {
            status = LdapStatus.NOT_INSTALLED
            return
        }

        val env = Hashtable<String, String>()
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory")
        env.put(Context.PROVIDER_URL, "ldap://$host:$port/$baseDn")
        env.put(Context.SECURITY_PRINCIPAL, bindDn)
        env.put(Context.SECURITY_CREDENTIALS, bindPassword)

        loadSearchControls()

        close()
        context = InitialLdapContext(env, null)

        try {
            context.search("", "($bindDn)", scOneLevel)
            status = LdapStatus.ACTIVE
        } catch (e: Exception) {
            status = LdapStatus.CONNECTION_FAILURE
        }


        for (user in getUsers()) {
            println(user)
        }
    }

    private fun loadSearchControls() {
        scSubTree = SearchControls()
        scSubTree.searchScope = SearchControls.SUBTREE_SCOPE
        scOneLevel = SearchControls()
        scOneLevel.searchScope = SearchControls.ONELEVEL_SCOPE
    }

    fun getAllGroups(): List<Group> {
        val res = context.search("", "(objectClass=posixGroup)", scSubTree)
        val list: MutableList<Group> = mutableListOf()
        while (res.hasMore()) {
            list.add(Group(res.next()))
        }
        return list
    }

    fun getNextGid(): String {
        var gid = propsService.getOptional("gidNumber").orElseGet {
            getAllGroups().stream().mapToInt{ Integer.parseInt(it.gidNumber) }.max().orElse(499).toString()
        }
        gid = (Integer.parseInt(gid) + 1).toString()
        propsService.set("gidNumber", gid)
        return gid
    }

    fun createGroup(name: String): Map<String, String> {

        val attrs = BasicAttributes(true)

        attrs.put(BasicAttribute("cn", name))
        attrs.put(BasicAttribute("objectclass", "posixGroup"))
        attrs.put(BasicAttribute("gidNumber", getNextGid()))

        try {
            context.bind("cn=$name", context, attrs)
        } catch (e: Exception) {
            return Maps.toMap("res", "ERROR", "msg", e.message)
        }

        return Maps.toMap("res", "OK")
    }

    fun deleteGroup(name: String, gid: String): Map<String, String> {
        try {
            println("cn=$name")
            context.destroySubcontext("cn=$name")
        } catch (e: Exception) {
            return Maps.toMap("res", "ERROR", "msg", e.message)
        }
        return Maps.toMap("res", "OK")
    }

    fun getUsers(): List<SearchResult> {
        return Converter.toList(context.search("", "(objectClass=posixAccount)", scOneLevel))
    }

    private fun <T> toList(e: NamingEnumeration<T>): List<T> = Converter.toList(e)

    @PreDestroy
    fun close() {
        try {
            context.close()
        } catch (e: Exception) {

        }
    }
}