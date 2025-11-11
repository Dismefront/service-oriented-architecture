package org.dismefront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

@Component
public class SslConfig implements ApplicationRunner {

    @Value("${app.ssl.trust-store}")
    private Resource trustStore;

    @Value("${app.ssl.trust-store-password}")
    private String trustStorePassword;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (System.getProperty("javax.net.ssl.trustStore") != null) {
            return;
        }

        try (InputStream is = trustStore.getInputStream()) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, trustStorePassword.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());

            // Set default SSL context for all HTTPS connections
            SSLContext.setDefault(sslContext);

            System.out.println("✅ Custom truststore loaded from classpath (JAR-safe)");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load truststore: " + trustStore, e);
        }
    }
}
