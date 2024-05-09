package com.blog.app.blog_configuartion;

import java.util.Arrays;

import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.blog.app.security.JwtAunthenticationEntrypoint;
import com.blog.app.security.JwtAuthenticationFilter;


@Configuration
@EnableMethodSecurity()
@EnableWebMvc
public class SecurityConfig{
	
//	public static final String[] PUBLIC_URLS= {"/api/**", "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"};
	
	@Autowired
    private JwtAunthenticationEntrypoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
             .authorizeHttpRequests(auth-> auth.requestMatchers(HttpMethod.GET).permitAll()// to allow all the get method 
            		.requestMatchers("/api/**").authenticated()
                	.anyRequest()
                	.permitAll())
               .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
          http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
   FilterRegistrationBean<CorsFilter> coresFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Cors configuration setup
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Accept", "Content-Type", "common"));
        corsConfiguration.setExposedHeaders(Arrays.asList("common"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        CorsFilter corsFilter = new CorsFilter(source);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
        bean.setOrder(-110);

        return bean;
    }

}
