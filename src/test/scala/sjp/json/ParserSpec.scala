package sjp.json

import org.scalatest.FunSpec
import sjp.parser.ParseError

class ParserSpec extends FunSpec {
  describe("Parser") {
    it("should parse incoming string in json format and return `JsonVal`") {
      assert(Json.parse("""{"key": "value"}""") == JsonObject(Seq("key" -> JsonString("value"))))
    }

    it("should parse incoming bad formed json-string format and throw `ParseError`") {
      assertThrows[ParseError](Json.parse("""{"key": value"}"""))
    }
  }
}
