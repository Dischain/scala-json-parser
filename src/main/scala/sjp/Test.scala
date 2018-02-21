package sjp

import sjp.conversions.Writable
import sjp.json.{Json, JsonString, JsonVal}

object Test extends App {
  override def main(args: Array[String]) = {

//    println(Json.parse("""{"hello": "world"}""") </> "hello")

//    val i: Option[JsonVal] = for {
//      arr <- Json.parse("""{"hello": ["world1", "world2"]}""") </> "hello"
//      item <- arr <^> 1
//    } yield item
//    println(i)

    case class Person(name: String, address: Address)
    case class Address(street: String, city: String)

    implicit val addressWriteable = new Writable[Address] {
      def write(addr: Address): JsonVal = {
        Json.obj(
          "street" -> JsonString(addr.street),
          "city" -> JsonString(addr.city)
        )
      }
    }

    implicit val personWriteable = new Writable[Person] {
      def write(person: Person): JsonVal = {
        Json.obj(
          "name" -> JsonString(person.name),
          "address" -> Json.toJson(person.address)
        )
      }
    }

    val a = for {
      addr <- Json.toJson(Person("John", Address("first", "LA"))) </> "address"
      city <- addr </> "city"
    } yield city

    println(Json.parse(Json.stringify(Json.toJson(Person("John", Address("first", "LA"))))) </> "name")

    println(Json.stringify(Json.toJson(Address("first", "LA"))))
  }
}
