package sjp.json

import sjp.conversions.{ Writable, Readable }
import sjp.parser.Parser

/**
  * Set of helper functions to serialize/deserialize json values
  */
object Json {
  /**
    * Represent a [[sjp.json.JsonVal]] as json string
    *
    * @param value `JsonVal` to be stringified
    * @return a string representation of `JsonVal`
    */
  def stringify(value: JsonVal): String = value.stringify

  /**
    * Parse a json string and returns it as [[sjp.json.JsonVal]]
    *
    * @param string the string to parse
    * @return `JsonVal`
    */
  def parse(string: String): JsonVal = Parser.parse(string)

  /**
    * Convert an object to [[sjp.json.JsonVal]]
    *
    * @param obj object to be converted as `JsonVal`
    * @param jw implicit [[sjp.conversions.Writable]] to serialize `obj`
    *           as json object
    * @tparam T the type of the value to be written as `JsonVal`
    * @return json representation of an `obj`
    */
  def toJson[T](obj: T)(implicit jw: Writable[T]): JsonVal =
    jw.write(obj)

  /**
    * Convert [[sjp.json.JsonVal]] to a value of requested type `T`
    *
    * @param value a json representation of an instance object of type `T`
    * @param jr implicit [[sjp.conversions.Readable]] to deserialize `value`
    * @tparam T the type of the value to be deserialized from `JsonVal`
    * @return optional of expected type `T` representing required instance
    */
  def fromJson[T](value: JsonVal)(implicit jr: Readable[T]): Option[T] =
    jr.read(value)

  /**
    * Creates a [[sjp.json.JsonObject]] from given `Seq` of key-value pairs
    *
    * @param members sequence of key-value pairs
    * @return `JsonObject` constructed from the given pairs
    */
  def obj(members: (String, JsonVal)*): JsonObject = JsonObject(members)
}
