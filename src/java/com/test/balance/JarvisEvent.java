/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

/**
 *
 * @author justin
 */
public class JarvisEvent {
    private String summary;
    private String date;
    
    public JarvisEvent (String summary, String date){
        this.summary = summary;
        this.date = date;
    }
    
    public JarvisEvent(){
        
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
