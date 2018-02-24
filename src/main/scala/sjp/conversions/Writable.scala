package sjp.conversions

import sjp.json.JsonVal

/**
  * Serializes an object to Json. You should define your own
  * implicit `Writable` as shown in example below.
  *
  * {{{
  * case class Person(name: String, address: Address)
  * case class Address(street: String, city: String)
  *
  * implicit val addressWritable = new Writable[Address] {
  *   def write(addr: Address): JsonVal = {
  *     Json.obj(
  *       "street" -> JsonString(addr.street),
  *       "city" -> JsonString(addr.city)
  *     )
  *   }
  * }
  *
  * implicit val personWritable = new Writable[Person] {
  *   def write(person: Person): JsonVal = {
  *     Json.obj(
  *       "name" -> JsonString(person.name),
  *       "address" -> Json.toJson(person.address)
  *     )
  *   }
  * }
  * }}}
  */
trait Writable[A] {
  /**
    * Convert an object into a JsonVal
    *
    * @param obj object to serialize
    * @return JsonVal
    */
  def write(obj: A): JsonVal
}