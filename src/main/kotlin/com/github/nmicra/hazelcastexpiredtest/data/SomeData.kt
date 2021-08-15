package com.github.nmicra.hazelcastexpiredtest.data

import java.io.Serializable

data class Employee(val name : String, val age: Int, val type :EmployeeType ) : Serializable

sealed class EmployeeType : Serializable
object Manager : EmployeeType()
object SalePerson : EmployeeType()