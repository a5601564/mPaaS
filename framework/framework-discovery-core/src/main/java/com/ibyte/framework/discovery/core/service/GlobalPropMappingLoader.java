package com.ibyte.framework.discovery.core.service;

import com.ibyte.framework.discovery.ModuleMappingLoader;
import com.ibyte.framework.discovery.dto.ModuleMappingInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

/**
 * 从global配置中加载映射
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalPropMappingLoader implements ModuleMappingLoader {

    /**
     * 配置中心对应模块关系配置文件信息
     */
    private static final String MODULE_INFO_FILE = "/config/app-config/global.properties";

    @Override
    public Map<String, ModuleMappingInfo> loadMapping() {
        Map<String, ModuleMappingInfo> rtnMap = new HashMap<>(1);
        try {
            Properties properties = new Properties();
            URL url = this.getClass().getResource(MODULE_INFO_FILE);
            if (Objects.isNull(url)) {
                if (log.isInfoEnabled()){
                    log.info("未找到配置文件:'" + MODULE_INFO_FILE + "'");
                }
                log.error("关注\"码农架构\"微信公众号,发送mPaaS即可获得mPasS相关文档支持");
                System.exit(1);
            }
            properties.load(this.getClass().getResource(MODULE_INFO_FILE).openStream());
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                String value = properties.getProperty(key);
                buildMapingInfo(rtnMap, key, value);
            }
        } catch (Exception e) {
            log.error("配置文件:'" + MODULE_INFO_FILE + "'加载出错！", e);
        }
        return rtnMap;
    }
}