ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "zio-spring",
    libraryDependencies += "dev.zio" %% "zio" % "1.0.12",
    libraryDependencies += "org.postgresql" % "postgresql" % "42.2.25",
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % "2.5.3",
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-jdbc" % "2.5.3",
    libraryDependencies += "org.tpolecat" %% "doobie-core" % "1.0.0-RC1",
    libraryDependencies += "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC1", // HikariCP transactor.
    libraryDependencies += "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC1"
  )
