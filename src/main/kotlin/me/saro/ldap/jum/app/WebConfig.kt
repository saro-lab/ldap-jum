package me.saro.ldap.jum.app

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class WebConfig: WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(this.noCacheInterceptor())
    }

    fun noCacheInterceptor(): HandlerInterceptor {
        return object : HandlerInterceptor {
            override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
                val path = request!!.servletPath
                if (path.matches("\\/[a-zA-Z]*".toRegex()) || path.startsWith("/oauth")) {
                    response!!.setHeader("Cache-Control", "no-cache")
                }
            }
        }
    }
}

