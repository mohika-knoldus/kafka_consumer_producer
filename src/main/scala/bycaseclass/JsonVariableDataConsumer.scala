package bycaseclass

import JsonVariableDataProducer.User
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods

import java.util.Properties
object JsonVariableDataConsumer extends App {
  val topic = "user-information"

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "json-consumer-group")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(java.util.Collections.singletonList(topic))

  implicit val formats: DefaultFormats.type = DefaultFormats

  while(true) {
    val records = consumer.poll(java.time.Duration.ofMillis(100))
    import scala.jdk.CollectionConverters._
    for(record <- records.asScala) {
      val userJson = record.value()
      val user = JsonMethods.parse(userJson).extract[User]
      println(s"Received USer: $user")
    }
  }
  consumer.close()
}
