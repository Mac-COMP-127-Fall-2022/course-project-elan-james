package pinball;

import edu.macalester.graphics.CanvasWindow;

public class Pinball {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    CanvasWindow canvas;
    
    public Pinball() {
        canvas = new CanvasWindow("Pinball", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void print(String string){
        System.out.print(string);
    }

    public static void main(String[] args) {
        new Pinball();
    }
}
