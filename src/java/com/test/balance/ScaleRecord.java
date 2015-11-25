/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author justin
 */
@Entity
@Table(name = "scale_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScaleRecord.findAll", query = "SELECT s FROM ScaleRecord s ORDER BY s.scaleDate DESC"),
    @NamedQuery(name = "ScaleRecord.mostRecent", query = "SELECT MAX(s.scaleDate) from ScaleRecord s"),
    @NamedQuery(name = "ScaleRecord.findByScaleDate", query = "SELECT s FROM ScaleRecord s WHERE s.scaleDate = :scaleDate"),
    @NamedQuery(name = "ScaleRecord.findByScaleWeight", query = "SELECT s FROM ScaleRecord s WHERE s.scaleWeight = :scaleWeight"),
    @NamedQuery(name = "ScaleRecord.findByScaleCalorie", query = "SELECT s FROM ScaleRecord s WHERE s.scaleCalorie = :scaleCalorie")})
public class ScaleRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "scale_date")
    @Temporal(TemporalType.DATE)
    private Date scaleDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "scale_weight")
    private Double scaleWeight;
    @Column(name = "scale_calorie")
    private Integer scaleCalorie;
    @Transient
    private String dateString;

    public ScaleRecord() {
    }

    public ScaleRecord(Date scaleDate) {
        this.scaleDate = scaleDate;
    }

    public Date getScaleDate() {
        return scaleDate;
    }

    public void setScaleDate(Date scaleDate) {
        this.scaleDate = scaleDate;
    }

    public Double getScaleWeight() {
        return scaleWeight;
    }

    public void setScaleWeight(Double scaleWeight) {
        this.scaleWeight = scaleWeight;
    }

    public Integer getScaleCalorie() {
        return scaleCalorie;
    }

    public void setScaleCalorie(Integer scaleCalorie) {
        this.scaleCalorie = scaleCalorie;
    }

    public String getDateString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(this.scaleDate);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scaleDate != null ? scaleDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScaleRecord)) {
            return false;
        }
        ScaleRecord other = (ScaleRecord) object;
        if ((this.scaleDate == null && other.scaleDate != null) || (this.scaleDate != null && !this.scaleDate.equals(other.scaleDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.test.balance.ScaleRecord[ scaleDate=" + scaleDate + " ]";
    }
    
}
