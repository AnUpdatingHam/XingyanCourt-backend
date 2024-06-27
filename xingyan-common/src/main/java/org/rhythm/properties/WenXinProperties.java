package org.rhythm.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rhythm.wenxin")
@Data
public class WenXinProperties {
    /**
     * 文心一言相关配置
     */
    private String apiKey;
    private String secretKey;
}
