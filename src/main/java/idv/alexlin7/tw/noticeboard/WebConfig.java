package idv.alexlin7.tw.noticeboard;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@PropertySource(value = {"classpath:springdoc.properties", "classpath:datasource.properties", "classpath:storage.properties"})
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.storage-dir}")
    private String basePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Default handler for webjars
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        String location = Path.of(basePath).toAbsolutePath().toUri().toString();
        registry
                .addResourceHandler("/files/**")
                .addResourceLocations(location);
    }
}
