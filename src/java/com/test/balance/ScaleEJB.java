/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author justin
 */
@Stateless
public class ScaleEJB {
    
    @PersistenceContext(unitName = "BalancePU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
    private static final Logger LOG = Logger.getLogger(ScaleEJB.class.getName());
    
    public void addScaleRecord(ScaleRecord record) {
        LOG.log(Level.INFO, "Adding scale record {0}", record.getScaleDate());
        try {
            this.em.persist(record);
        } catch (Exception e) {
            LOG.log(Level.INFO, "Adding result " + e.getMessage(), e);
        }
    }

    public void update(ScaleRecord record) {
        if (!this.em.contains(record)) {
            this.em.merge(record);
        }
    }

    public void remove(ScaleRecord record) {
        if (!this.em.contains(record)) {
            record = this.em.merge(record);
        }

        this.em.remove(record);
    }
    
    public List<ScaleRecord> listAllRecords() {
        TypedQuery<ScaleRecord> qry = this.em.createNamedQuery("ScaleRecord.findAll", ScaleRecord.class);
        return qry.getResultList();
    }
    
}
