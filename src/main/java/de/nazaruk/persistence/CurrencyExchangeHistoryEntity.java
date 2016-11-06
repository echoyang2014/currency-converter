package de.nazaruk.persistence;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nazaruk on 11/6/16.
 */
@Entity
@Table(name = "CURRENCY_EXCHANGE_HISTORY")
@Data
public class CurrencyExchangeHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fromCode;
    private String toCode;
    private BigDecimal rate;
    private Date exchangeDate;
    private String username;

    @Transient
    public BigDecimal getDisplayRate() {
        return rate.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Transient
    public String getDisplayExchangeDate() {
        return new SimpleDateFormat("dd-MM-YYYY").format(getExchangeDate());
    }
}
