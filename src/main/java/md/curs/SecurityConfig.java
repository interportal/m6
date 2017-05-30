package md.curs;

import md.curs.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MG
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/rest/user/**").hasAuthority("GLOBAL_ADMIN")
                .antMatchers(HttpMethod.PUT,"/rest/user/**").hasAuthority("GLOBAL_ADMIN")
                .antMatchers(HttpMethod.DELETE,"/rest/user/**").hasAuthority("GLOBAL_ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                md.curs.model.User user = userRepo.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException("User not found");
                }
                List<SimpleGrantedAuthority> authorities = user.getAuthorities().stream()
                        .map(a -> new SimpleGrantedAuthority(a.getName()))
                        .collect(Collectors.toList());
                return new User(username, user.getPassword(), authorities);
            }
        };
    }

// Uncomment this encoder to activate the BCrypt password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
