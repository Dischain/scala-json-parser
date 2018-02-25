package sjp.conversions

import sjp.json.JsonVal

/**
  * Decodes JsonVal instance into a optional value. Should be provided
  * as implicit value, as shown in the example below.
  *
  * {{{
  * case class Person(name: String, address: Address)
  * case class Address(street: String, city: String)
  *
  * implicit val addressRead = new Readable[Address] {
  *   def read(addr: JsonVal): Option[Person] = {
  *     for {
  *       street <- addr </> "street"
  *       city <- addr </> "city"
  *     } yield Address(street.asString, city.asString)
  *   }
  * }
  *
  * implicit val personRead = new Readable[Person] {
  *   def read(person: JsonVal): Option[Person] = {
  *     for {
  *       name <- person </> "name"
  *       address <- person </> "address"
  *     } yield Person(name.asString, address.as[Address])
  *   }
  * }
  * }}}
  */
trait Readable[A] {
  /**
    *
    * @param jsonVal json value to be decoded
    * @return optional value to be decoded
    */
  def read(jsonVal: JsonVal): Option[A]
}
