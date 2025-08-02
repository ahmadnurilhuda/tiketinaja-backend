package com.greenacademy.tiketinaja.config;


import com.midtrans.service.MidtransSnapApi; // <-- Import baru
import com.midtrans.service.impl.MidtransSnapApiImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.midtrans.Config;
import com.midtrans.ConfigBuilder;

@Configuration
public class MidtransConfig {

    @Value("${midtrans.server.key}")
    private String serverKey;

    @Value("${midtrans.client.key}")
    private String clientKey;

    @Bean
    public Config midtransConfiguration() {
        return new ConfigBuilder()
                .setServerKey(serverKey)
                .setClientKey(clientKey)
                .setIsProduction(false)
                .build();
    }

    @Bean
    public MidtransSnapApi midtransSnapApi(Config config) {
        return new MidtransSnapApiImpl(config);
    }
}