package com.example.services

import cats.effect.IO
import doobie._
import doobie.implicits._
import org.springframework.stereotype.Service
import zio.Runtime
import zio.console.Console
import javax.sql.DataSource
import cats.effect.unsafe.implicits.global
import com.example.models.Models._
import org.springframework.beans.factory.annotation.Autowired
import zio.logging._
import zio.{Has, Task, ZIO, ZLayer}


@Service
class LessonService(@Autowired val ds: DataSource) {
  val xa = Transactor
    .fromDataSource[IO](ds, scala.concurrent.ExecutionContext.global)

  val runtime = Runtime.default
  val logLayer = Logging.consoleErr()

  val backLayer = (Console.live ++ logLayer) >>> LessonService.live

  def postLesson(lesson: Lesson) = {
     runtime.unsafeRun(log.debug("IN the service").exitCode.provideCustomLayer(logLayer))
      val ls = LessonService.postLesson(lesson)
        .map(u => u.transact(xa).unsafeRunSync())
        .provideCustomLayer(backLayer)

    runtime.unsafeRun(ls)
  }

}

object LessonService {

  type LessonService = Has[Service]

  trait Service {
    def postLesson(lesson: Lesson): Task[ConnectionIO[Int]]
    def patchLesson(lesson: Lesson): Task[Lesson]
    def deleteLesson(id: String): Task[Unit]
  }

  def postLesson(lesson: Lesson): ZIO[LessonService,Throwable, ConnectionIO[Int]] = ZIO.accessM(_.get.postLesson(lesson))

  lazy val live: ZLayer[Logging, Nothing, LessonService] = ZLayer.fromService { (log) =>
    new Service {
        override def postLesson(lesson: Lesson) = {
          for {
            _   <- log.debug("Hello World")
            ls  <- Task(sql""" Insert into abc(name, createdAt, updatedAt)
                              values(lesson.name, now(), now())
                            """.stripMargin.update.run)
          } yield  ls

        }

        def patchLesson(lesson: Lesson): Task[Lesson] = ???

        def deleteLesson(id: String): Task[Unit] = ???

      }
    }

}
