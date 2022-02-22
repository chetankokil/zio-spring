package com.example.models

import java.time.LocalDateTime

object Models {

  case class Lesson(
      id: Option[Int],
      name: String,
      createdAt: Option[LocalDateTime],
      updatedAt: Option[LocalDateTime]
  )

}
