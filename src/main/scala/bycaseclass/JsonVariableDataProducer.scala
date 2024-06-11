package bycaseclass

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.json4s.DefaultFormats
import org.json4s.native.Serialization

import java.util.Properties
import scala.util.Random

object JsonVariableDataProducer extends App {
  case class User(firstName: String, lastName: String, location: String, online: Boolean, followers: Int)
  implicit val format: DefaultFormats.type = DefaultFormats

  val locationList = List("Ocean", "Oregon", "Indiana", "Washington", "NewYork")
  val onlineStatus = List(true, false)

  val random = new Random()

  val usersList = List(User("Sammy", "Shark", locationList(random.nextInt(locationList.size)), onlineStatus(random.nextInt(onlineStatus.size)), Random.between(500, 500000)))
  val topic = "user-information"

  val props = new Properties()

  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)

  for(user <- usersList) {
    val userJson = Serialization.write(user)
    val record = new ProducerRecord[String, String](topic, userJson)
    producer.send(record)
  }
  producer.close()
}
