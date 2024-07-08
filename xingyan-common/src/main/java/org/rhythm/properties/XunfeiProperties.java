package org.rhythm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rhythm.xunfei")
@Data
public class XunfeiProperties {
    private String hostUrl;
    private String appid;
    private String apiSecret;
    private String apiKey;
}
