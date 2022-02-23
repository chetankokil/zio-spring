package com.example.config

import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class Exceptionhandler extends   ResponseEntityExceptionHandler{


  override def handleExceptionInternal(ex: Exception, body: Any, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity[AnyRef] = super.handleExceptionInternal(ex, body, headers, status, request)

}
