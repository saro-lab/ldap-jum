package me.saro.ldap.jum.app

import me.saro.ldap.jum.ldap.LdapService
import me.saro.ldap.jum.ldap.LdapStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class WebConfig: WebMvcConfigurer {

    @Autowired lateinit var ldapService: LdapService

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(this.installInterceptor())
        registry.addInterceptor(this.loginInterceptor())
    }

    fun installInterceptor(): HandlerInterceptor {
        return object : HandlerInterceptor {
            override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
                if (modelAndView != null) {
                    if (request.servletPath.startsWith("/install")) {
                        if (ldapService.status.equals(LdapStatus.ACTIVE)) {
                            response.sendRedirect("/")
                        }
                    } else {
                        if (ldapService.status.equals(LdapStatus.NOT_INSTALLED) || ldapService.status.equals(LdapStatus.CONNECTION_FAILURE)) {
                            response.sendRedirect("/install")
                        }
                    }
                }
            }
        }
    }

    fun loginInterceptor(): HandlerInterceptor {
        return object : HandlerInterceptor {
            override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {

            }
        }
    }
}

