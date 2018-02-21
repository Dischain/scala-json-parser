package sjp.path

import sjp.json.{JsonArray, JsonObject, JsonVal}

class Path { self: JsonVal =>
  def </>(field: String): Option[JsonVal] = this match {
    case JsonObject(fields) => fields.get(field)
    case _ => None // TODO: handle exceptional situations
  }

  def <^>(index: Int): Option[JsonVal] = this match {
    case JsonArray(values) => Some(values(index))
    case _ => None // TODO: handle exceptional situations
  }
}
