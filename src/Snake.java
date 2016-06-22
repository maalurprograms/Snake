import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by jonas on 22.06.16.
 */
public class Snake {

    private JButton[][] Fields = new JButton[50][50];
    private JFrame Frame = new JFrame();
    private Character Direction = 'd';
    Point Food;
    java.util.List<Point> SnakePath = new ArrayList<>();
    Point SnakeHead;
    int SnakeSize = 0;

    public static void main(String[] args){new Snake();}

    public Snake(){
        setFields();
        setFood();
        setSnake();
        Frame.setSize(Fields.length*10, Fields.length*10);
        Frame.setLayout(new GridLayout(Fields.length, Fields.length));
        Frame.setVisible(true);
        Frame.setFocusable(true);
        Frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyChar()){
                    case 'w':
                        Direction = 'u';
                        break;
                    case 's':
                        Direction = 'd';
                        break;
                    case 'a':
                        Direction = 'l';
                        break;
                    case 'd':
                        Direction = 'r';
                        break;
                }
                System.out.println(Direction);
            }
        });
        mainLoop();
    }

    private void mainLoop(){
        try {
            while (true){
                Thread.sleep(200);
                repaintPlane();
                repaintSnake();
            }
        } catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void repaintPlane(){
        for (int y = 0; y < Fields.length; y++){
            for (int x = 0; x < Fields[y].length; x++){
                if(!new Point(x, y).equals(Food)) Fields[y][x].setBackground(Color.BLACK);
            }
        }
    }

    private void repaintSnake(){
        switch (Direction){
            case 'u':
                SnakeHead = new Point(SnakeHead.x, SnakeHead.y-1);
                break;
            case 'd':
                SnakeHead = new Point(SnakeHead.x, SnakeHead.y+1);
                break;
            case 'l':
                SnakeHead = new Point(SnakeHead.x-1, SnakeHead.y);
                break;
            case 'r':
                SnakeHead = new Point(SnakeHead.x+1, SnakeHead.y);
                break;
        }
        SnakePath.add((Point)SnakeHead.clone());
        try{
            Fields[SnakeHead.y][SnakeHead.x].setBackground(Color.WHITE);
            for (int i = 1; i <= SnakeSize; i++){
                Fields[SnakePath.get((SnakePath.size()-1)-i).y][SnakePath.get((SnakePath.size()-1)-i).x].setBackground(Color.WHITE);
            }
        }
        catch (Exception e){ System.exit(0);}
        if (Food.equals(SnakeHead)) {
            SnakeSize++;
            setFood();
        }
    }

    private void setFields(){
        for (int y = 0; y < Fields.length; y++) {
            for (int x = 0; x < Fields[y].length; x++) {
                Fields[y][x] = new JButton();
                Fields[y][x].setBackground(Color.BLACK);
                Frame.add(Fields[y][x]);
            }
        }
    }

    private void setFood(){
        Food = new Point(generateRandomInteger(0, Fields.length-1), generateRandomInteger(0, Fields.length-1));
        Fields[Food.y][Food.x].setBackground(Color.YELLOW);
    }

    private void setSnake(){
        SnakePath.add(new Point(0,0));
        SnakeHead = SnakePath.get(0);
        Fields[SnakeHead.y][SnakeHead.x].setBackground(Color.WHITE);
    }

    private int generateRandomInteger(int min, int max) {
        SecureRandom rand = new SecureRandom();
        rand.setSeed(new Date().getTime());
        return rand.nextInt((max - min) + 1) + min;
    }

}
