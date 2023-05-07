package com.paxier.moviesservice.config

import io.netty.channel.ChannelOption
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.validation.annotation.Validated
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.ProxyProvider
import reactor.netty.transport.ProxyProvider.Proxy.HTTP

@Configuration
@EnableConfigurationProperties(WebClientConfigurationProperties::class)
class WebClientConfig {

    @Bean
    fun clientHttpConnector(config: WebClientConfigurationProperties): ReactorClientHttpConnector {
        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.connectTimeoutMillis)
            .compress(true)
            .run {
                if (config.proxy.enabled) {
                    proxy { it.type(config.proxy.type).host(config.proxy.host).port(config.proxy.port) }
                } else {
                    noProxy()
                }
            }

        return ReactorClientHttpConnector(httpClient)
    }
}

@ConfigurationProperties(prefix = "app.webclient")
@Validated
data class WebClientConfigurationProperties(
    @field:[Min(100)] val connectTimeoutMillis: Int = 5000,
    @field:[NotNull] val proxy: HttpClientProxyConfig
)

@Validated
data class HttpClientProxyConfig(
    @field:[NotNull] val enabled: Boolean = false,
    @field:[NotBlank] val host: String = "",
    @field:[Min(1000)] val port: Int = -1,
    val type: ProxyProvider.Proxy = HTTP,
)