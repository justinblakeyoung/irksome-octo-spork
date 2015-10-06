/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author justin
 */
@ManagedBean
@SessionScoped
public class TransMB {

    @EJB
    private TransEJB transEJB;
    private Collection<Transactions> transactions;
    private Transactions transaction;
    
    private static final Logger LOG = Logger.getLogger(TransMB.class.getName());
    
    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public TransMB() {
        this.transactions = new ArrayList<Transactions>();
    }
    
   
    @PostConstruct
    public void initPage() {
        this.transactions.clear();
        this.transactions.addAll(this.transEJB.listAllTransactions());
    }
    
    public void addNewTransaction(Transactions trans){
        this.transEJB.addTrans(trans);
    }

    public TransEJB getTransEJB() {
        return transEJB;
    }

    public void setTransEJB(TransEJB transEJB) {
        this.transEJB = transEJB;
    }

    public Collection<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transactions> transactions) {
        this.transactions = transactions;
    }

    public Transactions getTransaction() {
        return transaction;
    }

    public void setTransaction(Transactions transaction) {
        this.transaction = transaction;
    }
   
    
}
