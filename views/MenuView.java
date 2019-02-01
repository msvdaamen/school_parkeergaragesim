package views;

import controllers.MenuController;

import javax.swing.*;
import java.util.Hashtable;

public class MenuView extends AbstractView {

    private MenuController controller;
    public JButton start;
    public JButton stop;
    public JSlider versnellen;

    public MenuView(MenuController controller) {
        addView(this);
        this.controller = controller;
        this.start = new JButton("Start");
        this.stop = new JButton("Stop");
        this.versnellen = new JSlider(JSlider.HORIZONTAL, 0, 3, 1);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(0, new JLabel("Stop") );
        labelTable.put(3, new JLabel("Sneller") );
        this.versnellen.setLabelTable( labelTable );

        this.versnellen.setPaintLabels(true);
        add(this.start);
        add(this.stop);
        add(this.versnellen);
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateView() {

    }
}
