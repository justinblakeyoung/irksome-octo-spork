/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author justin
 */
@ManagedBean(name = "MonthlyExpensesMB")
@RequestScoped
public class MonthlyExpensesMB {

    private MonthlyExpenses monthly;
    private List<MonthlyExpenses> monthlyList;
    @EJB
    private MonthlyExpensesEJB meEjb;
    private static final Logger LOG = Logger.getLogger(MonthlyExpensesMB.class.getName());
    private CartesianChartModel model;

    public MonthlyExpenses getMonthly() {
        return monthly;
    }

    public void setMonthly(MonthlyExpenses monthly) {
        this.monthly = monthly;
    }

    public List<MonthlyExpenses> getMonthlyList() {
        return monthlyList;
    }

    public void setMonthlyList(List<MonthlyExpenses> monthlyList) {
        this.monthlyList = monthlyList;
    }

    public MonthlyExpensesEJB getMeEjb() {
        return meEjb;
    }

    public void setMeEjb(MonthlyExpensesEJB meEjb) {
        this.meEjb = meEjb;
    }

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public MonthlyExpensesMB() {

    }

    public void listAll() {

    }

    public void listMonthlyExpensesGroupByDay() {

        LOG.log(Level.INFO, "made it to MB **********");
        this.monthlyList = new ArrayList<MonthlyExpenses>();
        LOG.log(Level.INFO, "made it to MB 2**********");

        List<Object[]> myList = this.meEjb.groupByDay();

        for (Object[] o : myList) {
            System.out.println(o[0] + " , " + o[1]);
        }
    }

    @PostConstruct
    public void createLinearModel() {
        model = new CartesianChartModel();
        try {
            model.addSeries(getChartData("Projection"));
        } catch (ParseException ex) {
            Logger.getLogger(MonthlyExpensesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ChartSeries getChartData(String label) throws ParseException {

        BigDecimal balance = new BigDecimal("326.00");
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.MONTH, 2);
        Date endDate = cal.getTime();
        Map<Integer, BigDecimal> meMap = new HashMap();
        ChartSeries data = new ChartSeries(label);
        int ctr = 0;
        int mod = 1;

        this.monthlyList = new ArrayList<MonthlyExpenses>();
        List<Object[]> myList = this.meEjb.groupByDay();
        List<String> payDays = this.listPayDays();
        for (Object[] o : myList) {
            meMap.put((Integer) o[0], (BigDecimal) o[1]);
        }
        Date curDate = today;
        while (curDate.before(endDate)) {
            System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(curDate));
            cal.setTime(curDate);
            if (meMap.containsKey(curDate.getDate())) {
                balance = balance.subtract(meMap.get(curDate.getDate()));
            }
            if (payDays.contains(new SimpleDateFormat("MM/dd/yyyy").format(curDate))) {
                balance = balance.add(new BigDecimal("2200.50"));
            }
            if (ctr%mod == 0) {
                data.set(new SimpleDateFormat("MM/dd/yyyy").format(curDate), balance);
            }
            System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(curDate) + " , " + balance.toString());
            //System.out.print(balance);
            cal.add(Calendar.DATE, 1);
            curDate = cal.getTime();
            ctr++;
        }
        return data;
    }

    /*
     public ChartSeries getChartData(String label) throws ParseException {
     ChartSeries data = new ChartSeries(label);
     data.set("one", 5);
     data.set("two", 10);
     LOG.log(Level.INFO, "Added to ChartSeries************");
     return data;
     }*/
    public List<String> listPayDays() throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        cal.add(Calendar.YEAR, 5);
        Date end = cal.getTime();
        Date current = format.parse("10/30/2015");
        cal.setTime(current);
        List<String> payDays = new ArrayList<String>();

        while (current.before(end)) {
            payDays.add(format.format(cal.getTime()));
            cal.add(Calendar.DATE, 14);
            current = cal.getTime();
            System.out.println(current.toString());
        }

        return payDays;

    }

    public CartesianChartModel getModel() {
        return model;
    }

    public void setModel(CartesianChartModel model) {
        this.model = model;
    }

}
