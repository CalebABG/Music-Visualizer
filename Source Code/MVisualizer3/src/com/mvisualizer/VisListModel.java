package com.mvisualizer;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.*;

public class VisListModel extends DefaultListModel<MSong> {

    public VisListModel() { }

    public void swap(int index1from, int index2to) {
        if (index1from != index2to) {
            MSong item1 = get(index1from);
            MSong item2 = get(index2to);
            set(index1from, item2);
            set(index2to, item1);
        }
    }

    public MSong poll() { return remove(0); }
}
