package sjp.json

import sjp.conversions.Readable

trait JsonTransformable { self: JsonVal =>
  def as[T](implicit readable: Readable[T]): T = readable.read(this) match {
    case Some(result) => result
    case None => throw new Exception
  }
}