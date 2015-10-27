/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
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
public class TransEJB {

    @PersistenceContext(unitName = "BalancePU")
    private EntityManager em;

    private static final Logger LOG = Logger.getLogger(TransEJB.class.getName());

    public void addTrans(Transactions trans) {
        LOG.log(Level.INFO, "Adding transaction {0}", trans.getId());
        try {
            this.em.persist(trans);
        } catch (Exception e) {
            LOG.log(Level.INFO, "Adding result " + e.getMessage(), e);
        }
    }

    public void update(Transactions trans) {
        if (!this.em.contains(trans)) {
            this.em.merge(trans);
        }
    }

    public void remove(Transactions trans) {
        if (!this.em.contains(trans)) {
            trans = this.em.merge(trans);
        }

        this.em.remove(trans);
    }

    public Collection<Transactions> listAllTransactions() {
        TypedQuery<Transactions> qry = this.em.createNamedQuery("Transactions.findAll", Transactions.class);
        return qry.getResultList();
    }

    public Collection<Transactions> findDescription(String searchstring) {
        TypedQuery<Transactions> qry = (TypedQuery<Transactions>) this.em.createNamedQuery("Transactions.findByDescription").setParameter("description", "%" + searchstring + "%");
        return qry.getResultList();
    }

    public Collection<Transactions> findDate(Date tDate) {
        TypedQuery<Transactions> qry = (TypedQuery<Transactions>) this.em.createNamedQuery("Transactions.findByTransDate").setParameter("transDate", tDate);
        return qry.getResultList();
    }

    public BigDecimal sum() {
        TypedQuery<BigDecimal> qry = this.em.createNamedQuery("Transactions.sum", BigDecimal.class);
        return qry.getSingleResult();
    }

    public List<Object[]> sumGroupByDescription() {
        TypedQuery<Object[]> qry = this.em.createNamedQuery("Transactions.sumGroupByDescription", Object[].class);
        return qry.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
