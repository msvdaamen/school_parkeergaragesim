package views;

import controllers.SimulatorController;
import models.Car;
import models.Location;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CarParkView extends AbstractView {

    public Dimension size;
    private Image carParkImage;
    private SimulatorController controller;
    private int limit = 0;
    public boolean knopje = false;



    //buttons
    public JButton stopButton;
    public JButton plusHonderd;
    public JButton resetSimulatie;
    public JButton doorgaan;


    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView (SimulatorController controller) {

        addView(this);
        this.controller = controller;
        setLayout(null);
        size = new Dimension(0, 0);
        // Knoppen construeren

        stopButton = new JButton("Stop");
        stopButton.setBounds(50, 400, 100, 20);
        add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setRunning(false);
            }
        });

        doorgaan = new JButton("Doorgaan");
        doorgaan.setBounds(160, 400, 100, 20);
        add(doorgaan);
        doorgaan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setRunning(true);
            }
        });





        plusHonderd = new JButton("+100");
        plusHonderd.setBounds(50, 430, 100, 20);
        add(plusHonderd);
        plusHonderd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });



        resetSimulatie = new JButton("Reset");
        resetSimulatie.setBounds(50, 460, 100, 20);
        add(resetSimulatie);
        resetSimulatie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



            }
        });






    }



    /**     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
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

    public boolean getKnopje(){
        return knopje;
    }


    public void updateView() {

            // Create a new car park image if the size has changed.


            if (!this.size.equals(this.getSize())) {
                this.size = this.getSize();
                this.setCarParkImage(this.createImage(this.size.width, this.size.height));
            }
            Graphics graphics = this.getCarParkImage().getGraphics();


            for (int floor = 0; floor < this.controller.getNumberOfFloors(); floor++) {


                for (int row = 0; row < this.controller.getNumberOfRows(); row++) {

                    for (int place = 0; place < this.controller.getNumberOfPlaces(); place++) {



                        Location location = new Location(floor, row, place);
                        Car car = this.controller.getCarAt(location);

                        //Antonie: Vip spots have a different color.


                        Color fieldColor;
                        if (controller.locationIsVip(location)) {
                            fieldColor = Color.yellow;
                        } else {
                            fieldColor = Color.white;
                        }
                        Color color = car == null ? fieldColor : car.getColor();
                        ///////////////////////////////////////////////////////
                        this.drawPlace(graphics, location, color);


                    }
                }





            this.repaint();

        }
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {

            if (carParkImage == null ) {
                return;
            }

            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            } else {
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
                    location.getFloor() * 260 + (1 + (int) Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
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
