package cn.eblcu.sso.ui.exception;

import cn.eblcu.sso.domain.exception.DomainException;
import cn.eblcu.sso.persistence.exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
  * @Author hanchuang
  * @Version 1.0
  * @Date add on 2019/6/3
  * @Description   异常统一处理
  */
@RestController
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /*处理自定义的持久层异常*/
    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handlerException(PersistenceException e){
        log.error("持久层业务异常："+e.getMessage(),e);
        HttpHeaders headers = new HttpHeaders();
        // todo 可以封装一些通用的东西到消息头中
        headers.add("app-key","123456");
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    /*处理自定义的domain层异常*/
    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handlerException(DomainException e){
        log.error("domain层业务异常："+e.getMessage(),e);
        HttpHeaders headers = new HttpHeaders();
        // todo 可以封装一些通用的东西到消息头中
        headers.add("app-key","123456");
        return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }


    /*处理接口参数异常*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handlerMethodArgumentException(MethodArgumentNotValidException e){
        log.error("参数校验异常："+e.getMessage(),e);
        BindingResult bindingResult = e.getBindingResult();
        return new ResponseEntity<>(bindingResult.getFieldError().toString(), HttpStatus.BAD_REQUEST);
    }


    /*处理系统异常*/
    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("系统异常!"+e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
