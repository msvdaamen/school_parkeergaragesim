package views;

import controllers.TimerController;

import javax.swing.*;
import java.awt.*;

public class TimerView extends AbstractView {

    private TimerController controller;
    public JLabel timeLabel;

    public TimerView(TimerController controller) {
        addView(this);
        this.controller = controller;
        this.timeLabel = new JLabel("");
        Font labelFont = timeLabel.getFont();
        this.timeLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 20));
        add(this.timeLabel);
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateView() {
        this.timeLabel.setText(this.controller.getTime());
    }
}
