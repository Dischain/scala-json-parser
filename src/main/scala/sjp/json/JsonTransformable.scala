package sjp.json

import sjp.conversions.Readable

/**
  * Provides a generic method to convert `JsonVal` to
  * a specific type `T` and some basic converters from
  * primitive types to a `JsonVal`
  */
trait JsonTransformable { self: JsonVal =>
  /**
    * Converts `JsonVal` to a specified instance of type `T`
    *
    * @param readable implicit converter
    * @tparam T type to convert*
    * @return an instance of type `T`
    * @throws sjp.json.JsonError
    */
  def as[T](implicit readable: Readable[T]): T = readable.read(this) match {
    case Right(result) => result
    case Left(throwable: JsonError) => throw throwable
  }

  def asString: String = this match {
    case JsonString(str) => str
    case _ => throw new Exception
  }

  def asNum: Long = this match {
    case JsonNumber(num) => num
    case _ => throw new Exception
  }

  def asBoolean: Boolean = this match {
    case JsonTrue => true
    case JsonFalse => false
    case _ => throw new Exception
  }

  def asArray: IndexedSeq[JsonVal] = this match {
    case JsonArray(elements) => elements
    case _ => throw new Exception
  }
}