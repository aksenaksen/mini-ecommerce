package tobyspring.gatewayservice.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Value("${token.expired}")
    private Long tokenExpired;

    @Value("${token.secret}")
    private String tokenSecret;


    public AuthorizationHeaderFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no Authrization Header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authHeader.replace("Bearer ","");
            
            if(!isJwtValid(jwt)){
                return onError(exchange,"JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            
            return chain.filter(exchange);
        });
    }

    private boolean isJwtValid(String jwt) {
        boolean result = true;

        String subject = null;

        try
        {
            subject = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(jwt).getBody().getSubject();
        }catch (Exception e){
            result = false;
        }
        log.info(subject);
        if(subject == null || subject.isEmpty()){
            result = false;
        }

        return result;
    }
//  Mono 단일 Flux 복수
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse res = exchange.getResponse();
        res.setStatusCode(status);

        log.error(err);
        return res.setComplete();
    }

    public static class Config{

    }

}
