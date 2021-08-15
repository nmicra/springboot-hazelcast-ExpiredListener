# springboot-hazelcast-ExpiredListener
Demonstrates how to use EntryExpiredListener of Hazelcast. And how to coordinate that only one springboot-instance will execute the task


# How to run

1. gradle clean build
2. docker build -t hz-exp .
3. docker-compose up
4. Execute http://localhost:8080/testme (or with port 8081 - the second instance). This will create Employee instance in Hazelcast which expires in 3 seconds.
5. After 3 seconds verify in the logs that Employee object expiration was handled by one (and only one of the instances).