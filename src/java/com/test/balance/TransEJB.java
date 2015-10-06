/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.util.Collection;
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
    
    public void addTrans(Transactions trans){
        this.em.persist(trans);
    }
    
    public Collection<Transactions> listAllTransactions() {
        TypedQuery<Transactions> qry = this.em.createNamedQuery("Transactions.findAll", Transactions.class);
        return qry.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
