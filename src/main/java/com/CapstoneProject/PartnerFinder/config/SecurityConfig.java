package com.CapstoneProject.PartnerFinder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.CapstoneProject.PartnerFinder.filter.JwtAuthenticationFilter;
import com.CapstoneProject.PartnerFinder.security.CustomUserDetailsService;
import com.CapstoneProject.PartnerFinder.security.JwtAuthenticationEntryPoint;


@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

	    private JwtAuthenticationEntryPoint authenticationEntryPoint;

	    private JwtAuthenticationFilter jwtFilter;
	    
	    private CustomAuthenticationSuccessHandler successHandler;
	    
	    private CustomUserDetailsService userDetailsService;

	    @Autowired
	    public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
	                          JwtAuthenticationFilter jwtFilter,
	                          CustomAuthenticationSuccessHandler successHandler,
	                          CustomUserDetailsService userDetailsService
	    ) {
	        this.authenticationEntryPoint = authenticationEntryPoint;
	        this.jwtFilter = jwtFilter;
	        this.successHandler = successHandler;
	        this.userDetailsService = userDetailsService;
	    }

	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


	        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // don't create JSESSIONID
	                .authorizeHttpRequests((request) -> request


	                        // Security For Auth Controller
	                        .requestMatchers("/api/auth/login").permitAll()
	                        
	                        //Security For Dashboard Controller
	                        .requestMatchers("/dashboard/user").hasRole("COLLABORATOR")
	                        .requestMatchers("/dashboard/poster").hasRole("POSTER")
	                        .requestMatchers("/dashboard/admin").hasRole("ADMIN")

	                        // Security For Project Category Controller
	                        .requestMatchers(HttpMethod.POST, "/api/projectCategories").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.GET, "/api/projectCategories").hasAnyRole("ADMIN", "POSTER", "COLLABORATOR")
	                        .requestMatchers(HttpMethod.PUT, "/api/projectCategories/**").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.DELETE, "/api/projectCategories/**").hasRole("ADMIN")

	                        // Security For Project Poster Controller

	                        // Poster itself
	                        .requestMatchers(HttpMethod.POST, "/api/posters").permitAll()
	                        .requestMatchers(HttpMethod.GET, "/api/posters").hasAnyRole("POSTER", "COLLABORATOR")
	                        .requestMatchers(HttpMethod.POST, "/api/posters/{posterId}/profileDetails").hasRole("POSTER")


	                        // Poster & Project
	                        .requestMatchers(HttpMethod.POST, "/api/posters/{posterId}/projects").hasRole("POSTER")
	                        .requestMatchers(HttpMethod.PUT, "/api/posters/{posterId}/myApplications/{applicationId}").hasRole("POSTER")
	                        .requestMatchers(HttpMethod.GET, "/api/posters/{posterId}/projects").hasAnyRole("POSTER", "ADMIN")
	                        .requestMatchers(HttpMethod.GET, "/api/posters/{posterId}/myApplications").hasAnyRole("POSTER", "ADMIN")
	                        .requestMatchers(HttpMethod.DELETE, "/api/posters/{posterId}/projects/{projectId}").hasAnyRole("POSTER")
	                        

	                        //Project Application approval/reject related
	                        .requestMatchers(HttpMethod.GET, "/api/posters/members/{posterId}").hasAnyRole("POSTER", "ADMIN")
	                        .requestMatchers(HttpMethod.POST, "/api/posters/approve-member/{posterId}").hasRole("POSTER")
	                        .requestMatchers(HttpMethod.POST, "/api/posters/projects/{projectId}/applications/{applicationId}/reject").hasRole("POSTER")
	                        
	                        // Security For Project Collaborator Controller

	                        // ------------------------- Collaborator Profile Methods-------------

	                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
	                        .requestMatchers(HttpMethod.POST, "/api/users/{userId}/qualifications").hasRole("COLLABORATOR")
	                        .requestMatchers(HttpMethod.POST, "/api/users/{userId}/profileDetails").hasRole("COLLABORATOR")
	                        .requestMatchers(HttpMethod.POST, "/api/users/{userId}/skills").hasRole("COLLABORATOR")
	                        .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("POSTER", "COLLABORATOR")
	                        .requestMatchers(HttpMethod.GET, "/api/users/{userId}").hasRole("COLLABORATOR")


	                        // -------------------Project Method Related to Collaborator Methods---------

	                        .requestMatchers(HttpMethod.POST, "/api/users/{userId}/projects/{projectId}/apply").hasRole("COLLABORATOR")
	                        .requestMatchers(HttpMethod.POST, "/api/users/{userId}/projects/{projectId}/yourApplications/{applicationId}/cancel").hasRole("COLLABORATOR")
	                        .requestMatchers(HttpMethod.GET, "/api/users/{userId}/projects/yourApplications").hasRole("COLLABORATOR")


	                        // Security For Project Controller

	                        .requestMatchers("/api/projects/**").permitAll()

	                        // Security For Project ApplicationController

	                        .requestMatchers(HttpMethod.GET , "/api/projects/applications").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.GET , "/api/projects/applications/{projectId}").hasRole("POSTER")



	                        //Security For Admin controller
	                        .requestMatchers(HttpMethod.POST, "/api/admin/ban/{id}").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.POST, "/api/admin/unban/{id}").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.GET, "/api/admin/poster-all").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.GET, "/api/admin/user-all").hasRole("ADMIN")
	                        .requestMatchers(HttpMethod.DELETE, "/api/admin/user-delete/{id}").hasRole("ADMIN")


	                        //Security for Comment Controller
	                        .requestMatchers(HttpMethod.POST, "/api/comments/project/{projectId}/actor/{actorId}").hasAnyRole("POSTER", "COLLABORATOR")
	                        .requestMatchers(HttpMethod.PUT, "/api/comments/hide/{commentId}/poster/{posterId}").hasRole("POSTER")
	                        .requestMatchers(HttpMethod.DELETE, "/api/comments/{commentId}").hasAnyRole("Admin", "POSTER", "COLLABORATOR")
	                        .requestMatchers(HttpMethod.GET, "/api/comments/project/{projectId}").hasAnyRole("Admin", "POSTER", "COLLABORATOR")
	                        .requestMatchers(HttpMethod.GET, "/api/comments/project/{projectId}/all").hasAnyRole("Admin", "POSTER")
	                
	                        // Security For API Documentation
	                        .requestMatchers(
	                                "/v3/api-docs",
	                                "/v3/api-docs/**",
	                                "/swagger-ui/**",
	                                "/swagger-ui.html"


	                        ).permitAll()
	                        .anyRequest().authenticated()
	                 )     
	                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));

	        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	        http.formLogin(form -> form.successHandler(successHandler));
	        http.httpBasic(Customizer.withDefaults());

	        http.csrf(csrf -> csrf.disable());

	        return http.build();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    
	    @Bean
	    public DaoAuthenticationProvider daoAuthenticationProvider() {
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
	        provider.setPasswordEncoder(passwordEncoder());
	        return provider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

}

