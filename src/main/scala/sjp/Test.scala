package sjp

import sjp.json.Json

object Test extends App {
  override def main(args: Array[String]) = {

    println(Json.parse("""{"hello": "world"}""") </> "hello")
  }
}
