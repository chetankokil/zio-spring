package com.example.controllers

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.beans.factory.annotation.Autowired
import com.example.services.LessonService
import com.example.models.Models
import cats.syntax.semigroup._
import cats.Monoid
import cats.instances.string._

@RestController
@Autowired
class DemoController(lessonService: LessonService) {

  @GetMapping(Array("/demo"))
  def demo = {
    "Some" |+| " Scala " |+| "With Cats"
    val a = Option(1)
    for {
      aVal <- a
      value <- aVal * 2 if (aVal == 1)
    } yield (value)
    lessonService.postLesson(Models.Lesson(None, "Test Lesson", None, None))
  }

}
