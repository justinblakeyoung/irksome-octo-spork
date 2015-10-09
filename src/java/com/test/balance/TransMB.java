/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
    private boolean isAddingMode;
    private String searchString;
    

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
        this.searchString = null;
    }

    public void addNewTransaction(Transactions trans) {
        this.transEJB.addTrans(trans);
    }

    public void printSelection() {
        if (transaction != null) {
            LOG.log(Level.INFO, "The current selected transaction is {0}", this.transaction.getDescription());
        }
    }

    public void initEditDialog() {
        String mode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("editMode");
        if (mode.equalsIgnoreCase("ADD")) {
            this.transaction = new Transactions();
            this.transaction.setId(this.getTransactions().size() + 500);
            this.isAddingMode = true;
        } else {
            this.isAddingMode = false;
        }
    }
    
    public void findByString(){
        this.transactions.clear();
        this.transactions.addAll(this.transEJB.findDescription(this.searchString));
        LOG.log(Level.INFO, "*** the current searchString is {0}", searchString);
        
    }
    
    public void sumsGroupByDesc(){
        this.transactions.clear();
        BigDecimal bd = this.transEJB.sumGroupByDescription();
        Transactions t = new Transactions();
        t.setAmount(bd);
        this.transactions.add(t);
        LOG.log(Level.INFO, "Made it to sumsGroupByDesc");
    }
    
    public void removeTransaction(){
       
        this.transEJB.remove(this.transaction);
        this.transactions.clear();
        this.transactions.addAll(this.transEJB.listAllTransactions());
    }

    public void commitChanges() {
        if (isAddingMode) {
            LOG.log(Level.INFO, "Made it to commit changes in isAddingMode");
            this.transEJB.addTrans(this.transaction);
        }
        else this.transEJB.update(this.transaction);
        
        this.transactions.clear();
        this.transactions.addAll(this.transEJB.listAllTransactions());
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
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
