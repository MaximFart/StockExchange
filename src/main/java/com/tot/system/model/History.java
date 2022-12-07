package com.tot.system.model;


import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "histories")
public class History {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "tradedate")
    private Date tradeDate;
    @Column(name = "numtrades")
    private Integer numTrades;
    @Column(name = "open")
    private Double open;
    @Column(name = "close")
    private Double close;

    @ManyToOne
    @JoinColumn(name = "secid")
    private Security security;

    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(id, history.id)
                && Objects.equals(tradeDate, history.tradeDate)
                && Objects.equals(numTrades, history.numTrades)
                && Objects.equals(open, history.open)
                && Objects.equals(close, history.close)
                && Objects.equals(security, history.security);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tradeDate, numTrades, open, close, security);
    }
}
