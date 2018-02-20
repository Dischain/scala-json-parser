package sjp

import sjp.json.Json

object Test extends App {
  override def main(args: Array[String]) = {
    import sjp.path.Path

    println(Json.parse("""{"hello": "world"}"""))
  }
}
