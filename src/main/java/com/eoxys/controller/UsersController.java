package com.eoxys.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eoxys.dto.LoginRequest;
import com.eoxys.dto.Response;
import com.eoxys.entity.UsersEntity;
import com.eoxys.service.UsersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/add-user")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Response> createUser(@RequestBody UsersEntity userEntity) {
        return new ResponseEntity<>(usersService.createUser(userEntity), HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-users")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Response> gettingUser() {
        return new ResponseEntity<>(usersService.gettingUser(), HttpStatus.ACCEPTED);
    }

//    @PostMapping("/login")
//    @CrossOrigin(origins = "http://localhost:3000")
//    public ResponseEntity<Response> validationuser(@RequestBody LoginRequest loginRequest) {
//        Response response = usersService.validationUser(loginRequest.getEmail(), loginRequest.getPassword());
//        return new ResponseEntity<>(response, response.isStatus() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
//    }
    
    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<Response> validationuser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        Response serviceResponse = usersService.validationUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (serviceResponse.isStatus()) {
            Map<String, Object> data = (Map<String, Object>) serviceResponse.getData();
            String token = (String) data.get("token");

            // ✅ Set token as HttpOnly cookie
            Cookie cookie = new Cookie("jwt_token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false); // set true if using HTTPS
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // 1 day
            response.addCookie(cookie);
        }

        return new ResponseEntity<>(serviceResponse, serviceResponse.isStatus() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }



    @PostMapping("/signup")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Response> signUp(@RequestBody UsersEntity userEntity) {
        return new ResponseEntity<>(usersService.signUp(userEntity), HttpStatus.ACCEPTED);
    }

    // ✅ New /users/me endpoint
    @GetMapping("/me")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String token = null;

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("jwt_token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        UsersEntity user = usersService.getUserFromToken(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userID", user.getUserId());
        userInfo.put("userName", user.getUserName());
        userInfo.put("email", user.getEmail());
        userInfo.put("mobile", user.getMobile());
        userInfo.put("role", user.getRole());

        return ResponseEntity.ok(userInfo);
    }
    
    @PostMapping("/logout")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Expire the cookie
        Cookie cookie = new Cookie("jwt_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete immediately
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

}





//package com.eoxys.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.eoxys.dto.LoginRequest;
//import com.eoxys.dto.Response;
//import com.eoxys.entity.UsersEntity;
//import com.eoxys.service.UsersService;
//
//@RequestMapping("/users")
//@RestController
//public class UsersController {
//
////	private static final String ApiResponse = addusers;
//	@Autowired
//	private UsersService usersService;
//
//	@PostMapping("/add-user")
//	@CrossOrigin(origins = "http://localhost:3000")
//	public ResponseEntity<Response> createUser(@RequestBody UsersEntity userEntity) {
//		System.out.println("controller user data=>" + userEntity);
//
////		return ResponseEntity.ok(userjson);
//		return new ResponseEntity<>(usersService.createUser(userEntity), HttpStatus.ACCEPTED);
//
//	}
//	
//	@CrossOrigin(origins = "http://localhost:3000")
//	@GetMapping("/get-users")
//	public ResponseEntity<Response> gettingUser() {
//        System.out.println("users =>"+usersService.gettingUser());
////		return (ResponseEntity<userjson>) ResponseEntity.ok(usersService.gettingUser());
//		return new ResponseEntity<>(usersService.gettingUser(), HttpStatus.ACCEPTED);
//
//	}
//	
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PostMapping("/login")
//	public ResponseEntity<Response> validationuser(@RequestBody LoginRequest loginRequest) {
//	    Response response = usersService.validationUser(loginRequest.getEmail(), loginRequest.getPassword());
//
//	    return new ResponseEntity<>(response, response.isStatus() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
//	}
//
//
//	@CrossOrigin(origins = "http://localhost:3000")
//	@PostMapping("/signup")
//	public ResponseEntity<Response> signUp(@RequestBody UsersEntity userEntity) {
//		return new ResponseEntity<>(usersService.signUp(userEntity), HttpStatus.ACCEPTED);
//	}
//	
//	
//	@CrossOrigin(origins = "http://localhost:3000")
//	@GetMapping("/users/me")
//	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User userDetails) {
//	    UsersEntity user = usersService.findByEmail(userDetails.getUsername());
//	    if (user == null) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//	    }
//
//	    Map<String, Object> userInfo = new HashMap<>();
//	    userInfo.put("userID", user.getUserId());
//	    userInfo.put("userName", user.getUserName());
//	    userInfo.put("email", user.getEmail());
//	    userInfo.put("mobile", user.getMobile());
//	    userInfo.put("role", user.getRole());
//
//	    return ResponseEntity.ok(userInfo);
//	}
//
//}
