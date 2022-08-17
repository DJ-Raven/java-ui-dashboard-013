package com.raven.menu;

import javax.swing.Icon;

public class ModelSubMenu {

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelSubMenu(Icon icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public ModelSubMenu() {
    }

    private Icon icon;
    private String name;
}
