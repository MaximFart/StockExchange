package com.tot.system.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "securities")
public class Security {
    @Column(name = "id")
    private Long id;
    @Id
    @Column(name = "secid")
    private String secId;
    @Column(name = "regnumber")
    private String regNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "emitent_title")
    private String emitentTitle;

    @OneToMany(mappedBy = "security", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories;

    public Security() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Security security = (Security) o;
        return Objects.equals(id, security.id)
                && Objects.equals(secId, security.secId)
                && Objects.equals(regNumber, security.regNumber)
                && Objects.equals(name, security.name)
                && Objects.equals(emitentTitle, security.emitentTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secId, regNumber, name, emitentTitle);
    }
}
