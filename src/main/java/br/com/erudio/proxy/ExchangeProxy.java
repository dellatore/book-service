package br.com.erudio.proxy;

import br.com.erudio.model.dto.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchange-service", url = "localhost:8000")
public interface ExchangeProxy {

    @GetMapping(value = "exchange-service/{amount}/{from}/{to}")
    Exchange getExchange(@PathVariable Double amount, @PathVariable String from, @PathVariable String to);
}
