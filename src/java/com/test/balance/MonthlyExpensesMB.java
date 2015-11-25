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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author justin
 */
@ManagedBean(name = "MonthlyExpensesMB")
@SessionScoped
public final class MonthlyExpensesMB {

    private MonthlyExpenses monthly;
    private List<MonthlyExpenses> monthlyListGroupedByDay;
    private List<MonthlyExpenses> monthlyExpenseList;
    @EJB
    private MonthlyExpensesEJB meEjb;
    private LineChartModel model;
    private Date today;
    private String todayString;
    private List<Date> dateIndex;
    private BigDecimal projBalance;
    private int projLength;
    private BigDecimal projAdjust;
    private BigDecimal currentBalance;

    public MonthlyExpenses getMonthly() {
        return monthly;
    }

    public void setMonthly(MonthlyExpenses monthly) {
        this.monthly = monthly;
    }

    public List<MonthlyExpenses> getMonthlyListGroupedByDay() {
        return monthlyListGroupedByDay;
    }

    public void setMonthlyList(List<MonthlyExpenses> monthlyList) {
        this.monthlyListGroupedByDay = monthlyList;
    }

    public MonthlyExpensesEJB getMeEjb() {
        return meEjb;
    }

    public void setMeEjb(MonthlyExpensesEJB meEjb) {
        this.meEjb = meEjb;
    }

    public List<MonthlyExpenses> getMonthlyExpenseList() {
        return monthlyExpenseList;
    }

    public void setMonthlyExpenseList(List<MonthlyExpenses> monthlyExpenseList) {
        this.monthlyExpenseList = monthlyExpenseList;
    }

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public MonthlyExpensesMB() {
    }

    public void listAll() {

    }

    public void listMonthlyExpensesGroupByDay() {

        this.monthlyListGroupedByDay = new ArrayList<MonthlyExpenses>();
        List<Object[]> myList = this.meEjb.groupByDay();

        for (Object[] o : myList) {
            System.out.println(o[0] + " , " + o[1]);
        }
    }

    @PostConstruct
    public void initPage() {
        this.projBalance = new BigDecimal("1023.90");
        this.projLength = 12;
        this.projAdjust = new BigDecimal("0.00");
        createLinearModel();
    }

    public void createLinearModel() {
        this.monthlyExpenseList = new ArrayList<MonthlyExpenses>();
        this.monthlyExpenseList.addAll(this.meEjb.listAllMonthlyExpenses());
        this.model = new LineChartModel();
        this.model.setTitle(this.projLength + "-Month Projection");
        this.model.setZoom(true);
        this.model.getAxis(AxisType.Y).setLabel("Balance");
        this.model.setShadow(true);
        this.model.setAnimate(true);
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        /*
         Calendar cal = Calendar.getInstance();
         cal.add(Calendar.MONTH, -1);
         axis.setMin(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
         */
        axis.setTickFormat("%b %#d, %y");

        this.model.getAxes().put(AxisType.X, axis);                             

        try {
            model.addSeries(getChartData(this.projLength + "-Month Projection"));
        } catch (ParseException ex) {
            Logger.getLogger(MonthlyExpensesMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listAllMonthlyExpenses() {
        this.monthlyExpenseList = new ArrayList<MonthlyExpenses>();
        this.monthlyExpenseList.addAll(this.meEjb.listAllMonthlyExpenses());

    }

    public void listMonthlyExpensesForDay(Integer dayOfMonth) {
        this.monthlyExpenseList = new ArrayList<MonthlyExpenses>();
        this.monthlyExpenseList.addAll(this.meEjb.listMonthlyExpensesForDay(dayOfMonth));
    }

    public LineChartSeries getChartData(String label) throws ParseException {

        //this.projBalance = new BigDecimal("1055.87");
        BigDecimal workingBalance = this.projBalance;
        this.dateIndex = new ArrayList();
        Calendar cal = Calendar.getInstance();
        this.today = cal.getTime();
        this.todayString = new SimpleDateFormat("MM/dd/yyyy").format(today);
        //this.projLength = 12;
        cal.add(Calendar.MONTH, projLength);
        Date endDate = cal.getTime();
        Map<Integer, BigDecimal> meMap = new HashMap();
        LineChartSeries data = new LineChartSeries(label);
        int ctr = 0;
        int mod = 1;

        this.monthlyListGroupedByDay = new ArrayList<MonthlyExpenses>();
        List<Object[]> myList = this.meEjb.groupByDay();
        List<String> payDays = this.listPayDays();
        for (Object[] o : myList) {
            meMap.put((Integer) o[0], (BigDecimal) o[1]);
        }
        Date curDate = today;
        while (curDate.before(endDate)) {

            dateIndex.add(curDate);
            data.set(new SimpleDateFormat("yyyy-MM-dd").format(curDate), workingBalance);

            System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(curDate));
            cal.setTime(curDate);
            if (meMap.containsKey(curDate.getDate())) {
                workingBalance = workingBalance.subtract(meMap.get(curDate.getDate()));
            }
            if (payDays.contains(new SimpleDateFormat("MM/dd/yyyy").format(curDate))) {
                workingBalance = workingBalance.add(new BigDecimal("2000.00"));
                if (this.projAdjust != null) {
                    workingBalance = workingBalance.add(projAdjust);

                }
            }

            System.out.println("Charting " + new SimpleDateFormat("MM/dd/yyyy").format(curDate) + " , " + workingBalance.toString());
            //System.out.print(balance);
            cal.add(Calendar.DATE, 1);
            curDate = cal.getTime();
            ctr++;
        }
        return data;
    }

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

    public void itemSelect(ItemSelectEvent event) {
        listMonthlyExpensesForDay(this.dateIndex.get(event.getItemIndex()).getDate());
    }

    public LineChartModel getModel() {
        return model;
    }

    public void setModel(LineChartModel model) {
        this.model = model;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public List<Date> getDateIndex() {
        return dateIndex;
    }

    public void setDateIndex(List<Date> dateIndex) {
        this.dateIndex = dateIndex;
    }

    public BigDecimal getProjBalance() {
        return projBalance;
    }

    public void setProjBalance(BigDecimal projBalance) {
        this.projBalance = projBalance;
    }

    public int getProjLength() {
        return projLength;
    }

    public void setProjLength(int projLength) {
        this.projLength = projLength;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public String getTodayString() {
        return todayString;
    }

    public void setTodayString(String todayString) {
        this.todayString = todayString;
    }

    public BigDecimal getProjAdjust() {
        return projAdjust;
    }

    public void setProjAdjust(BigDecimal projAdjust) {
        this.projAdjust = projAdjust;
    }

}
