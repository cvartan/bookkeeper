package pro.cvartan.test.bookkeeper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // Настройка LDAP контекста
    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldaps://samba.int.homenet:636");
        // contextSource.setBase("dc=int,dc=homenet");
        contextSource.setUserDn("CN=bkapp,CN=Users,DC=int,DC=homenet");
        contextSource.setPassword("BkApp_2025");
        contextSource.setPooled(true);
        return contextSource;
    }

    // Настройка LdapTemplate
    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }

    // Настройка аутентификации через LDAP
    @Bean
    public AuthenticationProvider ldapAuthenticationProvider() {
        BindAuthenticator authenticator = new BindAuthenticator(ldapContextSource());
        
        authenticator.setUserDnPatterns(new String[] { "CN={0},CN=Users,DC=int,DC=homenet" });
        
        /*
        authenticator.setUserSearch(new FilterBasedLdapUserSearch(
            "CN=Users,DC=int,DC=homenet",
            "(sAMAccountName={0})",
            ldapContextSource()
        ));
        */

        return new LdapAuthenticationProvider(authenticator);
    }

    // Конфигурация безопасности Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/bkadmins/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/bkusers/**").hasRole("USER")
                        .anyRequest().authenticated())
                .httpBasic(withDefaults()) // Включаем Basic Authentication
                .formLogin(withDefaults()) // Стандартная форма логина
                .logout(withDefaults()); // Стандартная форма выхода

        return http.build();
    }

    // Настройка AuthenticationManager для интеграции с Spring Security
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(ldapAuthenticationProvider())
                .build();
    }
}
