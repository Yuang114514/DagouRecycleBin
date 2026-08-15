package cn.yuang2714.dagou_recyclebin;

import javax.swing.*;
import java.awt.*;

public class DagouRecycleBin extends JFrame {
    public DagouRecycleBin() {
        //基本设置
        setTitle("大狗回收站");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //透明背景
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        
        
    }
}
