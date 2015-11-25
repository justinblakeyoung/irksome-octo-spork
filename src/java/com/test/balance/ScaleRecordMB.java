/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author justin
 */
@ManagedBean
@SessionScoped
public class ScaleRecordMB {

    ScaleRecord record;
    List<ScaleRecord> recordList;
    List<String> dateList;
    @EJB
    ScaleEJB scaleEJB;
    private boolean isAddingMode;

    @PostConstruct
    public void initPage() {
        dateList = new ArrayList();
        this.recordList.clear();
        this.recordList.addAll(this.scaleEJB.listAllRecords());
        for (ScaleRecord r : recordList) {
            dateList.add(r.getDateString());
        }
    }

    /**
     * Creates a new instance of ScaleRecordMB
     */
    public ScaleRecordMB() {
        recordList = new ArrayList();
    }

    public void initEditDialog() throws ParseException {
        String mode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recordEditMode");
        if (mode.equalsIgnoreCase("ADD")) {
            this.record = new ScaleRecord();
            this.isAddingMode = true;
        } else {
            this.isAddingMode = false;
        }
    }

    public void commitChanges() {
        if (this.isAddingMode == true) {
            if (!this.dateList.contains(this.record.getDateString())) {
                this.scaleEJB.addScaleRecord(this.record);
            }
            else{
                showDuplicateWarning(this.record.getDateString());
            }
        } else {
            this.scaleEJB.update(this.record);
        }

        this.initPage();
    }
    
    public void showDuplicateWarning(String date) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "You already have a record for " + date ));
    }
    
    public void removeRecord() {
        this.scaleEJB.remove(this.record);
        this.initPage();
    }

    public ScaleRecord getRecord() {
        return record;
    }

    public void setRecord(ScaleRecord record) {
        this.record = record;
    }

    public List<ScaleRecord> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ScaleRecord> recordList) {
        this.recordList = recordList;
    }

    public ScaleEJB getScaleEJB() {
        return scaleEJB;
    }

    public void setScaleEJB(ScaleEJB scaleEJB) {
        this.scaleEJB = scaleEJB;
    }

    public boolean isIsAddingMode() {
        return isAddingMode;
    }

    public void setIsAddingMode(boolean isAddingMode) {
        this.isAddingMode = isAddingMode;
    }

}
