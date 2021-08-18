package fernandez.abel.cryptobets.gateway.nomics;

import fernandez.abel.cryptobets.gateway.CurrencyClient;
import fernandez.abel.cryptobets.gateway.nomics.dto.NomicsCurrencyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NomicsCurrencyClient implements CurrencyClient {

    @Autowired
    private NomicsServiceClient nomicsServiceClient;

    //TODO llevar como configuraion
    //    @Value("${currencyService.currencyCod}")
    private String currencyCode = "BTC";
    //    @Value("${currencyService.interval}")
    private String currencyInterval = "1m";

    public Double getBitcoinPriceByMinute() {
        NomicsCurrencyResponse response = nomicsServiceClient.getBitcoinInformation(currencyCode, currencyInterval).get(0);
        return response.getPrice();
    }


}
