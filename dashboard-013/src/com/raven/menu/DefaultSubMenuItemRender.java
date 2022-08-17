package com.raven.menu;

import java.awt.Component;

public class DefaultSubMenuItemRender implements SubMenuItemRender {

    @Override
    public Component getSubMenuItemreder(Menu menu, ModelSubMenu data) {
        SubMenuItem item = new SubMenuItem(data.getName());
        if (data.getIcon() != null) {
            item.setIcon(data.getIcon());
        }
        return item;
    }
}
