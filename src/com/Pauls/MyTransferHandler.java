package com.Pauls;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;

public class MyTransferHandler extends TransferHandler {
    public boolean canImport(TransferHandler.TransferSupport info) {
        info.setShowDropLocation(true);
            if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }

            // fetch the drop location
        JTree.DropLocation dl = (JTree.DropLocation)info.getDropLocation();

        TreePath path = dl.getPath();
        return path != null;
    }

    public boolean importData(TransferHandler.TransferSupport info) {
        return true;
    }

}
