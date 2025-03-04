package com.community.communityservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
class CommunityServiceApplication

fun main(args: Array<String>) {
    runApplication<CommunityServiceApplication>(*args)
}
