package sjp.json

import org.scalatest.FunSpec
import sjp.conversions.Writable

class JsonValSpec extends FunSpec {
  case class Address(street: String, city: String)
  case class ArrayBased(arr: IndexedSeq[String])

  implicit val addressWritable: Writable[Address] = (addr: Address) =>
    Json.obj(
      "street" -> JsonString(addr.street),
      "city" -> JsonString(addr.city))

  implicit val arrayBasedWritable: Writable[ArrayBased] = (arrayBased: ArrayBased) =>
    Json.obj(
      "arr" -> JsonArray(arrayBased.arr map { JsonString })
    )

  val addressJsonVal: JsonVal = Json.toJson(Address("21rst", "Noname"))
  val arrayBased: JsonVal = Json.toJson(ArrayBased(IndexedSeq("1", "2")))

  describe("JsonVal") {
    describe("when look for value by field name") {
      it("should return `Either.Right` containing `JsonValue` corresponding to given field name") {
        addressJsonVal </> "street" match {
          case Right(JsonString(str)) => assert(str == "21rst")
        }
      }

      it("should return `Either.Left` containing `JsonFieldUndefinedError` when looking up " +
        "non-existing field") {
        addressJsonVal </> "undef" match {
          case Left(JsonFieldUndefinedError) => assert(true)
        }
      }

      it("should return Either.Left containing `IncompatibleOperationError` when called" +
        "on non-`JsonObject` instance of `JsonVal`") {
        JsonArray(IndexedSeq("1", "2") map { JsonString }) </> "some" match {
          case Right(v) => println(v)
          case Left(e) => println(e); assert(true)
        }
      }
    }

    describe("when look for value by index") {
      it("should return `Either.Right` containing `JsonValue` by given array index") {
        (arrayBased </> "arr") map {
          case fieldVal: JsonVal => fieldVal <^> 0 match {
            case Right(JsonString(value)) => assert(value == "1")
          }
        }
      }

      it("should return `Either.Left` containing `JsonArrayIndexOutOfBoundsError` when" +
        "called with non-existing index") {
        (arrayBased </> "arr") map {
          case fieldVal: JsonVal => fieldVal <^> 2 match {
            case Left(JsonArrayIndexOutOfBoundsError) => assert(true)
          }
        }
      }

      it("should return Either.Left containing `IncompatibleOperationError` when called" +
        "on non-`JsonArray` instance of `JsonVal`") {
        addressJsonVal <^> 1 match {
          case Left(IncompatibleOperationError) => assert(true)
        }
      }
    }
  }
}
