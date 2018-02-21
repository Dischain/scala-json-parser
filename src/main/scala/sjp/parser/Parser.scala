package sjp.parser

import sjp.json._

import scala.util.parsing.combinator.JavaTokenParsers

sealed class Parser extends JavaTokenParsers {
  def obj: Parser[JsonObject] =
    "{" ~> repsep(member, ",") <~ "}" ^^
      (x => JsonObject(x.toMap))

  def arr: Parser[JsonArray] =
    "[" ~> repsep(value, ",") <~ "]" ^^
      (x => JsonArray(x.toIndexedSeq))

  def member: Parser[(String, JsonVal)] =
    stringLiteral ~ ":" ~ value ^^ {
      case name ~ ":" ~ value => (name.stripPrefix("\"").stripSuffix("\""), value)
    }

  def value: Parser[JsonVal] = (
    obj
      | arr
      | stringLiteral ^^ (x => JsonString(x.stripPrefix("\"").stripSuffix("\"")))
      | floatingPointNumber ^^ (x => JsonNumber(x.toLong))
      | "null"  ^^ (_ => JsonNull)
      | "true"  ^^ (_ => JsonTrue)
      | "false" ^^ (_ => JsonFalse)
    )
}

object Parser extends Parser {
  def parse(string: String): JsonVal = parseAll(value, string) match {
    case Success(res, _) => res
    case Error(msg, _) => throw new Exception(msg)
    case Failure(msg, _) => throw new Exception(msg)
  }
}
