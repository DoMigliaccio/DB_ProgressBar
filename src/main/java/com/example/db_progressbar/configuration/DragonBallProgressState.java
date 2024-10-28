package com.example.db_progressbar.configuration;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.example.db_progressbar.progressBar.Icons;

import javax.swing.*;


@State(
        name = "configuration.DragonBallProgressState",
        storages = {@Storage("SdkSettingPluginProgressBar.xml")}
)

@SuppressWarnings("MissingRecentApi")
public class DragonBallProgressState implements PersistentStateComponent<DragonBallProgressState> {

    //questi sono i valori di default
    //Valori di default.
    private  Integer height = 22;

    private String pathLeftIco = Icons.basePath+"[LeftFighter]_Goku_v1.png";
    private String pathRightIco = Icons.basePath+"[RightFighter]_Vegeta_v1.gif";

    private String pathOndaIco = Icons.basePath+"OndaGapEnanched.gif";
    private String pathOndaRevIco = Icons.basePath+"OndaGapREnanched.gif";

    private Boolean isRandom = true;

    public DragonBallProgressState() {
    }

    public String getPathLeftIco() {        //NEW
        return pathLeftIco;
    }

    public void setPathLeftIco(String pathLeftIco) {  //NEW
        this.pathLeftIco = pathLeftIco;
    }

    public String getPathRightIco() {
        return pathRightIco;
    }

    public void setPathRightIco(String pathRightIco) {
        this.pathRightIco = pathRightIco;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getIsRandom() {
        return this.isRandom;
    }

    public void setIsRandom(Boolean random) {
        this.isRandom = random;
    }

    public static DragonBallProgressState getInstance() {
        try{
            return ApplicationManager.getApplication().getService(DragonBallProgressState.class);
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return ApplicationManager.getApplication().getService(DragonBallProgressState.class);
    }

    @Nullable
    @Override
    public DragonBallProgressState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DragonBallProgressState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
