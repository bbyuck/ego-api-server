package hanta.bbyuck.egoapiserver.controller;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import hanta.bbyuck.egoapiserver.exception.http.*;
import hanta.bbyuck.egoapiserver.exception.lol.UnknownException;
import hanta.bbyuck.egoapiserver.exception.lol.UserAuthenticationException;
import hanta.bbyuck.egoapiserver.exception.lol.UserNotFoundException;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@RestController
public class ResponseController {

    @ExceptionHandler(AbstractResponseException.class)
    public ResponseMessage abstractResponseException(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     final AbstractResponseException exception) {
        log.error("AbstractResponseException : " + exception.getMessage());
        response.setStatus(exception.getHttpStatus().value());
        return new ResponseMessage(exception, request.getRequestURL().toString());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage badRequestException(HttpServletRequest request, final BadRequestException exception) {
        log.error("BadRequestException : " + exception.getMessage());
        return new ResponseMessage(new BadRequestException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage conflictException(HttpServletRequest request, final ConflictException exception) {
        log.error("ConflictException : " + exception.getMessage());
        return new ResponseMessage(new ConflictException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage forbiddenException(HttpServletRequest request, final ForbiddenException exception) {
        log.error("ForbiddenException : " + exception.getMessage());
        return new ResponseMessage(new ForbiddenException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage lockedException(HttpServletRequest request, final LockedException exception) {
        log.error("LockedException : " + exception.getMessage());
        return new ResponseMessage(new LockedException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseMessage methodNotAllowedException(HttpServletRequest request, final MethodNotAllowedException exception) {
        log.error("MethodNotAllowedException : " + exception.getMessage());
        return new ResponseMessage(new MethodNotAllowedException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(NotAcceptableException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage notAcceptableException(HttpServletRequest request, final NotAcceptableException exception) {
        log.error("NotAcceptableException : " + exception.getMessage());
        return new ResponseMessage(new NotAcceptableException(exception.getMessage(),exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage notFoundException(HttpServletRequest request, final NotFoundException exception) {
        log.error("NotFoundException : " + exception.getMessage());
        return new ResponseMessage(new NotFoundException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(PayloadTooLargeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage payloadTooLargeException(HttpServletRequest request, final PayloadTooLargeException exception) {
        log.error("PayloadTooLargeException : " + exception.getMessage());
        return new ResponseMessage(new PayloadTooLargeException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(PreconditionRequiredException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage preconditionRequiredException(HttpServletRequest request, final PreconditionRequiredException exception) {
        log.error("PreconditionRequiredException : " + exception.getMessage());
        return new ResponseMessage(new PreconditionRequiredException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(RequestTimeoutException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage requestTimeoutException(HttpServletRequest request, final RequestTimeoutException exception) {
        log.error("RequestTimeoutException : " + exception.getMessage());
        return new ResponseMessage(new RequestTimeoutException(exception.getMessage(), exception),request.getRequestURL().toString());
    }

    @ExceptionHandler(TooManyRequestsException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage tooManyRequestException(HttpServletRequest request, final TooManyRequestsException exception) {
        log.error("TooManyRequestException : " + exception.getMessage());
        return new ResponseMessage(new TooManyRequestsException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseMessage unauthorizedException(HttpServletRequest request, final UnauthorizedException exception) {
        log.error("UnauthorizedException : " + exception.getMessage());
        return new ResponseMessage(new UnauthorizedException("User Not Authorized", exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseMessage userNotFoundException(HttpServletRequest request, final UserNotFoundException exception) {
        log.error("UserNotFoundException : " + exception.getMessage());
        return new ResponseMessage(new UserNotFoundException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(UserAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseMessage userAuthenticationException(HttpServletRequest request, final UserAuthenticationException exception) {
        log.error("UserAuthenticationException : " + exception.getMessage());
        return new ResponseMessage(new UserAuthenticationException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage exception(HttpServletRequest request, final Exception exception) {
        log.error("Exception : " + exception.getMessage());
        return new ResponseMessage(new UnknownException(exception.getMessage(), exception), request.getRequestURL().toString());
    }
}
