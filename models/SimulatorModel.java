package models;

import java.util.Random;
import java.awt.*;


public class SimulatorModel {
    public static final String AD_HOC = "1";
    public static final String PASS = "2";
    public static final String RES = "3";
    private int currentDay = 0;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    //Amount of VIP.
    private int numberOfVipRows;
    private int numberOfVipFloors;

    //Antonie: these values are here to make sure that we dont get more than the
    // maximum amount of pass cars at any one time.
    private int maxPassCar;
    private int numberPassCar;

    //Antonie: loadsa money
    private int revenueWeek[];

    private CarQueue entranceCarQueue;


    private CarQueue entrancePassQueue;
    private CarQueue entranceResQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private Car[][][] cars;


    public SimulatorModel(int numberOfFloors, int numberOfRows, int numberOfPlaces, int numberOfVipRows, int numberOfVipFloors) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        entranceResQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        this.revenueWeek = new int[7];
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfVipRows = numberOfVipRows;
        this.numberOfVipFloors = numberOfVipFloors;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;

        //Antonie: Amount of Vip spaces = amount of PassCar
        this.maxPassCar = numberOfVipFloors * numberOfVipRows * numberOfPlaces;
        this.numberPassCar = 0;

        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
    }

    public void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue, PASS);
        carsEntering(entranceCarQueue, AD_HOC);
        carsEntering(entranceResQueue, RES);
        //Antonie:
        checkForReservation();
        /////////
    }

    public void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    private void carsEntering(CarQueue queue, String type){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue()>0 &&
                getNumberOfOpenSpots() > 0 &&
                i<Car.getExitSpeed()) {
            Car car = queue.removeCar();
            //Antonie: Cars with a pass will look for a VIP location
            Location freeLocation;
            switch(type) {
                case PASS:
                    //Antonie: Dont add another passCar if all the passCars are on the parking lot!
                    if(numberPassCar < maxPassCar) {
                        //A pass car has come to the parking lot
                        numberPassCar++;
                        freeLocation = getFirstFreeVipLocation();
                        setCarAt(freeLocation, car);
                    }
                        break;
                case RES:
                    freeLocation = getFirstFreeLocation();
                    setCarAt(freeLocation, car);
                    break;
                default:
                    freeLocation = getFirstFreeLocation();
                    setCarAt(freeLocation, car);
                    break;
            }
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
        numberOfCars = getNumberOfCars(Car.getWeekDayReservation(), Car.getWeekendReservation());
        addArrivingCars(numberOfCars, RES);
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
                //Antonie: one of the Pass cars has left, remove amount of Pass Car on parking lot
                numberPassCar--;
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
            if(currentDay != Time.getDay() ) {
                revenueWeek[Time.getDay()] = 0;
                currentDay = Time.getDay();

            }
            //Antonie: Reservations cost more money than normal parking.
            if((car.getColor() == Color.green) || (car.getColor() == Color.gray)){
                revenueWeek[Time.getDay()] += car.getTotalMinuts() * 0.0045f + 10;
            } else {
                revenueWeek[Time.getDay()] += car.getTotalMinuts() * 0.0045f;
            }
            carLeavesSpot(car);
            i++;
        }
    }

    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    //Antonie: If waiting line is too long, keep driving.
                    if(entranceCarQueue.carsInQueue() <= 30) {
                        entranceCarQueue.addCar(new AdHocCar());
                    }
                }
                break;
            case PASS:
                for (int i = 0; i < numberOfCars; i++) {
                    //Antonie: Car with pass is more patient
                    if(entrancePassQueue.carsInQueue()<=40) {
                        entrancePassQueue.addCar(new ParkingPassCar());
                    }
                }
                break;
            case RES:
                for (int i = 0; i < numberOfCars; i++) {
                    //Antonie: Car with pass is more patient
                    if(entranceResQueue.carsInQueue()<=40) {
                        entranceResQueue.addCar(new ResCar());
                    }
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
        if(location != null) {
            int floor = location.getFloor();
            int row = location.getRow();
            int place = location.getPlace();
            if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
                return false;
            }
        }
        return true;
    }

    //Whether or not a spot is VIP
    public boolean locationIsVip(Location location) {
        int row = location.getRow();
        int floor = location.getFloor();
        if(row < numberOfVipRows && floor < numberOfVipFloors) {
            return true;
        }
        return false;
    }


    public Car getCarAt(Location location) {
        if(location == null) {
            return null;
        }
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
                    if (getCarAt(location) == null && !locationIsVip(location)) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    //Antonie: Get first free Vip location
    public Location getFirstFreeVipLocation() {
        for (int floor = 0; floor < getNumberOfVipFloors(); floor++) {
            for (int row = 0; row < getNumberOfVipRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null && locationIsVip(location)) {
                        return location;
                    }
                }
            }
        }
        //Cant find VIP location, so get a normal free location.
        Location location = getFirstFreeLocation();
        return location;
    }
    ///////////////////////////////////////////////////////////////////////////////

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

    //Antonie: Looks for reservation who need to become a car.
    public void checkForReservation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getColor() == Color.gray && (car.getStayMinutes() - car.getResMinutes()) > car.getMinutesLeft()) {
                        car.changeColor();
                    }
                }
            }
        }
    }



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

    public int getNumberOfVipRows() {
        return numberOfVipRows;
    }

    public int getNumberOfVipFloors() {
        return numberOfVipFloors;
    }

    public Car[][][] getCars() {
        return cars;
    }

    public CarQueue getEntranceCarQueue(){
        return entranceCarQueue;
    }


    public CarQueue getEntrancePassQueue() {
        return entrancePassQueue;
    }

    public CarQueue getEntranceResQueue() {
        return entranceResQueue;
    }

    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }

    public int[] getRevenueWeek() {
        return this.revenueWeek;
    }
}
