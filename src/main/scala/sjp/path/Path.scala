package sjp.path

import java.util.NoSuchElementException

import sjp.json._

class Path { self: JsonVal =>
  /**
    * Returns either [[sjp.json.JsonVal]] corresponding to given field name
    * or [[sjp.json.JsonError]] instance in case error:
    *   - [[sjp.json.JsonFieldUndefinedError]] if field is undefined on
    *   current [[sjp.json.JsonObject]]
    *   - [[sjp.json.IncompatibleOperationError]] if operation called on
    *   non-object instance
    *
    * @param field name of field
    * @return `Either[JsonError, JsonVal]`
    */
  def </>(field: String): Either[JsonError, JsonVal] = this match {
    case JsonObject(fields) => try {
      Right(fields(field))
    } catch {
      case e: NoSuchElementException => Left(JsonFieldUndefinedError(field))
    }
    case _ => Left(IncompatibleOperationError)
  }

  /**
    * Returns either [[sjp.json.JsonVal]] corresponding to given array
    * index or [[sjp.json.JsonError]] instance in case error:
    *   - [[sjp.json.JsonArrayIndexOutOfBoundsError]]
    *   - [[sjp.json.IncompatibleOperationError]] if operation called on
    *   non-object instance
    *
    * @param index array index
    * @return `Either[JsonError, JsonVal]`
    */
  def <^>(index: Int): Either[JsonError, JsonVal] = this match {
    case JsonArray(values) =>
      if (index < 0 || index > values.length - 1)
        Left(JsonArrayIndexOutOfBoundsError(index, values.length))
      else Right(values(index))
    case _ => Left(IncompatibleOperationError)
  }
}
