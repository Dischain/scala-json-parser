package sjp.json

import sjp.path.Path

sealed trait JsonVal extends Path with JsonTransformable {
  def stringify: String
}

case object JsonNull extends  JsonVal {

  def stringify: String = "null"
}

sealed trait JsonBoolean extends JsonVal

case object JsonTrue extends JsonBoolean {
  def stringify: String = "true"
}
case object JsonFalse extends JsonBoolean {
  def stringify: String = "false"
}

case class JsonNumber(private[json] val value: Long) extends JsonVal {
  def stringify: String = value.toString
}

case class JsonString(private[json] val value: String) extends JsonVal {
  def stringify: String = "\"" + value + "\""
}

case class JsonArray(private[json] val elements: IndexedSeq[JsonVal] = Array[JsonVal]())
  extends JsonVal
{
  def ++(other: JsonArray): JsonArray = JsonArray(elements ++ other.elements)

  def :+(el: JsonVal): JsonArray = JsonArray(elements :+ el)

  def +:(el: JsonVal): JsonArray = JsonArray(el +: elements)

  def stringify: String = "[" + (elements.map(el => el.stringify) mkString ",") + "]"
}

case class JsonObject(private[json] val members: Map[String, JsonVal] = Map[String, JsonVal]())
  extends JsonVal
{
  def fieldSet: Set[(String, JsonVal)] = members.toSet

  def keys: Set[String] = members.keySet

  def values: Iterable[JsonVal] = members.values

  def ++(other: JsonObject): JsonObject = JsonObject(members ++ other.members)

  def -(otherField: String): JsonObject = JsonObject(members - otherField)

  def +(otherField: (String, JsonVal)): JsonObject =
    JsonObject(members + otherField)

  def stringify: String = {
    "{" + (keys.map(key => "\"" + key + "\":" + members.get(key).get.stringify) mkString ",") + "}"
  }
}

object JsonObject {
  def apply(members: Seq[(String, JsonVal)]): JsonObject =
    members.foldLeft(JsonObject(Map[String, JsonObject]())) {
        (init: JsonObject, current: (String, JsonVal)) => init + current
    }
}
