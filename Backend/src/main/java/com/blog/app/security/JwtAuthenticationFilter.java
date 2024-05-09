package com.blog.app.security;

import java.io.IOException; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		 try {
//           Thread.sleep(500);
//       } catch (InterruptedException e) {
//           throw new RuntimeException(e);
//       }
			 
		// get token
		String requestHeader = request.getHeader("Authorization");
		//Bearer 2352345235sdfrsfgsdfsdf
		logger.info("Header : {}", requestHeader);
		
		String username = null;
		String token = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            //looking good
            token = requestHeader.substring(7);
            try {

                username = this.jwtTokenHelper.getUsernameFromJwtToken(token);
            }
                catch (ExpiredJwtException e) {
                    logger.error("Token expired: {}", e.getMessage());
                } catch (MalformedJwtException e) {
                    logger.error("Malformed token: {}", e.getMessage());
                } catch (SignatureException e) {
                    logger.error("Invalid JWT signature: {}", e.getMessage());
                } catch (UnsupportedJwtException e) {
                    logger.error("Unsupported JWT: {}", e.getMessage());
                } catch (IllegalArgumentException e) {
                    logger.error("JWT token compact of handler are invalid: {}", e.getMessage());
                } catch (Exception e) {
                    logger.error("Error during token validation: {}", e.getMessage());
                }



        } else {
            logger.info("Invalid Header Value !! ");
        }


        // validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            //fetch user detail from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtTokenHelper.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            } else {
                logger.info("Validation fails !!");
            }


        }

        filterChain.doFilter(request, response);


    }
}