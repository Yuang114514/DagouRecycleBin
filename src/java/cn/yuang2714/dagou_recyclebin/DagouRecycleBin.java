package cn.yuang2714.dagou_recyclebin;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class DagouRecycleBin {
    
    public DagouRecycleBin() throws Exception {
        JFrame frame = new JFrame();
        //基础设置
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setSize(163,207);
        frame.setLocationRelativeTo(null);
        
        //透明背景
        frame.setUndecorated(true);
        frame.setBackground(new Color(0,0,0,0));
        
        frame.add(new Dagou());
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(Dagou.class.getResource("/idle.png"))).getImage());
        
        //启动音频
        AudioHandler.init();
        
        //启动鼠标监听器
        DagouDraggyMouseHandler.setParent(frame);
        
        Utils.logln("Instance created");
    }
}
