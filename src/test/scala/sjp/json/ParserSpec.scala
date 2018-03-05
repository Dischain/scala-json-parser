package sjp.json

import org.scalatest.FunSpec

class ParserSpec extends FunSpec {
  describe("Parser") {
    it("should parse incoming string in json format and return `JsonVal`") {
      assert(Json.parse("""{"key": "value"}""") == JsonObject(Seq("key" -> JsonString("value"))))
    }
  }
}
