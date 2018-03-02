package sjp.conversions

import sjp.json.{JsonError, JsonVal}

/**
  * Decodes JsonVal instance into either a value of type `T`
  * or `JsonError`. Should be provided
  * as implicit value, as shown in the example below.
  *
  * {{{
  * case class Person(name: String, address: Address)
  * case class Address(street: String, city: String)
  *
  * implicit val addressRead = new Readable[Address] {
  *   def read(addr: JsonVal): Either[JsonError, Person] = {
  *     for {
  *       street <- addr </> "street"
  *       city <- addr </> "city"
  *     } yield Address(street.asString, city.asString)
  *   }
  * }
  *
  * implicit val personRead = new Readable[Person] {
  *   def read(person: JsonVal): Either[JsonError, Person] = {
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
    * Convert from `JsonVal` to an instance of specified type `T`
    *
    * @param jsonVal json value to be decoded
    * @return either [[sjp.json.JsonError]] or value of type `T` to be decoded
    */
  def read(jsonVal: JsonVal): Either[JsonError, A]
}
