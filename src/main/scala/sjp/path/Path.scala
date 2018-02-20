package sjp.path

import sjp.json.{JsonObject, JsonVal}

object Path {
  implicit class Path(jo: JsonObject) {
    def </>(field: String): Option[JsonVal] = jo match {
      case JsonObject(fields) => fields.get(field)
      case _ => None
    }
  }
}

class Path { self: JsonObject =>
  def p(field: String): Option[JsonVal] = this match {
    case JsonObject(fields) => fields.get(field)
    case _ => None
  }
}
