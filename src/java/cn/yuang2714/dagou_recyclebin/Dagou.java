package cn.yuang2714.dagou_recyclebin;

import javax.swing.*;
import java.awt.dnd.DropTarget;
import java.util.Objects;

public class Dagou extends JPanel {
    private final JLabel image;
    private static final ImageIcon idle = new ImageIcon(Objects.requireNonNull(Dagou.class.getResource("/idle.png")));
    private static final ImageIcon ready = new ImageIcon(Objects.requireNonNull(Dagou.class.getResource("/ready.png")));
    private static final ImageIcon bark = new ImageIcon(Objects.requireNonNull(Dagou.class.getResource("/bark.png")));
    public void idle() { setImage(idle); }
    public void ready() { setImage(ready); }
    public void bark() { setImage(bark); }
    private void setImage(Icon icon) {
        Utils.logln("Setting icon to " + icon);
        image.setIcon(icon);
        repaint();
    }
    
    public Dagou() {
        DagouDraggyMouseHandler.setDagou(this);
        setOpaque(false);
        
        image = new JLabel(idle);
        image.addMouseListener(new DagouDraggyMouseHandler.DagouMouseAdapter());
        image.addMouseMotionListener(new DagouDraggyMouseHandler.DagouMouseMotionAdapter());
        add(image);
        
        new DropTarget(this, new DagouDropTargetAdapter(this));
    }
}
