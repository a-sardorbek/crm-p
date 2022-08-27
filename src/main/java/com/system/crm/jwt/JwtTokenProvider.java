package com.system.crm.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.system.crm.constant.SecurityConstant;
import com.system.crm.dto.authentication.SystemUserPrincipal;
import com.system.crm.exception.custom.InvalidTokenRequestException;
import com.system.crm.service.SystemUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;


@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        //  avoid the secret key available in the JVM
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String generateJwtToken(SystemUserPrincipal systemUserPrincipal){
        String[] claims = getClaimsFromSystemUser(systemUserPrincipal);
        return JWT.create()
                .withIssuer(SecurityConstant.TOKEN_ISSUER)
                .withSubject(systemUserPrincipal.getUsername())
                .withIssuedAt(new Date())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(secret.getBytes()));


    }

    public Date getTokenExpiryFromJWT(String token) {
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getExpiresAt();
    }


    public String getSubjectFromToken(String token){
        JWTVerifier verifier = getJwtVerifier();
        return verifier.verify(token).getSubject();
    }

    public boolean isValidToken(String username, String token) {
        JWTVerifier verifier = getJwtVerifier();
        return StringUtils.isNotBlank(username) && !isTokenExpired(verifier,token);
    }


    private boolean isTokenExpired(JWTVerifier verifier,String token) {
        Date expirationDate = verifier.verify(token).getExpiresAt();
        return expirationDate.before(new Date());
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken systemUserPasswordAuthToken =
                new UsernamePasswordAuthenticationToken(username,null,authorities);
        systemUserPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        return systemUserPasswordAuthToken;
    }

    private JWTVerifier getJwtVerifier() {
        JWTVerifier verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.TOKEN_ISSUER).build();
        }catch (JWTVerificationException verificationException){
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }


    private String[] getClaimsFromSystemUser(SystemUserPrincipal systemUserPrincipal){
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : systemUserPrincipal.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier =getJwtVerifier();
        return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
    }


}
