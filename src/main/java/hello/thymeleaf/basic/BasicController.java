package hello.thymeleaf.basic;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")

public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model){
        model.addAttribute("data", "Hello Spring!");
        return "basic/text-basic";
    };
    @GetMapping("text-unescaped")
    public String textUnescaped(Model model){
        model.addAttribute("data", "Hello <b> Spring! </b>");
        return "basic/text-Unescaped";
    };
    @GetMapping("/variable")
    public String variable (Model model){
        User userA = new User("userA", 10);
        User userB = new User("userB", 40);
        List<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);
        Map<String,User> map = new HashMap<>();
        map.put("userA",userA);
        map.put("userB",userB);

        model.addAttribute("user",userA);
        model.addAttribute("users",list);
        model.addAttribute("userMap",map);
        return "/basic/variable";
    }
    //Httprequest는 유저가 들어왔다 나가면 꺼지지만 HttpSesion은 웹브라우저가 꺼지기 전까지 유지됨
   /* @GetMapping("/basic-objects") //스프링 3.0 미만 버전
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        return "basic/basic-objects";
    }*/
    @GetMapping("/basic-objects")  // 스프링 3.0 이상은 model 에 담아서 .
    public String basicObjects(Model model, HttpServletRequest request,
                               HttpServletResponse response, HttpSession session) {
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "basic/basic-objects";
    }
    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "basic/date";
    }
    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello " + data;
        }
    }
    @GetMapping("/link")
    public String link(Model model){
        model.addAttribute("param1","data1");
        model.addAttribute("param2","data2");

        return "basic/link";
    }
    @GetMapping("/literal")
    public String literal(Model model){
        model.addAttribute("data","Spring!");

        return "basic/literal";
    }
    @GetMapping("/operation")
    public String operation(Model model){
        model.addAttribute("nullData",null);
        model.addAttribute("data","Spring!");
        return "basic/operation";
    }

    @GetMapping("/attribute")
    public String attribute(){

        return "basic/attribute";
    }
    @GetMapping("/each")
    public String each(Model model) {
        addUsers(model);
        return "basic/each";
    }
    @GetMapping("/condition")
    public String condition(Model model){
        addUsers(model);
        return "basic/condition";
    }
    @GetMapping("/comments")
    public String comments(Model model){
        model.addAttribute("data","Spring!");
        return "basic/comments";
    }
    @GetMapping("/block")
    public String block(Model model){
        addUsers(model);
        return "basic/block";
    }
    private void addUsers(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));
        model.addAttribute("users", list);
    }

    @Data
    static class User {
        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
        private String username;
        private int age;
    }


}
