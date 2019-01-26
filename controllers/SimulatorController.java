package controllers;

import models.*;
import views.CarParkView;

import java.awt.*;
import java.util.Random;

public class SimulatorController extends AbstractController {
    private CarParkView carParkView;

    public static final String AD_HOC = "1";
    public static final String PASS = "2";
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private Car[][][] cars;

    public SimulatorController(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        addController(this);
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        this.carParkView = new CarParkView(this);
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];

    }

    public CarParkView getCarParkView() {
        return this.carParkView;
    }

    private void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue);
        carsEntering(entranceCarQueue);
    }

    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue()>0 &&
                getNumberOfOpenSpots() > 0 &&
                i<Car.getExitSpeed()) {
            Car car = queue.removeCar();
            Location freeLocation = getFirstFreeLocation();
            setCarAt(freeLocation, car);
            i++;
        }
    }

    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue()>0 && i < Car.getExitSpeed()){
            exitCarQueue.removeCar();
            i++;
        }
    }

    private void carsArriving(){
        int numberOfCars = getNumberOfCars(Car.getWeekDayArrivals(), Car.getWeekendArrivals());
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(Car.getWeekDayPassArrivals(), Car.getWeekendPassArrivals());
        addArrivingCars(numberOfCars, PASS);
    }

    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()){
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    private void carsPaying() {
        // Let cars pay.
        int i=0;
        while (paymentCarQueue.carsInQueue()>0 && i < Car.getPaymentSpeed()){
            Car car = paymentCarQueue.removeCar();
            // TODO Handle payment.
            carLeavesSpot(car);
            i++;
        }
    }

    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    entranceCarQueue.addCar(new AdHocCar());
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    entrancePassQueue.addCar(new ParkingPassCar());
                }
                break;
        }
    }

    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = Time.getDay() < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void carLeavesSpot(Car car){
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);
        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

//    public void tick() {
//        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
//            for (int row = 0; row < getNumberOfRows(); row++) {
//                for (int place = 0; place < getNumberOfPlaces(); place++) {
//                    Location location = new Location(floor, row, place);
//                    Car car = getCarAt(location);
//                    if (car != null) {
//                        car.tick();
//                    }
//                }
//            }
//        }
//    }
//
//    public void updateView() {
//        // Create a new car park image if the size has changed.
//        if (!this.carParkView.size.equals(this.carParkView.getSize())) {
//            this.carParkView.size = this.carParkView.getSize();
//            this.carParkView.setCarParkImage(this.carParkView.createImage(this.carParkView.size.width, this.carParkView.size.height));
//        }
//        Graphics graphics = this.carParkView.getCarParkImage().getGraphics();
//        for(int floor = 0; floor < this.numberOfFloors; floor++) {
//            for(int row = 0; row < this.numberOfRows; row++) {
//                for(int place = 0; place < this.numberOfPlaces; place++) {
//                    Location location = new Location(floor, row, place);
//                    Car car = getCarAt(location);
//                    Color color = car == null ? Color.white : car.getColor();
//                    this.carParkView.drawPlace(graphics, location, color);
//                }
//            }
//        }
//        this.carParkView.repaint();
//    }
    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    public Car[][][] getCars() {
        return cars;
    }

    public void updateView() {
        this.carParkView.updateView();
    }

    @Override
    public void tick() {
        handleExit();
    }

    @Override
    public void updateController() {
        handleEntrance();
    }
}
