package views;

import controllers.GrafiekController;
import models.Location;
import models.Time;

import javax.swing.*;
import java.awt.*;

public class GrafiekView extends AbstractView {

    public Dimension size;
    private Image legendaImage;
    private GrafiekController controller;
    private JLabel totalRevenue;
    private JLabel carsQue;
    private JLabel passQue;
    private JLabel resQue;
    private JLabel exitQue;

    public GrafiekView(GrafiekController controller) {
        addView(this);
        this.controller = controller;
        size = new Dimension(0, 0);
        totalRevenue = new JLabel("Totale omzet: ");
        carsQue = new JLabel("Aantal auto's in de rij: ");
        passQue = new JLabel("Aantal auto's in abbonoment rij: ");
        resQue = new JLabel("Aantal auto's in de reservingen rij: ");
        exitQue = new JLabel("Aantal auto's bij de uitgang: ");
        add(totalRevenue);
        add(carsQue);
        add(passQue);
        add(resQue);
        add(exitQue);
    }

    public Dimension getPreferredSize() {
        return new Dimension(230, 200);
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateView() {
        if (!this.size.equals(this.getSize())) {
            this.size = this.getSize();
            this.setLegendaImage(this.createImage(this.size.width, this.size.height));
        }
        Graphics graphics = this.getLegendaImage().getGraphics();
        int max = 1;
        for(int i = 0; i < this.controller.getRevenueWeek().length; i++) {
            if(this.controller.getRevenueWeek()[i] > max) {
                max = this.controller.getRevenueWeek()[i];
            }
        }
        this.totalRevenue.setText("Totale omzet " + Time.getDayString() + ": " + this.controller.getRevenueWeek()[Time.getDay()]);
        carsQue.setText("Aantal auto's in de rij: " + this.controller.getQueNormal());
        passQue.setText("Aantal auto's in abbonoment rij: " + this.controller.getQuePass());
        resQue.setText("Aantal auto's in de reservingen rij: " + this.controller.getResQue());
        exitQue.setText("Aantal auto's bij de uitgang: " + this.controller.getExitQue());

//        for(int i = 0; i < this.controller.getRevenueWeek().length; i++) {
//            int aantal = this.controller.getRevenueWeek()[i];
//            this.drawPlace(graphics, i, aantal, max, Color.GREEN);
//        }

        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (legendaImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(legendaImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(legendaImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void drawPlace(Graphics graphics, int offset, int aantal, int max, Color color) {
        graphics.setColor(color);
        graphics.fillRect(100 + (offset * 10) + (offset * 5), 100 - (100 / max * aantal), 10, 100 / max * aantal);
    }

    public Image getLegendaImage() {
        return this.legendaImage;
    }

    public void setLegendaImage(Image image) {
        this.legendaImage = image;
    }
}
