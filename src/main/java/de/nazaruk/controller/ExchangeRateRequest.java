package de.nazaruk.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by nazaruk on 11/7/16.
 */
@Setter
@Getter
public class ExchangeRateRequest {

    private String from;
    private String to;
    private Date date;
}
