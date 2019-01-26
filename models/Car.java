package models;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private static int enterSpeed = 3; // number of cars that can enter per minute
    private static int paymentSpeed = 7; // number of cars that can pay per minute
    private static int exitSpeed = 5; // number of cars that can leave per minute
    private static int weekDayArrivals= 100; // average number of arriving cars per hour
    private static int weekendArrivals = 200; // average number of arriving cars per hour
    private static int weekDayPassArrivals= 50; // average number of arriving cars per hour
    private static int weekendPassArrivals = 5; // average number of arriving cars per hour


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

}