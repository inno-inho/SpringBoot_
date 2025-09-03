package com.example.demo.Controller;


import com.example.demo.Dto.PersonDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/param")
public class ParameterController {

    @RequestMapping(value="/p01",method= RequestMethod.GET)
    public void paramHandler_1(@RequestParam(name="username",required = false) String name){
        // 입력하는 이름이 여기 쓴 이름(name)과 다를경우에는 명시해야함
        log.info("GET /param/p01...."+name);
    }

    @GetMapping("/p02")
    public void paramHandler_2(@RequestParam(name="username",required = true) String name){

        log.info("GET /param/p02...."+name);
    }

    // Post로 할 시에는 Body로 인자 받아야함
    @PostMapping("/p03")
    public void ParamHandler3(@RequestParam(name="username", required = false) String name){
        log.info("Post /param/p03" +name);
    }

    //@RequestParam: 동기요청 파라미터 처리/ html form 기반 전달되는 파라미터 처리
    // (Java Script의 form-data 받기가능, JSON Type 받기 불가)
    //@RequestBody: 비동기요청 파라미터 처리/ json, filedata등 전달되는 파라미터 처리(html form 처리가능), 파일데이터 같은거
    @PostMapping(value = "/p04")
    public void ParamHandler4(@RequestBody String name){
        log.info("Post /param/p04" +name);

    }

    @PostMapping("/p05")
    public void ParamHandler5(@RequestParam(name="username", required = false,defaultValue = "홍길동") String name){
        // 파라미터를 전달하지 않는다면 기본적으로 받는 값 == defaultValue
        log.info("Post /param/p05" +name);
    }

    @GetMapping("/p06")
    public void ParamHandler6(
            @RequestParam String name,
            @RequestParam int age,
            @RequestParam String addr
    ){
        // 파라미터를 전달하지 않는다면 기본적으로 받는 값 == defaultValue
        log.info("Get /param/p06" + name + " " + age + " " + addr);
    }

    @GetMapping("/p07")
    public void ParamHandler7(@ModelAttribute com.example.demo.Dto.PersonDto dto){  // ModelAttributer
        log.info("Get /param/p07" +dto);
    }

    @GetMapping("/p08/{name}/{age}/{addr}")
    public void ParamHandler8(
            // 경로기반의 파라미터 전송 방법
            // localhost:8090/param/p08/홍길동/55/대구 이런식으로 파라미터가 뭐인지 모르게 받아올 수 있는 방법
            @PathVariable String name,
            // @PathVariable(value = "name") String username, -> 혹시 안쪽의 코드문에는 username이라 썻다면 value값을 name으로 설정해야한다
            @PathVariable int age,
            @PathVariable String addr
    ){
        log.info("Get /param/p08" + name + " " + age + " " + addr);
    }

    @GetMapping("/p09/{name}/{age}/{addr}")
    public void ParamHandler9(com.example.demo.Dto.PersonDto dto){
        log.info("Get /param/p09" + dto);
    }

    @GetMapping("/page1")
    public void page1(com.example.demo.Dto.PersonDto dto, Model model){    // 기본 패시브@ModelAttribute로 파라미터 받음
        log.info("GET /param/page1..." + dto);

        // 01 파라미터 받기
        // 02 유효성 검증
        // 03 서비스 실행

        model.addAttribute("dto", dto);
        model.addAttribute("isLogin","true");
        // 04 뷰로 이동(자동)
    }

    @GetMapping("/page2/{name}/{age}/{addr}")
    public ModelAndView page2_handler(com.example.demo.Dto.PersonDto dto){
        log.info("GET /param/page2..." + dto);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dto", dto);
        modelAndView.setViewName("param/page2");

        return modelAndView;
    }

    @GetMapping("/page3")
    public void page3_handler(HttpServletRequest request, HttpServletResponse response){
        log.info("GET /param/page3...");
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String addr = request.getParameter("addr");
        log.info("GET / param/page3..." + name + " " + age + " " + addr);

        com.example.demo.Dto.PersonDto dto = new PersonDto(name, age, addr);
        request.setAttribute("dto", dto);           // requset는 기본적으로 포워딩방식


//        HttpSession session = request.getSession();
//        session.setAttribute("dto", dto);     // session은 포워딩방식이 아니기때문에 입력한 값 확인 불가

        Cookie cookie = new Cookie("c1", "v1");
        response.addCookie(cookie);     // 쿠키 전달
    }

    @GetMapping("/page4")
    public void page4_handler(@RequestParam Map<String,Object> params)      // 이 방식이면 어느 키값이라도 받아낼 수 있다
    {
        log.info("GET /param/page4..." + params);       // GET /param/page4...{name=홍길동, height=177.7, weight=100.1}
    }

    // -----------------------------------
    // Forward
    // -----------------------------------

    // Forwarding을 하기 위한 예약어 forward:
    @GetMapping("/forward/init")
    public String foward_init_handler(Model model){
        log.info("GET/ param/forward/init...");
        model.addAttribute("init", "init_value");

        return "forward:/param/forward/step1";      // step1으로 포워딩
    }

    @GetMapping("/forward/step1")
    public String foward_step1_handler(Model model){
        model.addAttribute("step1", "step_value");
        log.info("GET/ param/forward/step1...");

        return "forward:/param/forward/step2";
    }

    @GetMapping("/forward/step2")
    public void foward_step2_handler(){
        log.info("GET/ param/forward/step2...");

    }




    // -----------------------------------
    // Redirect
    // -----------------------------------

    @GetMapping("/redirect/init")
    public String redirect_init_handler(Model model, RedirectAttributes redirectAttributes){
        log.info("GET/ param/Redirect/init...");
        model.addAttribute("init", "init_value");
        redirectAttributes.addAttribute("r_init","r_init_value");   // 쿼리스트링으로 리다이렉트된다
        redirectAttributes.addFlashAttribute("r_init2", "r_init2_value");     // addFlashAttribute: session객체에 담음

        return "redirect:/param/redirect/step1";
    }

    @GetMapping("/redirect/step1")
    public void redirect_step1_handler(Model model, @RequestParam String r_init){       // inti페이지에서
                                                                                        // 쿼리스트링으로 전해진 r_init받기
        log.info("GET/ param/Redirect/step1...r_init" + r_init);
        model.addAttribute("step1", "step1_value");     // init은 redirect방식이라
                                                                                // 새로운 동작이 들어오면 없어진다
    }

    @GetMapping("/redirect/step2")
    public void redirect_step2_handler() {
        log.info("GET/ param/Redirect/step2...");

    }




//    @RequestMapping("")
//    public void ParamHandler_1(){
//        log.info("");
//    }
}
