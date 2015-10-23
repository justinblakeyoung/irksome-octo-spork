/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author justin
 */
@Entity
@Table(name = "monthly_expenses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MonthlyExpenses.findAll", query = "SELECT m FROM MonthlyExpenses m"),
    @NamedQuery(name = "MonthlyExpenses.groupByDay", query = "SELECT m.dayOfMonth, SUM(m.amount) FROM MonthlyExpenses m GROUP BY m.dayOfMonth"),
    @NamedQuery(name = "MonthlyExpenses.findByIdmonthlyExpenses", query = "SELECT m FROM MonthlyExpenses m WHERE m.idmonthlyExpenses = :idmonthlyExpenses"),
    @NamedQuery(name = "MonthlyExpenses.findByDescription", query = "SELECT m FROM MonthlyExpenses m WHERE m.description = :description"),
    @NamedQuery(name = "MonthlyExpenses.findByAmount", query = "SELECT m FROM MonthlyExpenses m WHERE m.amount = :amount"),
    @NamedQuery(name = "MonthlyExpenses.findByDayOfMonth", query = "SELECT m FROM MonthlyExpenses m WHERE m.dayOfMonth = :dayOfMonth")})
public class MonthlyExpenses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "idmonthly_expenses")
    private Integer idmonthlyExpenses;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "dayOfMonth")
    private Integer dayOfMonth;

    public MonthlyExpenses() {
    }

    public MonthlyExpenses(Integer idmonthlyExpenses) {
        this.idmonthlyExpenses = idmonthlyExpenses;
    }

    public MonthlyExpenses(Integer idmonthlyExpenses, String description) {
        this.idmonthlyExpenses = idmonthlyExpenses;
        this.description = description;
    }

    public Integer getIdmonthlyExpenses() {
        return idmonthlyExpenses;
    }

    public void setIdmonthlyExpenses(Integer idmonthlyExpenses) {
        this.idmonthlyExpenses = idmonthlyExpenses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmonthlyExpenses != null ? idmonthlyExpenses.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonthlyExpenses)) {
            return false;
        }
        MonthlyExpenses other = (MonthlyExpenses) object;
        if ((this.idmonthlyExpenses == null && other.idmonthlyExpenses != null) || (this.idmonthlyExpenses != null && !this.idmonthlyExpenses.equals(other.idmonthlyExpenses))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.balance.MonthlyExpenses[ idmonthlyExpenses=" + idmonthlyExpenses + " ]";
    }
    
}