package com.socat.socatserver.config

import com.mongodb.ReadPreference
import com.mongodb.WriteConcern
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.WriteResultChecking
import org.springframework.data.mongodb.core.convert.MongoConverter

@Configuration
class MongodbConfig {


    @Bean
    fun mongoTemplate(factory: MongoDatabaseFactory, converter: MongoConverter): MongoOperations {

        val mongoTemplate = MongoTemplate(factory, converter)
        mongoTemplate.let {
            it.setWriteResultChecking(WriteResultChecking.EXCEPTION)
            it.setReadPreference(ReadPreference.secondaryPreferred())
            it.setWriteConcern(WriteConcern.ACKNOWLEDGED)
        }

        return mongoTemplate
    }

}