package pro.cvartan.test.bookkeeper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@ConfigurationProperties
@RequiredArgsConstructor
@Setter
@Getter
public class LdapProperties {
    //@Value("${spring.security.ldap.user-dn-patterns}")
    //private final String userDNPatterns;

    @Value("${spring.security.ldap.user-search-filter}")
    private final String userSearchFilter;

    @Value("${spring.security.ldap.user-search-base}")
    private final String userSearchBase;

    @Value("${spring.security.ldap.group-search-base}")
    private final String groupSearchBase;

    @Value ("${spring.security.ldap.authorities.group-search-filter}")
    private final String groupSearchFilter;
}
