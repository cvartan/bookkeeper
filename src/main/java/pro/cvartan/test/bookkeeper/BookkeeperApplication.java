package pro.cvartan.test.bookkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import pro.cvartan.test.bookkeeper.config.LdapProperties;


@SpringBootApplication
//@ConfigurationPropertiesScan
@EnableConfigurationProperties(LdapProperties.class)
public class BookkeeperApplication {


	public static void main(String[] args) throws Exception {

		System.setProperty("javax.net.ssl.trustStore", "/home/cvartan/Projects/learnjava/bookkeeper/truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword","changeit");

		System.setProperty("com.sun.jndi.ldap.trace.ber", "true");
		System.setProperty("com.sun.jndi.ldap.connect.pool.debug", "all");



		SpringApplication.run(BookkeeperApplication.class, args);
	}

}
