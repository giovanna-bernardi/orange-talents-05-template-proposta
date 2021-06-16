package br.com.zupacademy.giovanna.proposta.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().httpStrictTransportSecurity().disable();
        http.authorizeRequests(authorizeRequests ->
                authorizeRequests
                        .antMatchers("/propostas/**").hasAuthority("SCOPE_propostas")
                        .antMatchers("/cartoes/**").hasAuthority("SCOPE_propostas")
                        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll())
//                        .anyRequest().authenticated())
//                        .anyRequest().permitAll())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }
}
