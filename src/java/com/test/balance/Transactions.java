/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author justin
 */
@Entity
@Table(name = "Transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t ORDER BY t.transDate DESC"),
    @NamedQuery(name = "Transactions.findByTransDate", query = "SELECT t FROM Transactions t WHERE t.transDate = :transDate"),
    @NamedQuery(name = "Transactions.findByDescription", query = "SELECT t FROM Transactions t WHERE t.description LIKE :description"),
    @NamedQuery(name = "Transactions.findByComments", query = "SELECT t FROM Transactions t WHERE t.comments = :comments"),
    @NamedQuery(name = "Transactions.findByCheckNumber", query = "SELECT t FROM Transactions t WHERE t.checkNumber = :checkNumber"),
    @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transactions.sum", query = "SELECT Sum(t.amount) FROM Transactions t"),
    @NamedQuery(name = "Transactions.sumGroupByDescription", query = "SELECT t.description, Sum(t.amount) FROM Transactions t GROUP BY t.description"),
    @NamedQuery(name = "Transactions.findByBalance", query = "SELECT t FROM Transactions t WHERE t.balance = :balance"),
    @NamedQuery(name = "Transactions.findById", query = "SELECT t FROM Transactions t WHERE t.id = :id")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransDate")
    @Temporal(TemporalType.DATE)
    private Date transDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Description")
    private String description;
    @Size(max = 100)
    @Column(name = "Comments")
    private String comments;
    @Size(max = 10)
    @Column(name = "CheckNumber")
    private String checkNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Balance")
    private BigDecimal balance;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Transient
    private String dateString;
    @Transient
    private String searchString;

    public Transactions() {
    }

    public Transactions(Integer id) {
        this.id = id;
    }

    public Transactions(Integer id, Date transDate, String description, BigDecimal amount, BigDecimal balance) {
        this.id = id;
        this.transDate = transDate;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getDateString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(this.transDate);
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.balance.Transactions[ id=" + id + " ]";
    }

}
