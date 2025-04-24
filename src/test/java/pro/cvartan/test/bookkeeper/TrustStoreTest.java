package pro.cvartan.test.bookkeeper;

import javax.net.ssl.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class TrustStoreTest {
    public static void main(String[] args) {
        try {
            KeyStore trustStore = KeyStore.getInstance("PKCS12");
            InputStream trustStream = new FileInputStream("/home/cvartan/Projects/learnjava/bookkeeper/truststore.jks");
            trustStore.load(trustStream, "changeit".toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            TrustManager[] trustManagers = tmf.getTrustManagers();
            for (TrustManager tm : trustManagers) {
                if (tm instanceof X509TrustManager) {
                    X509TrustManager x509Tm = (X509TrustManager) tm;
                    System.out.println("Trust manager: " + x509Tm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}