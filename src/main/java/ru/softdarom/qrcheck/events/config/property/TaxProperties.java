package ru.softdarom.qrcheck.events.config.property;

//ToDo https://softdarom.myjetbrains.com/youtrack/issue/QRC-55
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Setter
@Getter
@Validated
@ConfigurationProperties("payment.tax")
public class TaxProperties {

    private Double generalTax = 0.02d;

}