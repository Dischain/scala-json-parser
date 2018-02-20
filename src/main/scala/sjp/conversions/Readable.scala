package sjp.conversions

import sjp.json.JsonVal

/**
  * {{{
  * case class Person(name: String, address: Address)
  * case class Address(street: String, city: String)
  *
  * implicit val addressRead = new Readable[Address] {
  *   def read(addr: JsonVal): Option[Address] = {
  *     for {
  *       street <- (addr </> "street") as[String]
  *       city <- (addr </> "city") as[String]
  *     } Option(Address(street, city))
  *   }
  * }
  *
  * implicit val personRead = new Readable[Person] {
  *   def read(person: JsonVal): Option[Person] = {
  *     for {
  *       name <- (person </> "name") as[String]
  *       address <- (person </> "address") as[Address]
  *     }
  *   }
  * }
  * }}}
  */
trait Readable[A] {
  def read(jsonVal: JsonVal): Option[A]
}
