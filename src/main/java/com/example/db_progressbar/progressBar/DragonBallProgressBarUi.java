package com.example.db_progressbar.progressBar;

import com.intellij.openapi.ui.GraphicsConfig;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.util.ui.GraphicsUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import com.example.db_progressbar.configuration.DragonBallProgressState;


import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DragonBallProgressBarUi extends BasicProgressBarUI {

    ImageIcon selectedIconLeft;
    ImageIcon selectedIconRight;

    ImageIcon selectedOnda = Icons.Onda;
    ImageIcon onda_Freezer = Icons.Onda_Freezer;
    ImageIcon onda_yellow = Icons.Onda_Yellow;


    ImageIcon selectedOndaR = Icons.OndaRev;
    ImageIcon ondaR_Freezer = Icons.OndaRev_Freezer;
    ImageIcon ondaR_yellow = Icons.OndaRev_Yellow;



    public static final Color TRANSPARENT = new Color (0,0,0,0);


    int i = 0;

    public DragonBallProgressBarUi(){
        //System.out.println("**Costruttore base **");
    }


    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        //System.out.println("**DragonBallProgressBarUi.createUI**");
        c.setBorder(JBUI.Borders.empty().asUIResource());
        return new DragonBallProgressBarUi();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        //System.out.println("getpreferredSize Height "+DragonBallProgressState.getInstance().getHeight());
        //var state = DragonBallProgressState.getInstance().getState();
        return new Dimension(super.getPreferredSize(c).width, JBUIScale.scale(DragonBallProgressState.getInstance().getHeight())); //DragonBallProgressState.getInstance().getHeight()
    }


    @Override
    protected void installListeners() {
        super.installListeners();
        progressBar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
            }
        });
    }

    private volatile int offset = 0;
    private volatile int offset2 = 0;
    private volatile int velocity = 1;

    @Override
    protected void paintIndeterminate(Graphics g2d, JComponent c) {
        if (!(g2d instanceof Graphics2D)) {
            return;
        }
        Graphics2D g = (Graphics2D) g2d;
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        //g.setColor(new JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50)));
        g.setColor(TRANSPARENT);
        int w = c.getWidth();
        int h = c.getPreferredSize().height;
        if (isNotEven(c.getHeight() - h)) h++;
        if (c.isOpaque()) {
            g.fillRect(0, (c.getHeight() - h) / 2, w, h);
        }
        //g.setColor(new JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50)));
        g.setColor(TRANSPARENT);
        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        g.translate(0, (c.getHeight() - h) / 2);

        final float R = JBUIScale.scale(8);
        final float R2 = JBUIScale.scale(9);
        final Area containingRoundRect = new Area(new RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R));
        g.fill(containingRoundRect);
        offset = (offset + 1) % getPeriodLength();
        offset2 += velocity;
        if (offset2 <= 2) {
            offset2 = 2;
            velocity = 1;
        } else if (offset2 >= w - JBUIScale.scale(15)) {
            offset2 = w - JBUIScale.scale(15);
            velocity = -1;
        }
        Area area = new Area(new Rectangle2D.Float(0, 0, w, h));
        area.subtract(new Area(new RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R)));
        if (c.isOpaque()) {
            g.fill(area);
        }

        area.subtract(new Area(new RoundRectangle2D.Float(0, 0, w, h, R2, R2)));

        if (c.isOpaque()) {
            g.fill(area);
        }

        RoundRectangle2D rectangle = new RoundRectangle2D.Float(1f, 1f, w - 2f - 1f, h - 2f - 1f, R, R);
        //g.draw(new RoundRectangle2D.Float(1f, 1f, w - 2f - 1f, h - 2f - 1f, R, R));
        g.draw(rectangle);
        drawBorder(rectangle,g);
        g.translate(0, -(c.getHeight() - h) / 2);

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, boxRect.x, boxRect.width);
            } else {
                paintString(g, b.left, b.top, barRectWidth, barRectHeight, boxRect.y, boxRect.height);
            }
        }

        //IconsSection
        var state = DragonBallProgressState.getInstance().getState();

        ArrayList<ImageIcon> fighterOnda = generaOndaIco(state.getPathLeftIco());

        ImageIcon scaledIcon = velocity > 0 ? (fighterOnda.get(0)) : (fighterOnda.get(1));
        scaledIcon.paintIcon(progressBar, g, offset2 - JBUI.scale(16), -JBUI.scale(4));

        selectedIconLeft = generaIco(state.getPathLeftIco(), 'l');
        selectedIconRight = generaIco(state.getPathRightIco(), 'r');
        if(state.getPathRightIco().contains("MajinBu_v1")){
            selectedIconRight.paintIcon(progressBar, g, barRectWidth - JBUI.scale(58), -2);
        }else if(state.getPathRightIco().contains("Vegito_v2")){
            selectedIconRight.paintIcon(progressBar, g, barRectWidth - JBUI.scale(35), -2);
        }else{
            selectedIconRight.paintIcon(progressBar, g, barRectWidth - JBUI.scale(30), -2);
        }

        selectedIconLeft.paintIcon(progressBar, g, -JBUI.scale(1), -2);


        config.restore();
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        if (progressBar.getOrientation() != SwingConstants.HORIZONTAL || !c.getComponentOrientation().isLeftToRight()) {
            super.paintDeterminate(g, c);
            return;
        }
        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        Insets b = progressBar.getInsets(); // area for border
        int w = progressBar.getWidth();
        int h = progressBar.getPreferredSize().height;
        if (isNotEven(c.getHeight() - h)) h++;

        int barRectWidth = w - (b.right + b.left);
        int barRectHeight = h - (b.top + b.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        Container parent = c.getParent();
        Color background = parent != null ? parent.getBackground() : UIUtil.getPanelBackground();

        g.setColor(background);
        Graphics2D g2 = (Graphics2D) g;
        if (c.isOpaque()) {
            g.fillRect(0, 0, w, h);
        }

        final float R = JBUIScale.scale(8);
        final float R2 = JBUIScale.scale(9);
        final float off = JBUIScale.scale(1);

        //background
        g2.translate(0, (c.getHeight() - h) / 2);
        g2.setColor(progressBar.getForeground());
        g2.fill(new RoundRectangle2D.Float(0, 0, w - off, h - off, R2, R2));
        g2.setColor(background);
        g2.fill(new RoundRectangle2D.Float(off, off, w - 2f * off - off, h - 2f * off - off, R, R));

        g2.translate(0, -(c.getHeight() - h) / 2);

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top,
                    barRectWidth, barRectHeight,
                    amountFull, b);
        }

        //Icons Section
        try {
            var state = DragonBallProgressState.getInstance().getState();

            //Expected -> Sprites//[LeftFighter]_MajinBu_v3.gif
            ArrayList<ImageIcon> fighterOnda = generaOndaIco(state.getPathLeftIco());
            //Prendo l'elemento 0 perchè è quello che va da destra a sinistra
            fighterOnda.get(0).paintIcon(progressBar,g2, amountFull - JBUI.scale(1), -JBUI.scale(4));

            selectedIconLeft = generaIco(state.getPathLeftIco(),'l');
            selectedIconRight = generaIco(state.getPathRightIco(),'r');
            if(state.getPathRightIco().contains("MajinBu_v1")){
                selectedIconRight.paintIcon(progressBar, g, barRectWidth - JBUI.scale(58), -2);
            }else if(state.getPathRightIco().contains("Vegito_v2")){
                selectedIconRight.paintIcon(progressBar, g, barRectWidth - JBUI.scale(35), -2);
            }else{
                selectedIconRight.paintIcon(progressBar, g, barRectWidth - JBUI.scale(30), -2);
            }
            selectedIconLeft.paintIcon(progressBar, g, -JBUI.scale(1), -2);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        config.restore();
    }

    private void paintString(Graphics g, int x, int y, int w, int h, int fillStart, int amountFull) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        String progressString = progressBar.getString();
        g2.setFont(progressBar.getFont());
        Point renderLocation = getStringPlacement(g2, progressString,
                x, y, w, h);
        Rectangle oldClip = g2.getClipBounds();

        if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
            g2.setColor(getSelectionBackground());
            BasicGraphicsUtils.drawString(progressBar, g2, progressString, renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(fillStart, y, amountFull, h);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString, renderLocation.x, renderLocation.y);
        } else { // VERTICAL
            g2.setColor(getSelectionBackground());
            AffineTransform rotate =
                    AffineTransform.getRotateInstance(Math.PI / 2);
            g2.setFont(progressBar.getFont().deriveFont(rotate));
            renderLocation = getStringPlacement(g2, progressString,
                    x, y, w, h);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString, renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(x, fillStart, w, amountFull);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString, renderLocation.x, renderLocation.y);
        }
        g2.setClip(oldClip);
    }


    private static boolean isNotEven(int value) {
        return !(value % 2 == 0);
    }

    protected int getPeriodLength() {
        return JBUI.scale(16);
    }

    public ImageIcon generaIco(String path, char side){
        try{
            ImageIcon Icon = new ImageIcon(Icons.class.getClassLoader().getResource(path));
            return Icon;
        }catch(Exception e){
            System.out.println("DBUI-generaIco - exception: "+e.getMessage());
            System.out.println("generaIco - setto default: "+e.getMessage());
            if(side=='l'){
                path = Icons.basePath+"[LeftFighter]_Goku_v1.png";
            }else if(side == 'r'){
                path=Icons.basePath+"[RightFighter]_Vegeta_v1.gif";
            }
            ImageIcon Icon = new ImageIcon(Icons.class.getClassLoader().getResource(path));
            return Icon;

        }
    }


    public ArrayList<ImageIcon> generaOndaIco(String leftFighterName){
        ArrayList<ImageIcon> ondaIconList = new ArrayList<ImageIcon>();
        try{

            if(leftFighterName.contains("Vegeta_v5") ||
                    leftFighterName.contains("Trunks_v1")||
                    leftFighterName.contains("C19_v1")||
                    leftFighterName.contains("C20_v1")||
                    leftFighterName.contains("Radish_v1")||
                    leftFighterName.contains("Vegito_v2")||
                    leftFighterName.contains("Nappa_v1")||
                    leftFighterName.contains("Tensing_v1")){
                ondaIconList.add(Icons.Onda_Yellow);
                ondaIconList.add(Icons.OndaRev_Yellow);

            }else if(leftFighterName.contains("Beerus_v1") ){
                ondaIconList.add(Icons.Onda_Beerus);
                ondaIconList.add(Icons.Onda_Beerus);

            }else if(leftFighterName.contains("Jiren")){
                ondaIconList.add(Icons.Onda_Red);
                ondaIconList.add(Icons.OndaRev_Red);

            }else if(leftFighterName.contains("Freezer") ||
                        leftFighterName.contains("MajinBu_v2")||
                        leftFighterName.contains("MajinBu_v5")||
                        leftFighterName.contains("Shenron_v1")){
                ondaIconList.add(Icons.Onda_Freezer);
                ondaIconList.add(Icons.OndaRev_Freezer);

            }else if(leftFighterName.contains("Vegeta_v4")){
                ondaIconList.add(Icons.Onda_Purple);
                ondaIconList.add(Icons.OndaRev_Purple);

            }else if(leftFighterName.contains("Gotenks_v1")){
                ondaIconList.add(Icons.Onda_Gotenks);
                ondaIconList.add(Icons.OndaRev_Empty);

            }else if(leftFighterName.contains("Granolah_v1")){
                ondaIconList.add(Icons.Onda_Green);
                ondaIconList.add(Icons.OndaRev_Green);

            }else if(leftFighterName.contains("MajinBu_v1")){
                ondaIconList.add(Icons.Onda_Raggio);
                ondaIconList.add(Icons.OndaRev_Empty);

            }else if(leftFighterName.contains("C16_v1")){
                ondaIconList.add(Icons.Onda_C16);
                ondaIconList.add(Icons.OndaRev_Empty);
            }else if(leftFighterName.contains("Junior_v1")){
                ondaIconList.add(Icons.Onda_Junior);
                ondaIconList.add(Icons.OndaRev_Empty);
            }else{
                ondaIconList.add(Icons.Onda);
                ondaIconList.add(Icons.OndaRev);
            }
        }catch(Exception e){
            //Get Default onda
            System.out.println("GeneraOndaICO Exception - "+e.getMessage());
            ondaIconList.add(Icons.Onda);
            ondaIconList.add(Icons.OndaRev);

        }
        return ondaIconList;
    }

    private void drawBorder(final RoundRectangle2D rectangle, final Graphics2D graphics2D) {
        final Color color = graphics2D.getColor();
        final Stroke stroke = graphics2D.getStroke();

        graphics2D.setColor(progressBar.getForeground());
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.draw(rectangle);

        graphics2D.setColor(color);
        graphics2D.setStroke(stroke);
    }

    //TODO - mappa che prende già l'ico dell'onda.
    //Un getter - setter dell'onda già nel 'State'
    //Deve seguire il giro del selectedLeft e SelectedRight



}
