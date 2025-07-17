package com.minse0.kbc.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.minse0.kbc.user.domain.User;
import com.minse0.kbc.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {
	
	private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
	
	@GetMapping("/join-view")
	public String joinInput() {
		return "user/join";
	}
	
	@GetMapping("/login-view")
	public String loginInput() {
		return "user/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	
		session.removeAttribute("userId");
		session.removeAttribute("userNickname");
		
		return "redirect:/user/login-view";
	}
	
	@PostMapping("/login")
	public String login(
	        @RequestParam String loginId,
	        @RequestParam String password,
	        HttpSession session,
	        Model model) {

	    User user = userService.getUser(loginId, password);

	    if (user != null) {
	        session.setAttribute("userId", user.getUserId());
	        session.setAttribute("userNickname", user.getNickname());
	        
	        return "post/main"; 
	    } else {
	        model.addAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
	        return "user/login"; 
	    }
	}

}
