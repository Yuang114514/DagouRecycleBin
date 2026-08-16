package cn.yuang2714.dagou_recyclebin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DagouDraggyMouseHandler {
    private static boolean isDragging;
    private static JFrame parent;
    private static Dagou dagou;
    
    private static int offsetX;
    private static int offsetY;
    
    public static class DagouMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed (MouseEvent e) {
            isDragging = true;
            Point loc = parent.getLocationOnScreen();
            offsetX = e.getXOnScreen() - loc.x;
            offsetY = e.getYOnScreen() - loc.y;
            dagou.ready();
            AudioHandler.instance.playDagou();
            Utils.logln("Mouse pressed. offsetX:" + offsetX + ", offsetY:" + offsetY);
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            isDragging = false;
            dagou.idle();
            AudioHandler.instance.stop();
            Utils.logln("Mouse released.");
        }
    }
    
    public static class DagouMouseMotionAdapter extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (!isDragging) return;
            int newX = e.getXOnScreen() - offsetX;
            int newY = e.getYOnScreen() - offsetY;
            parent.setLocation(newX, newY);
        }
    }
    
    public static void setParent(JFrame parent1) {
        parent = parent1;
    }
    public static void setDagou(Dagou dagou1) {
        dagou = dagou1;
    }
}
