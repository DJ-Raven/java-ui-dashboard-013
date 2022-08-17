package com.raven.menu;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

public class SubMenuPanel extends JComponent {

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    private int round = 10;
    private float opacity = 0.15f;

    public SubMenuPanel() {
        setLayout(new MigLayout("wrap, inset 0, gap 0, fillx", "fill", "[fill, 35, shrink 0]"));
        initStyle();
    }

    private void initStyle() {
        Color color = UIManager.getColor("raven.submenu.color");
        if (color != null) {
            setBackground(color);
        } else {
            setBackground(Color.WHITE);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        initStyle();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), round, round));
        g2.dispose();
        super.paintComponent(g);
    }
}
