package qltb;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
 
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
 
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/login", "/doLogout","/header.html","/loginerror").permitAll()
        	.antMatchers("/home").hasAnyRole("USER","ADMIN")
        	.antMatchers("/admin/**").hasRole("ADMIN")
        	.antMatchers("/").hasRole("USER")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            	.permitAll()
            	.loginPage("/login")
            	.usernameParameter("username")
            	.passwordParameter("password")
            	.defaultSuccessUrl("/home")
            	.failureUrl("/loginerror")
            .and()
            .logout()
            	.permitAll()
            	.logoutUrl("/doLogout")
            	.logoutSuccessUrl("/login")
        	.and().csrf().disable()
        	.exceptionHandling().accessDeniedPage("/403");
        	
        	http.authorizeRequests().and() //
            .rememberMe().tokenRepository(this.persistentTokenRepository()) //
            .tokenValiditySeconds(1 * 24 * 60 * 60);
        	
        return http.build();
    }

	private PersistentTokenRepository persistentTokenRepository() {
		InMemoryTokenRepositoryImpl memory = new InMemoryTokenRepositoryImpl(); 
        return memory;
	}
}
