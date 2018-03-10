package sjp.parser

/**
  * Represent a scala combinator parsing error

  * @param msg message
  */
final case class ParseError(msg: String) extends Throwable
