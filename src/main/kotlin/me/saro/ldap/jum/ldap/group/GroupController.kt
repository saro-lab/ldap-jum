package me.saro.ldap.jum.ldap.group

import me.saro.commons.Converter
import me.saro.ldap.jum.ldap.LdapService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/group")
class GroupController {

    @Autowired lateinit var ldapService: LdapService

    @GetMapping("/")
    fun root(model: Model): String {
        model.addAttribute("groups", ldapService.getAllGroups())
        // aa
        return "group/group"
    }

    @GetMapping("/list")
    @ResponseBody
    fun list() : String {
        return Converter.toJson(ldapService.getAllGroups())
    }

    @PostMapping("/{name}")
    @ResponseBody
    fun create(@PathVariable("name") name: String) : String {
        return Converter.toJson(ldapService.createGroup(name))
    }

    @DeleteMapping("/{name}/{gid}")
    @ResponseBody
    fun delete(@PathVariable("name") name: String, @PathVariable("gid") gid: String) : String {
        return Converter.toJson(ldapService.deleteGroup(name, gid))
    }
}
