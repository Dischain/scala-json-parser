package sjp.json

import sjp.path.Path

sealed trait JsonVal extends Path with JsonTransformable

case object JsonNull extends  JsonVal {

override def toString: String = "\"null\""
}

sealed trait JsonBoolean extends JsonVal

case object JsonTrue extends JsonBoolean {
  override def toString: String = "true"
}
case object JsonFalse extends JsonBoolean {
  override def toString: String = "false"
}

case class JsonNumber(private[json] val value: Long) extends JsonVal {
  override def toString: String = value.toString
}

case class JsonString(private[json] val value: String) extends JsonVal {
  override def toString: String = value
}

case class JsonArray(private[json] val value: IndexedSeq[JsonVal] = Array[JsonVal]())
  extends JsonVal
{
  def ++(other: JsonArray): JsonArray = JsonArray(value ++ other.value)

  def :+(el: JsonVal): JsonArray = JsonArray(value :+ el)

  def +:(el: JsonVal): JsonArray = JsonArray(el +: value)

  override def toString: String = value.mkString(",")
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

  override def toString: String = members.toSeq.mkString(",")
}

object JsonObject {
  def apply(members: Seq[(String, JsonVal)]): JsonObject =
    members.foldLeft(JsonObject(Map[String, JsonObject]())) {
        (init: JsonObject, current: (String, JsonVal)) => init + current
      }
}
