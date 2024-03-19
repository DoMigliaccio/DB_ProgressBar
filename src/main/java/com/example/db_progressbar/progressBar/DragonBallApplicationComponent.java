package com.example.db_progressbar.progressBar;

import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;



@SuppressWarnings("MissingRecentApi")
public class DragonBallApplicationComponent implements LafManagerListener {

    public DragonBallApplicationComponent() {
        updateProgressBarUI();
    }

    @Override
    public void lookAndFeelChanged(@NotNull LafManager lafManager) {
        updateProgressBarUI();
    }

    private static void updateProgressBarUI() {
        UIManager.put("ProgressBarUI", DragonBallProgressBarUi.class.getName());
        UIManager.getDefaults().put(DragonBallProgressBarUi.class.getName(), DragonBallProgressBarUi.class);
        //System.out.println("**V4_ApplicationComponent() -> updateProgressBarUI -> UIManager.getDefaults().put()** " + UIManager.getDefaults().put(DragonBallProgressBarUi.class.getName(), DragonBallProgressBarUi.class));
    }
}