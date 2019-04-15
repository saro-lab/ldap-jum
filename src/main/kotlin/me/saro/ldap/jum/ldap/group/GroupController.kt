package me.saro.ldap.jum.ldap.group

import me.saro.ldap.jum.props.PropsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/group")
class GroupController {

    @Autowired lateinit var propsService: PropsService
    @Autowired lateinit var ldapService: PropsService

    @GetMapping("/")
    fun root(model: Model): String {
        model.addAttribute("props", propsService.all())
        // aa
        return "group/index"
    }
}
