package sjp.json

/**
  * Json errors representation
  */
sealed trait JsonError

/**
  * Represents js-like undefined field error.
  */
case object JsonFieldUndefinedError extends JsonError

/**
  * Represents simple [[ArrayIndexOutOfBoundsException]]
  */
case object JsonArrayIndexOutOfBoundsError extends JsonError

/**
  * Represents incompatible with current type of [[sjp.json.JsonVal]]
  * operation error.
  */
case object IncompatibleOperationError extends JsonError
