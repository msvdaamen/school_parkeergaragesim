package models;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private int totalMinuts;
    private boolean isPaying;
    private boolean hasToPay;
    private static int enterSpeed = 3; // number of cars that can enter per minute
    private static int paymentSpeed = 7; // number of cars that can pay per minute
    private static int exitSpeed = 5; // number of cars that can leave per minute
    private static int weekDayArrivals= 70; // average number of arriving cars per hour
    private static int weekendArrivals = 120; // average number of arriving cars per hour
    private static int weekDayReservation = 30; // average number of reservations cars per hour
    private static int weekendReservation = 80;
    private static int weekDayPassArrivals= 25; // average number of arriving cars per hour
    private static int weekendPassArrivals = 15; // average number of arriving cars per hour


    /**
     * Constructor for objects of class models.Car
     */
    public Car() {

    }

    public static int getWeekDayArrivals() {
        return weekDayArrivals;
    }

    public static int getWeekendArrivals() {
        return weekendArrivals;
    }

    public static int getWeekDayPassArrivals() {
        return weekDayPassArrivals;
    }

    public static int getWeekendPassArrivals() {
        return weekendPassArrivals;
    }

    public static int getWeekDayReservation() {
        return weekDayReservation;
    }

    public static int getWeekendReservation() {
        return weekendReservation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
        this.totalMinuts = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
    public static int getEnterSpeed() {
        return enterSpeed;
    }

    public static int getPaymentSpeed() {
        return paymentSpeed;
    }

    public static int getExitSpeed() {
        return exitSpeed;
    }
    //Antonie: DONT use theses for any other car than ResCar unless you want to get an error.
    public void changeColor(){};

    public int getTotalMinuts() {
        return this.totalMinuts;
    }

    public int getResMinutes(){ return getResMinutes();}

    public int getStayMinutes() {return getStayMinutes();}


}