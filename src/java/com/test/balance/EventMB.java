/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.balance;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author justin
 */
@ManagedBean(name = "EventMB")
@RequestScoped
public class EventMB {

    private JarvisEvent event;
    private List<JarvisEvent> eventsList;

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    public EventMB() {
        this.eventsList = new ArrayList<JarvisEvent>();
    }
    
    @PostConstruct
    public void initPage() {
        this.eventsList.clear();
        try {
            listAllEvents();
        } catch (IOException ex) {
            Logger.getLogger(EventMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listAllEvents() throws IOException{
        List<String[]> myList = new ArrayList<String[]>();

        eventsList = new ArrayList<JarvisEvent>();

        com.google.api.services.calendar.Calendar service
                = CalendarQuickstart.getCalendarService();

        System.out.println("This is working");

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(20)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                eventsList.add(new JarvisEvent(event.getSummary(), (start.toString().equals(today) ? "Today" : start.toString())));
            }
        }
        for (JarvisEvent e : eventsList) {
            System.out.println(e.getSummary() + " -- " + e.getDate());
        }
    }
    
    public List<JarvisEvent> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<JarvisEvent> eventsList) {
        this.eventsList = eventsList;
    }

    public JarvisEvent getEvent() {
        return event;
    }

    public void setEvent(JarvisEvent event) {
        this.event = event;
    }
   
    
}
