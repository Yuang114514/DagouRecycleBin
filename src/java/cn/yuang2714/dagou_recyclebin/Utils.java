package cn.yuang2714.dagou_recyclebin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Utils {
    private static final StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final Desktop desktop;
    static {
        if (!Desktop.isDesktopSupported()) {
            logln("Desktop not supported");
            System.exit(1);
        }
        desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.MOVE_TO_TRASH)) {
            logln("Move to trash not supported");
            System.exit(1);
        }
    }
    
    public static void logln(Object msg) {
        IO.println(
                "[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]" +
                " [" + Thread.currentThread().getName() + "]" +
                " (" + walker.getCallerClass().getSimpleName() + ")" +
                " " + msg
        );
    }
    
    public static void deleteAsync(List<File> files, Dagou dagou) {
        new Thread(() -> {
            for (File file : files) {
                if (! desktop.moveToTrash(file)) {
                    logln("Failed to move " + file.getAbsolutePath() + "to trash.");
                } else logln("Deleted file:" + file.getAbsolutePath());
            }
            dagou.idle();
            AudioHandler.instance.stop();
        }, "File deletion Thread").start();
    }
    
    public static void genFile(String directory, int count) {
        File dir = new File(directory);
        
        for (int i = 0; i < count; i++) {
            File f = new File(dir, String.valueOf(i + 1));
            try {
                f.createNewFile();
            } catch (IOException _) {}
        }
    }
    
    void main() {
        genFile("C:\\Users\\9b1d_8c6d_9dac_9dac\\Desktop\\tmp\\rec1", 5000);
    }
}
