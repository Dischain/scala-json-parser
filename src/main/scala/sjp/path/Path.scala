package sjp.path

import sjp.json.{JsonArray, JsonObject, JsonVal}

class Path { self: JsonVal =>
  def </>(field: String): Option[JsonVal] = this match {
    case JsonObject(fields) => fields.get("\"" + field + "\"")
    case _ => None
  }

  def <^>(index: Int): Either[]
}
