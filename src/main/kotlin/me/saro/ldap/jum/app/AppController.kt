package me.saro.ldap.jum.app

import me.saro.ldap.jum.ldap.LdapService
import me.saro.ldap.jum.pub.ResultData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Controller
class AppController {

    @GetMapping("/install")
    fun install(model: Model): String {
        return "app/install"
    }

}

@RestController
class RestAppController {

    @Autowired lateinit var ldapService: LdapService

    @PostMapping("/install")
    fun install(@RequestParam param: Map<String, String>): ResultData<Void?> {
        val host = param.get("host") ?: ""
        val port = param.get("port") ?: ""
        val bindDn = param.get("bindDn") ?: ""
        val bindPassword = param.get("bindPassword") ?: ""
        val basdDn = param.get("basdDn") ?: ""

        return when (ldapService.load(host, port, bindDn, bindPassword, basdDn)) {
            true -> ResultData("OK")
            false -> ResultData( "FAIL", "ldap connect fail")
        }
    }
}
