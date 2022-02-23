ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"


val excludeSlf4jBinding = ExclusionRule(organization = "org.slf4j")
val excludeStarterLogging = ExclusionRule( "org.springframework.boot", "spring-boot-starter-logging")


lazy val root = (project in file("."))
  .settings(
    name := "zio-spring",
    libraryDependencies += "dev.zio" %% "zio" % "1.0.12" excludeAll(excludeSlf4jBinding, excludeStarterLogging),
    libraryDependencies += "org.postgresql" % "postgresql" % "42.2.25" excludeAll(excludeSlf4jBinding),
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % "2.5.3" excludeAll(excludeSlf4jBinding, excludeStarterLogging),
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-jdbc" % "2.5.3" excludeAll(excludeSlf4jBinding),
    libraryDependencies += "dev.zio" %% "zio-logging-slf4j" % "0.5.14",
    libraryDependencies += "org.tpolecat" %% "doobie-core" % "1.0.0-RC1" excludeAll(excludeSlf4jBinding),
    libraryDependencies += "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC1" excludeAll(excludeSlf4jBinding), // HikariCP transactor.
    libraryDependencies += "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC1" excludeAll(excludeSlf4jBinding),
      libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"

  )
