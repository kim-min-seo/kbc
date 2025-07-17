package com.minse0.kbc.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minse0.kbc.response.ApiResponse;
import com.minse0.kbc.response.ResponseCode;
import com.minse0.kbc.user.domain.User;
import com.minse0.kbc.user.service.UserService;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    
    @PostMapping("/join")
    public Map<String, String> join(
            @RequestParam String loginId,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String nickname,
            @RequestParam String preferTeam) {

        Map<String, String> resultMap = new HashMap<>();

        User newUser = userService.addUser(loginId, password, name, nickname, preferTeam);
        if (newUser != null) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    
    @GetMapping("/is-duplicated-id")
    public ApiResponse<Void> isDuplicatedId(@RequestParam String loginId) {
       
        if (userService.isDuplicatedId(loginId)) {
            return ApiResponse.fail(ResponseCode.DUPLICATE_ID);
        } else {
        	return ApiResponse.success(null);
        }
        
    }

}
