package com.raven.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.ListModel;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class Menu extends JComponent {

    public MainForm getMainForm() {
        return mainForm;
    }

    public void setMainForm(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    public SubMenuPanel getSubMenuPanel() {
        return subMenuPanel;
    }

    public void setSubMenuPanel(SubMenuPanel subMenuPanel) {
        this.subMenuPanel = subMenuPanel;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    private float opacity = 0.2f;
    private SubMenuPanel subMenuPanel;
    private MainForm mainForm;
    private SubMenuItemRender subMenuItemRender = new DefaultSubMenuItemRender();
    private MenuAnimation animator;
    private int selectedIndex = -1;
    private List<EventMenu> events = new ArrayList<>();

    public Menu() {
        setLayout(new MigLayout("wrap, inset 10 5 80 5, fillx", "fill", "[]70[]20[]"));
        animator = new MenuAnimation(this);
        initStyle();
    }

    private void initStyle() {
        Color color = UIManager.getColor("raven.menu.color");
        if (color != null) {
            setForeground(color);
        } else {
            setForeground(Color.WHITE);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        initStyle();
    }

    public void addEventMenuSelected(EventMenu event) {
        events.add(event);
    }

    private void runEventMainMenu(int index, MenuItem menuItem) {
        for (EventMenu event : events) {
            event.mainMenuSelected(mainForm, index, menuItem);
        }
    }

    private void runEventSubMenu(int index, int subMenuIndex, Component subMenuItem) {
        for (EventMenu event : events) {
            event.subMenuSelected(mainForm, index, subMenuIndex, subMenuItem);
        }
    }

    @Override
    public Component add(Component comp) {
        applyEvent(comp);
        return super.add(comp);
    }

    private void applyEvent(Component com) {
        if (com instanceof JButton) {
            JButton cmd = (JButton) com;
            cmd.addActionListener(e -> {
                setSelectedIndex(getIndexOf(com));
            });
        }
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && selectedIndex != index) {
            if (subMenuPanel != null) {
                subMenuPanel.removeAll();
                Component com = getComponent(index + 1);
                if (com instanceof MenuItem) {
                    MenuItem item = (MenuItem) com;
                    ListModel model = item.getMenuModel();
                    ListModel icon = item.getMenuIcon();
                    if (model != null) {
                        for (int i = 0; i < model.getSize(); i++) {
                            ModelSubMenu data = new ModelSubMenu(getMenuIcon(icon, i), model.getElementAt(i).toString());
                            Component c = subMenuItemRender.getSubMenuItemreder(this, data);
                            if (c instanceof JButton) {
                                ((JButton) c).addActionListener((ActionEvent ae) -> {
                                    runEventSubMenu(getIndexOf(com), subMenuPanel.getComponentZOrder(c), c);
                                });
                            }
                            subMenuPanel.add(c);
                        }
                    }
                    selectedIndex = index;
                    runEventMainMenu(index, item);
                    animator.change(item);
                }
                subMenuPanel.revalidate();
                subMenuPanel.repaint();
            }

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        if (getComponentCount() > 1) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g2.setColor(getForeground());
            g2.fill(createStyle());
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            float x = getWidth() - 3;
            float y = animator.getLocation();
            float height = animator.getItemSize();
            g2.setPaint(new GradientPaint(x, y, animator.getSelectedColor1(), x + 3, y + height, animator.getSelectedColor2()));
            g2.fill(new RoundRectangle2D.Double(x, y, 3, height, 5, 5));
            g2.dispose();
        }
        super.paintComponent(g);
    }

    private Shape createStyle() {
        int x = 2;
        int y = getMenuFirstItem() - 80;
        int width = getWidth();
        int space = 30;
        int lastItemLocation = getMenuItemLastLocation();
        GeneralPath p = new GeneralPath(new CubicCurve2D.Double(width, y, width, y + 50, x, y + space, x, y + 50 + space));
        p.lineTo(x, lastItemLocation);
        p.append(new CubicCurve2D.Double(x, lastItemLocation, x, lastItemLocation + 50, width, lastItemLocation + space, width, lastItemLocation + 50 + space), true);
        return p;
    }

    private Icon getMenuIcon(ListModel<String> data, int index) {
        if (data != null && index < data.getSize()) {
            return new ImageIcon(getClass().getResource("/" + data.getElementAt(index)));
        } else {
            return null;
        }
    }

    private int getMenuFirstItem() {
        for (Component com : getComponents()) {
            if (com instanceof JButton) {
                return com.getY();
            }
        }
        return 0;
    }

    private int getMenuItemLastLocation() {
        Component com = getComponent(getComponentCount() - 1);
        return com.getY() + com.getHeight();
    }

    private int getIndexOf(Component com) {
        return getComponentZOrder(com) - 1;
    }

    public SubMenuItemRender getSubMenuItemRender() {
        return subMenuItemRender;
    }

    public void setSubMenuItemRender(SubMenuItemRender subMenuItemRender) {
        this.subMenuItemRender = subMenuItemRender;
    }
}
