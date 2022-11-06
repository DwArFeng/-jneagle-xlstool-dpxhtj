package com.jneagle.xlstool.dpxhtj.configuration;

import com.dwarfeng.subgrade.sdk.bean.key.UuidKeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.UuidKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public KeyFetcher<UuidKey> uuidKeyFetcher() {
        return new UuidKeyFetcher();
    }
}
