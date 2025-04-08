package pro.cvartan.test.bookkeeper.config;

import pro.cvartan.test.bookkeeper.service.AuthProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    // private final PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final AuthProvider authProvider;

/*
    @Bean
    public InMemoryUserDetailsManager userDetailsServices() {

        UserDetails admin = User.withUsername("admin")
        .password(pe.encode("admin"))
        .roles("ADMIN", "USER")
        .build();

        UserDetails user = User.withUsername("user")
        .password(pe.encode("user"))
        .roles("USER")
        .build();
    

        return new InMemoryUserDetailsManager(admin, user);
    }
*/        

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request
                .anyRequest().authenticated()
            )
            .authenticationProvider(authProvider)
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .logout(Customizer.withDefaults());

        return http.build();
    }

}
