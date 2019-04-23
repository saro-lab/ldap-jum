package me.saro.ldap.jum.ldap.group

import me.saro.ldap.jum.ldap.LdapService
import me.saro.ldap.jum.props.PropsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController {

    @Autowired lateinit var ldapService: LdapService

    @GetMapping("/")
    fun root(model: Model): String {
        model.addAttribute("users", ldapService.getUsers())
        // aa
        return "user/user"
    }
}
