package fernandez.abel.cryptobets.gateway.nomics;

import fernandez.abel.cryptobets.config.NomicsInterceptor;
import fernandez.abel.cryptobets.gateway.nomics.dto.NomicsCurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "nomicsServiceClient", url = "${currencyService.nomics.url}", configuration = NomicsInterceptor.class)
public interface NomicsServiceClient {

    @GetMapping(value = "/ticker?ids={currencyCode}&interval={interval}", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<NomicsCurrencyResponse> getBitcoinInformation(
            @PathVariable(value = "currencyCode") String currencyCode,
            @PathVariable(value = "interval") String interval
    );

}
