package com.example.demo.Controller.GlobalException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice   // 이 클래스는 모든 컨트롤러에서 발생한 예외를 처리하는
                    // 전역 핸들러다라는 걸 선언하는 애노테이션
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String allExceptionHandler(Exception e, Model model){
        log.error("Global Exception Handler... " + e);
        model.addAttribute("ex", e);
        return "global_error";
    }

}
