package sjp.json

import org.scalatest.FunSpec
import sjp.conversions.{Readable, Writable}

class JsonSpec extends FunSpec {
  describe("Json") {
    case class Address(street: String, city: String)
    case class Person(name: String, address: Address)
    case class ArrayBased(arr: IndexedSeq[String])

    describe(".parse method") {
      it("should parse simple key-value json string to JsonVal") {
        assert(Json.parse("""{"hello": "world"}""") ==
          JsonObject(Map("hello" -> JsonString("world"))))
      }

      it("should parse json string with key corresponding to array value to JsonVal") {
        assert(Json.parse("""{"arr": ["frst", "second"]}""") ==
          JsonObject(Map("arr" -> JsonArray(IndexedSeq(JsonString("frst"), JsonString("second"))))))
      }

      it("should parse json string with key corresponding to another object to JsonVal") {
        assert(Json.parse("""{"key1": {"key2": "someval"}}""") ==
          JsonObject(Map("key1" -> JsonObject(Map("key2" -> JsonString("someval"))))))
      }

      it("should finally parse some complex json string to JsonVal") {
        assert(Json.parse(
          """{
            |  "key1": {
            |    "key2": 1,
            |    "key3": {
            |      "key4": {
            |        "key5": "wow!"
            |      }
            |    }
            |  },
            |  "key6": [null, 123244400, -1, "&^@^#&", true, false]
            |}""".stripMargin) ==
          JsonObject(
            Map("key1" -> JsonObject(
              Map("key2" -> JsonNumber(1),
                "key3" -> JsonObject(Map(
                  "key4" -> JsonObject(Map(
                    "key5" -> JsonString("wow!")
                  ))
                ))
              )),
              "key6" -> JsonArray(IndexedSeq(
                JsonNull, JsonNumber(123244400), JsonNumber(-1), JsonString("&^@^#&"), JsonTrue, JsonFalse
              ))
            )
          )
        )
      }
    }

    describe(".stringify method") {
      it("should convert a simple json value to json string ") {
        assert(Json.stringify(JsonObject(Map("key" -> JsonString("value")))) ==
          """{"key":"value"}""")
      }

      it("should convert json value with a key corresponding to array to a json string") {
        assert(Json.stringify(JsonObject(Map("arr" -> JsonArray(IndexedSeq(JsonString("frst"), JsonString("second")))))) ==
          """{"arr":["frst","second"]}""")
      }

      it("should convert json value with a key corresponding to another json object to a json string") {
        assert(Json.stringify(JsonObject(Map("key1" -> JsonObject(Map("key2" -> JsonString("someval")))))) ==
          """{"key1":{"key2":"someval"}}""")
      }

      it("should finally convert some complex json object to a json string") {
        assert("""{"key1":{"key2":1,"key3":{"key4":{"key5":"wow!"}}},"key6":[null,123244400,-1,"&^@^#&",true,false]}""" ==
          Json.stringify(JsonObject(
            Map("key1" -> JsonObject(
              Map("key2" -> JsonNumber(1),
                "key3" -> JsonObject(Map(
                  "key4" -> JsonObject(Map(
                    "key5" -> JsonString("wow!")
                  ))
                ))
              )),
              "key6" -> JsonArray(IndexedSeq(
                JsonNull, JsonNumber(123244400), JsonNumber(-1), JsonString("&^@^#&"), JsonTrue, JsonFalse
              ))
            )
          ))
        )
      }
    }

    describe(".toJson method") {
      implicit val addressWritable: Writable[Address] = (addr: Address) =>
        Json.obj(
          "street" -> JsonString(addr.street),
          "city" -> JsonString(addr.city))

      implicit val personWritable: Writable[Person] = (person: Person) =>
        Json.obj(
          "name" -> JsonString(person.name),
          "address" -> Json.toJson(person.address))

      implicit val arrayBasedWritable: Writable[ArrayBased] = (arrayBased: ArrayBased) =>
        Json.obj(
          "arr" -> JsonArray(arrayBased.arr map { JsonString })
        )

      it("should convert a plain object to JsonVal") {
        assert(Json.toJson(Person("John", Address("21rst", "Noname"))) ==
          JsonObject(Map("name" -> JsonString("John"),
            "address" ->
            JsonObject(Map("street" -> JsonString("21rst"), "city" -> JsonString("Noname"))))))
      }

      it("should convert a plain object which contains an array to JsonVal") {
        assert(Json.toJson(ArrayBased(IndexedSeq[String]("a", "b"))) ==
          JsonObject(Map("arr" ->
            JsonArray(IndexedSeq(JsonString("a"), JsonString("b"))))))
      }
    }

    describe(".fromJson method") {
      implicit val addressRead: Readable[Address] = (addr: JsonVal) =>
        for {
          street <- addr </> "street"
          city <- addr </> "city"
        } yield Address(street.asString, city.asString)

      implicit val personRead: Readable[Person] = (person: JsonVal) =>
        for {
          name <- person </> "name"
          address <- person </> "address"
        } yield Person(name.asString, address.as[Address])

      implicit val arrayBasedRead: Readable[ArrayBased] = (arrBased: JsonVal) =>
        for {
          arr <- arrBased </> "arr"
        } yield ArrayBased(arr.asArray map {
          case JsonString(value) => value
        })

      it("should convert JsonVal to `Some` object") {
        assert(Right(Person("John", Address("21rst", "Noname"))) ==
          Json.fromJson[Person](JsonObject(Map("name" -> JsonString("John"),
          "address" ->
            JsonObject(Map("street" -> JsonString("21rst"), "city" -> JsonString("Noname")))))))
      }

      it("should convert JsonVal which contains an array of values to `Some` object") {
        assert(Right(ArrayBased(IndexedSeq[String]("a", "b"))) ==
          Json.fromJson[ArrayBased](JsonObject(Map("arr" ->
            JsonArray(IndexedSeq(JsonString("a"), JsonString("b")))))))
      }
    }
  }
}