package com.github.nmicra.hazelcastexpiredtest.controller

import com.github.nmicra.hazelcastexpiredtest.data.Employee
import com.github.nmicra.hazelcastexpiredtest.data.SalePerson
import com.hazelcast.map.IMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.concurrent.TimeUnit

@RestController
class HCtestController(val employeeMap : IMap<String, Employee>) {

    @GetMapping("/testme")
    fun testme() : String  {
        val uuid = UUID.randomUUID().toString()
        employeeMap.put(uuid, Employee("kuku $uuid", 33, SalePerson), 3, TimeUnit.SECONDS)
        return "added: $uuid"
    }
}