package com.example.db_progressbar.configuration;

import com.intellij.openapi.options.Configurable;

import javax.swing.*;

public class DragonBallProgressConfigurable implements Configurable {
    private DragonBallProgressConfigurationComponent component;

    @Override
    public String getDisplayName() {
        return "DragonBall progressbar settings";
    }

    @Override
    public JComponent createComponent() {
        component = new DragonBallProgressConfigurationComponent();
        return component.getPanel();
    }


    @Override
    public boolean isModified() {
        final DragonBallProgressState state = DragonBallProgressState.getInstance();
        //boolean modified = !(component.getHeightSlider()!= state.getHeight());
        //boolean modifiedTest = (component.getHeightSlider()!= state.getHeight()) ? true: false;

        //boolean modifiedTest1 = ((component.getSelectedHeight()!= state.getHeight()) || (component.getRadioLFighter() != state.getLeftFighter()) || (component.getRadioRFighter() != state.getRightFighter()));
        //boolean modifiedTest2 = (component.isChangedLeft() || component.isChangedRight() || component.isChangedSlider());
        boolean isModified = ((component.getSelectedHeight()!= state.getHeight()) || component.getPathLeftIco() != state.getPathLeftIco() || component.getPathRightIco() != state.getPathRightIco());
        //System.out.println("**IsModifiedTest** "+modifiedTest);
        //System.out.println("**modifiedTest1** "+modifiedTest1);
        //System.out.println("**modifiedTest2** "+modifiedTest2);
        //System.out.println("**modifiedTest2** "+isModified);
        return isModified;
    }

    @Override
    public void apply() {
        //System.out.println("[Configurable-apply()]");
        DragonBallProgressState state = DragonBallProgressState.getInstance();
        //state.height = component.getHeight();
        //System.out.println("[Configurable-apply()] state.getState() BEFORE ="+state.getState().getHeight());
        //System.out.println("[Configurable-apply()] component.getHeight() ="+component.getHeightSlider());
        System.out.println("component.getselectedHeight(): "+component.getSelectedHeight());
        //System.out.println("component.getRadioLFighter(): "+component.getRadioLFighter());
        //System.out.println("component.getRadioRFighter(): "+component.getRadioRFighter());
        System.out.println("component.getPathLeftIco(): "+component.getPathLeftIco());
        System.out.println("component.getPathRightIco(): "+component.getPathRightIco());
        state.setHeight(component.getSelectedHeight());
        //state.setLeftFighter(component.getRadioLFighter());
        //state.setRightFighter(component.getRadioRFighter());

        state.setPathLeftIco(component.getPathLeftIco());
        state.setPathRightIco(component.getPathRightIco());

        //state.setTitoloTest(Integer.toString(component.getSelectedHeight()));

    }

    @Override
    public void reset() {
        DragonBallProgressState state = DragonBallProgressState.getInstance();
        component.setSelectedHeight(state.getHeight());
        component.setPathLeftIco(state.getPathLeftIco());
        component.setPathRightIco(state.getPathRightIco());
    }



}
