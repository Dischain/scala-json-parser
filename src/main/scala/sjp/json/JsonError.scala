package sjp.json

/**
  * Json errors representation
  */
sealed trait JsonError extends Throwable {
  def msg: String
}

/**
  * Represents js-like undefined field error.
  */
case class JsonFieldUndefinedError(field: String) extends JsonError {
  override def msg: String = s"Object does not contains $field field"
}

/**
  * Represents simple [[ArrayIndexOutOfBoundsException]]
  */
case class JsonArrayIndexOutOfBoundsError(index: Int, length: Int)
  extends JsonError
{
  override def msg: String =
    s"Index $index is out of bounds of an array with length $length"
}

/**
  * Represents incompatible with current type of [[sjp.json.JsonVal]]
  * operation error.
  */
case object IncompatibleOperationError extends JsonError {
  override def msg: String = "Operation is not supported on this " +
    "type of JsonVal instance"
}
