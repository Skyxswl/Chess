package view;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Level {

    Level1(100,30), Level2(200,30),Level3(500,30),Level4(1000,30),Level5(1000,25),
    Level6(1500,25),Level7(3000,25),Level8(5000,30),Level9(8000,30),Level10(10000,30);
    private final int num1, num2;

    Level(int num1, int num2) {
        this.num1 = num1;
        this.num2 = num2;
    }
    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }
}
