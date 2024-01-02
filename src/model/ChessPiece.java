package model;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;

    public ChessPiece(String name) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}
    public Image getImage() throws IOException {
        Image image1 = new ImageIcon("Picture/chicken.jpg").getImage();
        Image image2 = new ImageIcon("Picture/frog.jpg").getImage();
        Image image3 = new ImageIcon("Picture/cat.jpg").getImage();
        Image image4 = new ImageIcon("Picture/bear.jpg").getImage();
        Image image5 = new ImageIcon("Picture/horse.jpg").getImage();
        Image image6 = new ImageIcon("Picture/fox.jpg").getImage();

        if (name.compareTo("💎") == 0) return image1;
        if (name.compareTo("⚪") == 0) return image2;
        if (name.compareTo("▲") == 0) return image3;
        if (name.compareTo("🔶") == 0) return image4;
        if (name.compareTo("a") == 0) return image5;
        if (name.compareTo("b") == 0) return image6;
        return null;
    }

}
