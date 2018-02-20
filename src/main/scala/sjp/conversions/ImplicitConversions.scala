package sjp.conversions

import sjp.json.{JsonNumber, JsonVal}

object ImplicitConversions {
  implicit val intReadable = new Readable[Int] {
    def read(jn: JsonVal): Option[Int] = jn match {
      case JsonNumber(number: Long) => Some(number.toInt)
      case _ => None
    }
  }

  implicit val floatReadable = new Readable[Float] {
    def read(jn: JsonVal): Option[Float] = jn match {
      case JsonNumber(number: Long) => Some(number)
      case _ => None
    }
  }

  // ...
}
