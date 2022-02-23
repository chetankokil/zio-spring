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
import org.springframework.http.{HttpStatus, ResponseEntity}
import zio.{Runtime, ZLayer}
import zio.console.Console
import zio.logging.Logging

@RestController
@Autowired
class DemoController(lessonService: LessonService) {

  val logLayer = Logging.consoleErr()

  val backLayer = LessonService.live

  val runtime = Runtime.default

  @GetMapping(Array("/demo"))
  def demo = {
    val lessonRet = lessonService.postLesson(Models.Lesson(Some(1), "Chetan", None, None))
    ResponseEntity.ok().body(runtime.unsafeRun(lessonRet))
  }

}
