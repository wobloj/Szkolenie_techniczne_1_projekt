/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.szkolenie_techniczne_1_projekt;

import java.math.BigDecimal;

/**
 *
 * @author Wiktor
 */
public class Currencies {
    
    private String currencies = null;
    private BigDecimal rate = null;
    private int lp = 0;

    public Currencies(int lp, String currencies, BigDecimal rate) {
        this.lp = lp;
        this.currencies = currencies;
        this.rate = rate;
    }  

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }
    
    public String getCurrencies() {
        return currencies;
    }

    public void setCurrencies(String currencies) {
        this.currencies = currencies;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
