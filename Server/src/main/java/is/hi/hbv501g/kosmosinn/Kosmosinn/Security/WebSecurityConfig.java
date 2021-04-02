package is.hi.hbv501g.kosmosinn.Kosmosinn.Security;

import org.springframework.beans.factory.annotation.Autowired;
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

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author Siva
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter 
{
       
    @Autowired
    private UserDetailsService customUserDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
        throws Exception 
    {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
            ;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.csrf().disable()
	        .authorizeRequests()
		        .antMatchers("/","/login").permitAll()
		        .antMatchers("**/addBoard").hasRole("ADMIN")
		        .antMatchers("/api/**").permitAll();
        
    }
}