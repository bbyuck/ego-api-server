package hanta.bbyuck.egoapiserver.controller;

import hanta.bbyuck.egoapiserver.exception.*;
import hanta.bbyuck.egoapiserver.exception.http.*;
import hanta.bbyuck.egoapiserver.exception.lol.UnknownException;
import hanta.bbyuck.egoapiserver.exception.lol.UserNotFoundException;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * HANTA - Response Controller class
 *
 * @ description : Custom Excepiton Handling Controller
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

@Slf4j
@ControllerAdvice
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
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
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
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

    @ExceptionHandler(UnauthorizedAppVersionException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseMessage unauthorizedAppVersionException(HttpServletRequest request, final UnauthorizedAppVersionException exception) {
        log.error("UnauthorizedAppVersionException : " + exception.getMessage());
        return new ResponseMessage(new UnauthorizedAppVersionException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage exception(HttpServletRequest request, final Exception exception) {
        log.error("Exception : " + exception.getMessage());
        return new ResponseMessage(new UnknownException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseMessage authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return new ResponseMessage(new CAuthenticationEntryPointException(e.getMessage(), e), request.getRequestURL().toString());
    }

    @ExceptionHandler(CAccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseMessage accessDeniedException(HttpServletRequest request, CAccessDeniedException e) {
        return new ResponseMessage(new CAccessDeniedException(e.getMessage(), e), request.getRequestURL().toString());
    }

    @ExceptionHandler(BadMatchRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage BadMatchRequestException(HttpServletRequest request, final BadMatchRequestException exception) {
        log.error("BadMatchRequestException : " + exception.getMessage());
        return new ResponseMessage(new BadMatchRequestException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(SendRequestExhaustedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage SendRequestExhaustedException(HttpServletRequest request, final SendRequestExhaustedException exception) {
        log.error("SendRequestExhaustedException : " + exception.getMessage());
        return new ResponseMessage(new SendRequestExhaustedException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(MatchNotExistException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage MatchNotExistException(HttpServletRequest request, final MatchNotExistException exception) {
        log.error("MatchNotExistException : " + exception.getMessage());
        return new ResponseMessage(new MatchNotExistException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(RecommendRequestOverException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage RecommendRequestOverException(HttpServletRequest request, final RecommendRequestOverException exception) {
        log.error("RecommendRequestOverException : " + exception.getMessage());
        return new ResponseMessage(new RecommendRequestOverException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(RecommendRefreshOverException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage RecommendRefreshOverException(HttpServletRequest request, final RecommendRefreshOverException exception) {
        log.error("RecommendRefreshOverException : " + exception.getMessage());
        return new ResponseMessage(new RecommendRefreshOverException(exception.getMessage(), exception), request.getRequestURL().toString());
    }

    @ExceptionHandler(NoUserException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage NoUserException(HttpServletRequest request, final NoUserException exception) {
        log.error("NoUserException : " + exception.getMessage());
        return new ResponseMessage(new NoUserException(exception.getMessage(), exception), request.getRequestURL().toString());
    }
}
