package views;

import models.Location;

import java.awt.*;

public class LegendaView extends AbstractView {

    public Dimension size;
    private Image legendaImage;

    public LegendaView() {
        addView(this);
        size = new Dimension(0, 0);
    }

    public Dimension getPreferredSize() {
        return new Dimension(150, 200);
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
        this.drawPlace(graphics, 0, 0, Color.YELLOW, "Abbonoment plek");
        this.drawPlace(graphics, 0, 1, Color.BLUE, "Abonoment car");
        this.drawPlace(graphics, 0, 2, Color.RED, "Normale auto");
        this.drawPlace(graphics, 0, 3, Color.GRAY, "Gereserveerde plek");
        this.drawPlace(graphics, 0, 4, Color.GREEN, "Gereserveerde auto");
        this.drawPlace(graphics, 0, 5, Color.white, "Lege plek");
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

    public void drawPlace(Graphics graphics, int x, int y, Color color, String text) {
        graphics.setColor(color);
        graphics.fillRect(x + 5, 60 + (y * 22) + 10, 38, 22);
        graphics.setColor(Color.black);
        graphics.drawString(text, x + 40 + 5, 60 + (y * 22) + 10 + 14);
    }

    public Image getLegendaImage() {
        return this.legendaImage;
    }

    public void setLegendaImage(Image image) {
        this.legendaImage = image;
    }
}
