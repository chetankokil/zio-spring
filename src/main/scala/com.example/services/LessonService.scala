package com.example.services

import org.springframework.stereotype.Service
import javax.sql.DataSource
import doobie.util.ExecutionContexts
import cats.effect.IO
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._

// This is just for testing. Consider using cats.effect.IOApp instead of calling
// unsafe methods directly.
import cats.effect.unsafe.implicits.global
import zio.{Has, Task}
import org.springframework.beans.factory.annotation.Autowired
import zio.console.Console
import zio.{ZIO, RIO, RLayer, ZLayer}
import doobie.util.update
import com.example.models.Models._

@Service
class LessonService(@Autowired val ds: DataSource) {
  val xa = Transactor
    .fromDataSource[IO](ds, scala.concurrent.ExecutionContext.global)

  val backLayer = Console.live ++ LessonService.live

  def postLesson(lesson: Lesson) = {
    val ls = LessonService
      .postLesson(lesson)
      .provideLayer(backLayer)
      .catchAll(t =>
        ZIO
          .succeed(t.printStackTrace())
          .map(err => throw new Exception(s"error $err"))
      )
      .map { u =>
        u.transact(xa)
        println(s"Registered user: $u")
      }

    ls.flatMap(l => ZIO.effectTotal(println(l)))
  }

}

object LessonService {

  type LessonService = Has[Service]

  trait Service {
    def postLesson(lesson: Lesson): Task[ConnectionIO[Int]]
    def patchLesson(lesson: Lesson): Task[Lesson]
    def deleteLesson(id: String): Task[Unit]
  }

  def postLesson(lesson: Lesson): RIO[LessonService, ConnectionIO[Int]] =
    ZIO.accessM(_.get.postLesson(lesson))

  lazy val live: RLayer[Console, LessonService] =
    ZLayer.fromService[Console.Service, Service] { (console) =>
      new Service {

        override def postLesson(lesson: Lesson): Task[ConnectionIO[Int]] =
          for {
            _ <- console.putStrLn(s"Hello World $lesson")
            sql <- Task(sql"""
                              Insert into lesson(name, createdAt, updatedAt)
                              values(lesson.name, now(), now())
                            """.stripMargin.update.run)
          } yield (sql)

        def patchLesson(lesson: Lesson): Task[Lesson] = ???

        def deleteLesson(id: String): Task[Unit] = ???

      }
    }

}
