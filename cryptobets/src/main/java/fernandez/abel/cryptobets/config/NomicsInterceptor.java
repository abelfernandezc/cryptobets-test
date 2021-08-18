package fernandez.abel.cryptobets.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class NomicsInterceptor implements RequestInterceptor {

    @Value("${currencyService.key}")
    private String key;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.query("key", key);
    }
}
