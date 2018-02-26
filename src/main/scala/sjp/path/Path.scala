package sjp.path

import sjp.json._

class Path { self: JsonVal =>
  /**
    * Returns either [[sjp.json.JsonVal]] corresponding to given field name
    * or [[sjp.json.JsonError]] instance in case error:
    *   - [[sjp.json.JsonFieldUndefinedError]] if field is undefined on
    *   current [[JsonObject]]
    *   - [[sjp.json.IncompatibleOperationError]] if operation called on
    *   non-object instance
    *
    * @param field
    * @return `Either[JsonError, JsonVal]`
    */
  def </>(field: String): Either[JsonError, JsonVal] = this match {
    case JsonObject(fields) => fields(field) match {
      case value: JsonVal => Right(value)
      case _ => Left(JsonFieldUndefinedError)
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
    * @param index
    * @return `Either[JsonError, JsonVal]`
    */
  def <^>(index: Int): Either[JsonError, JsonVal] = this match {
    case JsonArray(values) => {
      if (index < 0 || index > values.length - 1) Left(JsonArrayIndexOutOfBoundsError)
      else Right(values(index))
    }
    case _ => Left(IncompatibleOperationError)
  }
}
