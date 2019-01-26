package views;

import controllers.SimulatorController;
import models.Car;
import models.Location;

import java.awt.*;

public class CarParkView extends AbstractView {

    public Dimension size;
    private Image carParkImage;
    private SimulatorController controller;

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView(SimulatorController controller) {
        addView(this);
        this.controller = controller;
        size = new Dimension(0, 0);
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    public void tick() {
        for (int floor = 0; floor < this.controller.getNumberOfFloors(); floor++) {
            for (int row = 0; row < this.controller.getNumberOfRows(); row++) {
                for (int place = 0; place < this.controller.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = this.controller.getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!this.size.equals(this.getSize())) {
            this.size = this.getSize();
            this.setCarParkImage(this.createImage(this.size.width, this.size.height));
        }
        Graphics graphics = this.getCarParkImage().getGraphics();
        for(int floor = 0; floor < this.controller.getNumberOfFloors(); floor++) {
            for(int row = 0; row < this.controller.getNumberOfRows(); row++) {
                for(int place = 0; place < this.controller.getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = this.controller.getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    this.drawPlace(graphics, location, color);
                }
            }
        }
        this.repaint();
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    public void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }

    public Image getCarParkImage() {
        return this.carParkImage;
    }

    public void setCarParkImage(Image image) {
        this.carParkImage = image;
    }
}