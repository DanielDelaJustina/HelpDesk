package com.daniel.helpdesk.api.security.jwt;

import com.daniel.helpdesk.api.entity.User;
import com.daniel.helpdesk.api.enums.ProfileEnum;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserFactory {
	
	
	private JwtUserFactory() {
		
	}
	
	
	public static JwtUser create(User user) {
		
		return new JwtUser(user.getId(),user.getEmail(),user.getPassword(),
						   mapToGrantedAuthorities(user.getProfile()));
	}
	
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		return authorities;
		
	}

}
