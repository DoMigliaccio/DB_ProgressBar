package com.example.db_progressbar.progressBar;

import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;



public class DragonBallApplicationComponent implements LafManagerListener, DynamicPluginListener, ApplicationActivationListener {
    private static final String UI_CLASS_NAME = DragonBallProgressBarUi.class.getName();
    public DragonBallApplicationComponent() {
        updateProgressBarUI();
    }

    @Override
    public void lookAndFeelChanged(@NotNull LafManager lafManager) {
        updateProgressBarUI();
    }

    private static void updateProgressBarUI() {
        UIManager.put("ProgressBarUI", UI_CLASS_NAME);
        UIManager.getDefaults().put(UI_CLASS_NAME, DragonBallProgressBarUi.class);
        //System.out.println("**V4_ApplicationComponent() -> updateProgressBarUI -> UIManager.getDefaults().put()** " + UIManager.getDefaults().put(DragonBallProgressBarUi.class.getName(), DragonBallProgressBarUi.class));
    }

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        updateProgressBarUI();
    }

    @Override
    public void pluginLoaded(@NotNull IdeaPluginDescriptor ideaPluginDescriptor) {
        updateProgressBarUI();
    }
}