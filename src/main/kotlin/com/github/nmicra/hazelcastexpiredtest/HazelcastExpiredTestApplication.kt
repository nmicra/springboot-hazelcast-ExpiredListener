package com.github.nmicra.hazelcastexpiredtest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HazelcastExpiredTestApplication

fun main(args: Array<String>) {
	runApplication<HazelcastExpiredTestApplication>(*args)
}
