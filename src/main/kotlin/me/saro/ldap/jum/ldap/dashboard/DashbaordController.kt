package me.saro.ldap.jum.ldap.dashboard

import me.saro.ldap.jum.config.PropsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DashbaordController {

    @Autowired lateinit var propsService: PropsService
    @Autowired lateinit var ldapService: PropsService

    @GetMapping("/")
    fun dashboard(model: Model): String {
        model.addAttribute("props", propsService.all())
        return "index"
    }
}
