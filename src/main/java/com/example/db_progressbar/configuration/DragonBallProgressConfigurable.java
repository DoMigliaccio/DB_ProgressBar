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
        boolean isModified = ((component.getSelectedHeight()!= state.getHeight()) || component.getPathLeftIco() != state.getPathLeftIco() || component.getPathRightIco() != state.getPathRightIco());
        return isModified;
    }

    @Override
    public void apply() {
        DragonBallProgressState state = DragonBallProgressState.getInstance();

        System.out.println("component.getselectedHeight(): "+component.getSelectedHeight());

        System.out.println("component.getPathLeftIco(): "+component.getPathLeftIco());
        System.out.println("component.getPathRightIco(): "+component.getPathRightIco());
        state.setHeight(component.getSelectedHeight());

        state.setPathLeftIco(component.getPathLeftIco());
        state.setPathRightIco(component.getPathRightIco());
    }

    @Override
    public void reset() {
        DragonBallProgressState state = DragonBallProgressState.getInstance();
        component.setSelectedHeight(state.getHeight());
        component.setPathLeftIco(state.getPathLeftIco());
        component.setPathRightIco(state.getPathRightIco());
    }



}
