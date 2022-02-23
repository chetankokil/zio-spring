package com.example.models

import zio.json._

import java.time.LocalDateTime

object Models {

  case class Lesson(
      id: Option[Int],
      name: String,
      createdAt: Option[LocalDateTime],
      updatedAt: Option[LocalDateTime]
  )

  object Lesson {
    implicit val decoder: JsonDecoder[Lesson] = DeriveJsonDecoder.gen
    implicit val encoder: JsonEncoder[Lesson] = DeriveJsonEncoder.gen
  }

}
