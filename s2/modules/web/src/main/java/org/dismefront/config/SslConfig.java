package org.dismefront.config;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SslConfig {

    @Value("${app.ssl.trust-store}")
    private Resource trustStore;

    @Value("${app.ssl.trust-store-password}")
    private String trustStorePassword;

    @PostConstruct
    public void init() {
        if (System.getProperty("javax.net.ssl.trustStore") != null) return;

        try (InputStream is = trustStore.getInputStream()) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, trustStorePassword.toCharArray());

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            SSLContext.setDefault(sslContext);
            System.out.println("✅ Custom truststore loaded from classpath (JAR-safe)");
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load truststore: " + trustStore, e);
        }
    }
}