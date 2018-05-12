package com.mvisualizer;

import processing.core.PApplet;

// TODO: 3/24/2018 Look into why occasionally closing program stalls and never closes Minim
// TODO: 3/24/2018 Fix Pause and Mute controls - captions do not change on key press if changed previously by click
// TODO: 3/24/2018 Clean code for the ListModel and Music List GUI as well as Control.kt file

public class Main {
    static {
        System.setProperty("sun.java2d.transaccel", "true");
        System.setProperty("sun.java2d.ddforcevram", "true");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public static void main(String[] args){
        PApplet.main(Visualizer.class);
    }
}
