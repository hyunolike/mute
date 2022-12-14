package com.hyun.musicmark.global.config;

import com.hyun.musicmark.auth.domain.Oauth2User;
import com.hyun.musicmark.user.application.UserService;
import com.hyun.musicmark.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hyun.musicmark.auth.domain.Oauth2User.OAuth2Provider.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private Oauth2UserService oAuth2UserService;

    @Autowired
    private OidcUserService oidcUserService;

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    DaoAuthenticationProvider daoAuthenticationProvider;

    /**
     * <h3>CSP</h3>
     * default-src 'self';
     * script-src 'report-sample' 'self';
     * style-src 'report-sample' 'self';
     * object-src 'none';
     * base-uri 'self';
     * connect-src 'self';
     * font-src 'self';
     * frame-src 'self';
     * img-src 'self';
     * manifest-src 'self';
     * media-src 'self';
     * report-uri https://63395d02ef389e2c712260ae.endpoint.csper.io/?v=1;
     * worker-src 'none';
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
                .xssProtection();
        http
                .authorizeRequests()
                .mvcMatchers("login", "robots.txt").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().loginPage("/login")
                .and()
                .oauth2Login(
                        oauth2->
                                oauth2.userInfoEndpoint(userInfo->
                                                userInfo.userService(oAuth2UserService)
                                                        .oidcUserService(oidcUserService)
                                        )
                                        .successHandler(new AuthenticationSuccessHandler() {
                                            @Override
                                            public void onAuthenticationSuccess(
                                                    HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Authentication authentication
                                            ) throws IOException, ServletException {

                                                Object principal = authentication.getPrincipal();


                                                if(principal instanceof OAuth2User){
                                                    if(principal instanceof OidcUser){
                                                        // google
                                                        Oauth2User googleUser = google.convert((OAuth2User) principal);
                                                        User user = userService.loadUser(googleUser);
                                                        SecurityContextHolder.getContext().setAuthentication(
                                                                new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities())
                                                        );
                                                    }else if(((OAuth2User) principal).getAttributes().containsKey("kakao_account")){
                                                        // naver, or kakao, facebook
                                                        Oauth2User kakaoUser = kakao.convert((OAuth2User) principal);
                                                        User user = userService.loadUser(kakaoUser);
                                                        SecurityContextHolder.getContext().setAuthentication(
                                                                new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities())
                                                        );
                                                    }else if(((OAuth2User) principal).getAttributes().containsKey("response")){
                                                        Oauth2User naverUser = naver.convert((OAuth2User) principal);
                                                        User user = userService.loadUser(naverUser);
                                                        SecurityContextHolder.getContext().setAuthentication(
                                                                new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities())
                                                        );
                                                    }else {
                                                        Oauth2User facebookUser = facebook.convert((OAuth2User) principal);
                                                        User user = userService.loadUser(facebookUser);
                                                        SecurityContextHolder.getContext().setAuthentication(
                                                                new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities())
                                                        );
                                                    }
                                                }
                                                response.sendRedirect("/home");
                                            }
                                        })
                )
                .logout();
        ;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
}
