package com.example

import com.example.services.LessonService
import org.springframework.boot.autoconfigure.SpringBootApplication
import zio._
import zio.console.Console
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan
import zio.logging.{LogFormat, LogLevel, Logging}

@SpringBootApplication
class Application

object Application extends zio.App {

  val env =
    Logging.console(
      logLevel = LogLevel.Info,
      format = LogFormat.ColoredLogFormat()
    ) >>> Logging.withRootLoggerName("LessonService")

  val backLayer = Console.live ++ env ++ LessonService.live

  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] =
    managedSpringApp(args).useForever
      .provideLayer(Console.live)
      .fold(
        _ => ExitCode.failure,
        _ => ExitCode.success
      )

  private def managedSpringApp(
      args: List[String]
  ): ZManaged[Console, Throwable, ConfigurableApplicationContext] =
    ZManaged.make(
      console.putStrLn("Starting spring container...") *>
        ZIO.effect(SpringApplication.run(classOf[Application], args: _*))
    )(ctx =>
      ZIO
        .effect(if (ctx.isActive()) SpringApplication.exit(ctx))
        .catchAll(ex => throw new Exception(s"Error $ex"))
    )
}
