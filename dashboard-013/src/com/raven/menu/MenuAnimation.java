package com.raven.menu;

import java.awt.Color;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class MenuAnimation {

    public Color getSelectedColor1() {
        return selectedColor1;
    }

    public void setSelectedColor1(Color selectedColor1) {
        this.selectedColor1 = selectedColor1;
    }

    public Color getSelectedColor2() {
        return selectedColor2;
    }

    public void setSelectedColor2(Color selectedColor2) {
        this.selectedColor2 = selectedColor2;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public float getLocation() {
        return location;
    }

    public void setLocation(float location) {
        this.location = location;
        menu.repaint();
    }

    private final Animator animator;
    private final Menu menu;
    private TimingTarget target1;
    private TimingTarget target2;
    private TimingTarget targetSize;
    private TimingTarget targetLocation;
    private Color selectedColor1 = new Color(255, 255, 255, 0);
    private Color selectedColor2 = new Color(255, 255, 255, 0);
    private int itemSize = -1;
    private float location = -1;

    public MenuAnimation(Menu menu) {
        this.menu = menu;
        animator = new Animator(350);
        animator.setResolution(5);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
    }

    public void change(MenuItem item) {
        if (animator.isRunning()) {
            animator.stop();
        }
        if (location == -1) {
            location = item.getY();
            itemSize = item.getHeight();
        }
        animator.removeTarget(target1);
        animator.removeTarget(target2);
        animator.removeTarget(targetSize);
        animator.removeTarget(targetLocation);
        target1 = new PropertySetter(this, "selectedColor1", selectedColor1, item.getBackground());
        target2 = new PropertySetter(this, "selectedColor2", selectedColor2, item.getForeground());
        targetSize = new PropertySetter(this, "itemSize", itemSize, item.getHeight());
        targetLocation = new PropertySetter(this, "location", location, (float) item.getY());
        animator.addTarget(target1);
        animator.addTarget(target2);
        animator.addTarget(targetSize);
        animator.addTarget(targetLocation);
        animator.start();
    }
}
