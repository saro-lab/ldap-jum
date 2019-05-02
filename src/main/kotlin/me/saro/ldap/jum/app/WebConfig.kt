package me.saro.ldap.jum.app

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
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
        super.addInterceptors(registry)
    }

    fun noCacheInterceptor(): HandlerInterceptor {
        return HandlerInterceptor() {

            override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
                val path = request!!.servletPath
                if (path.matches("\\/[a-zA-Z]*".toRegex())) {
                    response!!.setHeader("Cache-Control", "no-cache")
                }
            }
        }
    }
}

