package view;


import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {

    private boolean selected;

    private ChessPiece chessPiece;

//    public PlayerController playerController = new PlayerController(this);

    public ChessComponent(int size, ChessPiece chessPiece) {
        this.selected = false;
        setSize(size, size);
        setLocation(0,0);
        setVisible(true);
        this.chessPiece = chessPiece;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Helvetica", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        try {
            g2.drawImage(this.chessPiece.getImage(), 0,0,getWidth(), getHeight(),this);// FIXME: Use library to find the correct offset.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.gray);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
