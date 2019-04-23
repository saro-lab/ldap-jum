package me.saro.ldap.jum.app

import me.saro.ldap.jum.config.PropsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AppController {

    @Autowired lateinit var propsServaice: PropsService
    @Autowired lateinit var ldapService: PropsService

    @GetMapping("/install")
    fun install(model: Model): String {
        return "app/install"
    }
}
