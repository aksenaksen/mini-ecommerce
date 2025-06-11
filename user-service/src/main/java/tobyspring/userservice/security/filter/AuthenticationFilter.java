package tobyspring.userservice.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import tobyspring.userservice.application.UserFinder;
import tobyspring.userservice.dto.RequestLogin;
import tobyspring.userservice.dto.UserDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserFinder userFinder;

    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserFinder userFinder, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userFinder = userFinder;
        this.env = env;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.email(),
                            creds.password()
                            ,new ArrayList<>()
                    )
            );
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.debug(authResult.getPrincipal().toString());

        String email = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDetails = userFinder.findByEmail(email);

        String token = Jwts.builder()
                .setSubject(userDetails.userId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expired"))))
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.userId());

        log.info("successful authentication");
    }
}
