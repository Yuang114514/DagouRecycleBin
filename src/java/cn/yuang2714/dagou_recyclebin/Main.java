import cn.yuang2714.dagou_recyclebin.DagouRecycleBin;

import javax.swing.*;

void main() {
    SwingUtilities.invokeLater(
            () -> {
                try {
                    new DagouRecycleBin();
                } catch (Exception e) {
                    IO.println(e);
                }
            }
    );
}