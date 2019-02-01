package models;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ResCar extends Car   {
        private Color COLOR=Color.gray;
        private int resMinutes;
        private int stayMinutes;

        public ResCar() {
            Random random = new Random();
            stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
            resMinutes =  random.nextInt(45) +1;
            this.setMinutesLeft(stayMinutes);
            this.setHasToPay(true);
        }

        public void changeColor(){ COLOR = Color.green; }

        public Color getColor(){
            return COLOR;
        }

        public int getResMinutes(){ return resMinutes;}

        public int getStayMinutes() {return stayMinutes;}
}
