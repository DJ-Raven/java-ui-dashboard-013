package com.raven.menu;

import java.awt.Component;

public interface EventMenu {

    public void mainMenuSelected(MainForm mainForm, int index, MenuItem menuItem);

    public void subMenuSelected(MainForm mainForm, int index, int subMenuIndex, Component menuItem);
}
