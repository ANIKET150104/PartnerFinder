package com.CapstoneProject.PartnerFinder.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.CapstoneProject.PartnerFinder.model.Poster;
import com.CapstoneProject.PartnerFinder.model.PosterRole;
import com.CapstoneProject.PartnerFinder.model.User;
import com.CapstoneProject.PartnerFinder.model.UserRole;
import com.CapstoneProject.PartnerFinder.repo.PosterRepository;
import com.CapstoneProject.PartnerFinder.repo.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	private PosterRepository posterRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository, PosterRepository posterRepository) {
		this.userRepository = userRepository;
		this.posterRepository = posterRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(email);

		if (userOptional.isPresent()) {

			User user = userOptional.get();

			List<GrantedAuthority> authorities = new ArrayList<>();

			for (UserRole role : user.getRoles()) {

				authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));

			}

			System.out.println("Authorities: " + authorities);

			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					authorities);
		}

		Optional<Poster> posterOptional = posterRepository.findByEmail(email);

		if (posterOptional.isPresent()) {

			Poster poster = posterOptional.get();

			List<GrantedAuthority> authorities = new ArrayList<>();

			for (PosterRole role : poster.getRoles()) {

				authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));

			}

			return new org.springframework.security.core.userdetails.User(poster.getEmail(), poster.getPassword(),
					authorities);
		}

		throw new UsernameNotFoundException("Account Not Found");
	}

}
