/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.util.Collection;
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
public class MonthlyExpensesEJB {
    private static final Logger LOG = Logger.getLogger(MonthlyExpensesEJB.class.getName());
    
    @PersistenceContext(unitName = "BalancePU")
    private EntityManager em;
    
    /*
    
    
    public Collection<MonthlyExpenses> groupByDay() {
        LOG.log(Level.INFO, "Made it to EJB");
        TypedQuery<MonthlyExpenses> qry = this.em.createNamedQuery("MonthlyExpenses.groupByDay", MonthlyExpenses.class);
        return qry.getResultList();
    }
    */
    
    public Collection<MonthlyExpenses> listAllMonthlyExpenses() {
        TypedQuery<MonthlyExpenses> qry = this.em.createNamedQuery("MonthlyExpenses.findAll", MonthlyExpenses.class);
        return qry.getResultList();
    }
    
    public Collection<MonthlyExpenses> listMonthlyExpensesForDay(Integer DOM) {
        TypedQuery<MonthlyExpenses> qry = (TypedQuery<MonthlyExpenses>) this.em.createNamedQuery("MonthlyExpenses.findByDayOfMonth").setParameter("dayOfMonth", DOM);
        return qry.getResultList();
    }
    
    public List<Object[]> groupByDay() {
        TypedQuery<Object[]> qry = this.em.createNamedQuery("MonthlyExpenses.groupByDay", Object[].class);
        return qry.getResultList();
    }
    
    
}
