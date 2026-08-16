package cn.yuang2714.dagou_recyclebin;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;

public class DagouDropTargetAdapter extends DropTargetAdapter {
    private final Dagou dagou;
    
    @Override
    public synchronized void drop(DropTargetDropEvent event) {
        try {
            event.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = event.getTransferable();
            
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                @SuppressWarnings("unchecked")
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                
                AudioHandler.instance.playBark();
                dagou.bark();
                Utils.deleteAsync(files, dagou);
                event.dropComplete(true);
            } else event.dropComplete(false);
            Utils.logln(event);
        } catch (Exception e) {
            Utils.logln(e);
        }
    }
    
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        Utils.logln("Drag entered");
        AudioHandler.instance.playDagou();
        dagou.ready();
    }
    
    @Override
    public void dragExit(DropTargetEvent dte) {
        Utils.logln("Drag exited");
        dagou.idle();
    }
    
    public DagouDropTargetAdapter(Dagou dagou) { this.dagou = dagou; }
}
