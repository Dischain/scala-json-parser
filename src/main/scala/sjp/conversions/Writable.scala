package sjp.conversions

import sjp.json.JsonVal

/**
  * {{{
  * case class Person(name: String, address: Address)
  * case class Address(street: String, city: String)
  *
  * implicit val addressWriteable = new Writable[Address] {
  *   def write(addr: Address): JsonVal = {
  *     Json.obj(
  *       street -> addr.street,
  *       city -> addr.city
  *     )
  *   }
  * }
  *
  * implicit val personWriteable = new Writable[Person] {
  *   def write(person: Person): JsonVal = {
  *     Json.obj(
  *       name -> person.name,
  *       address -> Json.toJson(person.address)
  *     )
  *   }
  * }
  * }}}
  */
trait Writable[A] {
  def write(obj: A): JsonVal
}