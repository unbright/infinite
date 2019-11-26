package com.wzpeng.fw.config;

import com.google.inject.Provider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IDEA
 * ProjectName: infinite
 * Date: 2019/10/9
 * Time: 11:16
 *
 * @author wzpeng
 * @version v1.0
 */
@Data
public class SystemConfigs {

    private int port;

    @Slf4j
    static class ConfigProvider implements Provider<SystemConfigs> {

        @Override
        public SystemConfigs get() {
            Properties properties = new Properties();
            SystemConfigs configs = new SystemConfigs();
            try {
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
                properties.load(in);
                configs.setPort(NumberUtils.toInt(properties.getProperty("server.port"), 8080));
            } catch (Exception ex) {
                log.error("Configuration file load error ====> ", ex);
            }
            return configs;
        }
    }
}
