package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SampleController {
    @GetMapping("/login")
    public String form() {
        return "login.html";
    }
    
    @GetMapping("/error1")//errorが発生した際はエラーメッセージを表示したいので違う処理に入るように制御する
    public String getLoginError(Model model) {
    	model.addAttribute("ErrorMessage","*ユーザー名もしくはパスワードが一致しません");
        return "login";
    }

    //デフォルトではPostでリクエストが発生。カスタマイズ時はユーザーが指定したmethodに従う。
    @PostMapping("/login")
    public String success() {
        return "sample";
    }
    
    @GetMapping("/sample")
    public String sample() {
      return "sample";
    }
}
