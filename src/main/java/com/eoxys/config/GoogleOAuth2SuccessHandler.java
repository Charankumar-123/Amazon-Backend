package com.eoxys.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.eoxys.entity.UsersEntity;
import com.eoxys.repository.UsersRepository;
import com.eoxys.service.JWTService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
	
	private final UsersRepository usersRepository;
	private final JWTService jwtService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");
		
		//save user if not exists
		
		UsersEntity user = usersRepository.findByEmail(email)
                .orElseGet(() -> {
                    UsersEntity newUser = new UsersEntity();
                    newUser.setEmail(email);
                    newUser.setUserName(name);
                    newUser.setRole("USER");
                    return usersRepository.save(newUser);
                });
		
		String token = jwtService.generateToken(user.getEmail());
		
		Cookie cookie = new  Cookie("jwt_token", token);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setMaxAge(24 * 60 * 60);
		
		response.addCookie(cookie);
		response.sendRedirect("http://localhost:3000/login-success");

		
	}

}
