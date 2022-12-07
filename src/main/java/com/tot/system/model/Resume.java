package com.tot.system.model;

import java.sql.Date;
import java.util.Objects;

public class Resume {
    private String secId;
    private String regNumber;
    private String name;
    private String emitentTitle;
    private Date tradeDate;
    private Integer numTrades;
    private Double open;
    private Double close;

    public Resume() {
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmitentTitle() {
        return emitentTitle;
    }

    public void setEmitentTitle(String emitentTitle) {
        this.emitentTitle = emitentTitle;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Integer getNumTrades() {
        return numTrades;
    }

    public void setNumTrades(Integer numTrades) {
        this.numTrades = numTrades;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(secId, resume.secId)
                && Objects.equals(regNumber, resume.regNumber)
                && Objects.equals(name, resume.name)
                && Objects.equals(emitentTitle, resume.emitentTitle)
                && Objects.equals(tradeDate, resume.tradeDate)
                && Objects.equals(numTrades, resume.numTrades)
                && Objects.equals(open, resume.open)
                && Objects.equals(close, resume.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secId, regNumber, name, emitentTitle, tradeDate, numTrades, open, close);
    }
}
