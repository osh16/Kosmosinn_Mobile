package is.hi.hbv501g.kosmosinn.Kosmosinn.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.http.HttpMethod;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    @Autowired
    private UserDetailsService customUserDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(tokenSecret), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/login/**", "/api/signup/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/boards/addBoard/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/boards/{boardId:[\\d+]}/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/boards/{boardId:[\\d+]}/addTopic/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/boards/{boardId:[\\d+]}/delete/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/boards/{boardId:[\\d+]}/delete/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/topics/{topicId:[\\d+]}/**").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/api/topics/{topicId:[\\d+]}/addComment/**").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/topics/{topicId:[\\d+]}/delete/**").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/api/users/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/users/{id:[\\d+]}/topics/**").permitAll()
                ;
        
    }
}