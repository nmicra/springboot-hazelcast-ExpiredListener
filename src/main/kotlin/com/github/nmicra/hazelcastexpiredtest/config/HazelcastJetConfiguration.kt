package com.github.nmicra.hazelcastexpiredtest.config

import com.github.nmicra.hazelcastexpiredtest.data.Employee
import com.hazelcast.core.EntryEvent
import com.hazelcast.cp.CPSubsystem
import com.hazelcast.jet.Jet
import com.hazelcast.jet.JetInstance
import com.hazelcast.jet.config.JetConfig
import com.hazelcast.map.IMap
import com.hazelcast.map.listener.EntryExpiredListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HazelcastJetConfiguration {

    @Bean
    fun jet(): JetInstance {
        return Jet.newJetInstance(getHazelcastCong())
    }

    private fun getHazelcastCong(): JetConfig {
        val jetCnf = JetConfig()
        val hzCnf = jetCnf.hazelcastConfig
        hzCnf.clusterName = "test-hz-cluster"
        return jetCnf
    }

    @Bean
    fun employeeMap(jet: JetInstance): IMap<String, Employee> {
        return jet.getMap<String, Employee>("hz-synthetic-template")
            .also { it.addEntryListener(object : EntryExpiredListener<String, Employee> {
                override fun entryExpired(event: EntryEvent<String, Employee>) {
                    val lock = jet.hazelcastInstance.cpSubsystem.getLock(event.key)

                    if (lock.tryLock()){
                        try {
                            println("handling expired Employee = [${event.key}], expiredValue [${event.oldValue}]")
                        } finally {
                            lock.unlock()
                        }
                    } else {
                        println("other instance is already handling Expired Employee task ")
                    }
                }
            }, true) }
    }

    @Bean
    fun hzLockMechanism(jet : JetInstance) : CPSubsystem {
        return jet.hazelcastInstance.cpSubsystem
    }
}