package sjp.json

import sjp.conversions.{ Writable, Readable }
import sjp.parser.Parser

object Json {
  def stringify(value: JsonVal): String = value.toString

  def parse(string: String): JsonVal = Parser.parse(string)

  def toJson[T](obj: T)(implicit jw: Writable[T]): JsonVal =
    jw.write(obj)

  def fromJson[T](value: JsonVal)(implicit jr: Readable[T]): Option[T] =
    jr.read(value)

  def obj(members: (String, JsonVal)*): JsonObject = JsonObject(members)
}
