package com.example.db_progressbar.configuration;

import com.intellij.openapi.ui.LabeledComponent;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.example.db_progressbar.progressBar.DragonBallProgressBarUi;
import com.example.db_progressbar.progressBar.Icons;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

public class DragonBallProgressConfigurationComponent {
    private JPanel mainPanel;
    final JProgressBar determinateProgressBar = new JProgressBar();
    private DragonBallProgressBarUi determinateUi;
    final JProgressBar indeterminateProgressBar = new JProgressBar();
    private DragonBallProgressBarUi indeterminateUi;

    private final JSlider sliderForHeight = new JSlider(20, 32, DragonBallProgressState.getInstance().getHeight());


    private String pathLeftIco;
    private String pathRightIco;

    /*private String pathOndaIco;
    private String pathOndaRevIco;*/

    private int selectedHeight;

    int i = 0;

    ButtonGroup buttonGroup_LF = new ButtonGroup();
    ButtonGroup buttonGroup_RF = new ButtonGroup();

    String[] orderCriteria = {"Goku","Muten","Crillin","Radish","Tensing","Vegeta","Gohan","Junior",
            "Freezer","Trunks","C19","C20","C16","C17","C18","Cell","Gotenks","MajinBu","Hit","Jiren",
            "Beerus","Granolah", "Shenron"};

    public DragonBallProgressConfigurationComponent() {
        try {
            //System.out.println("DragonBallProgressConfigurationComponent -> createUI");
            createUi();
        }catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        createUi();
    }

    void createUi() {
        final FormBuilder formBuilder = FormBuilder.createFormBuilder();
        //formBuilder.addComponent(createTestBox());
        formBuilder.addComponent(createTitlePanel());
        formBuilder.addComponent(createPreviewPanel());
        formBuilder.addComponent(createHieghtPanel());

        ArrayList<ImageIcon> Rfighters = resourceLoader("r");
        ArrayList<ImageIcon> Lfighters = resourceLoader("l");

        //formBuilder.addComponent(createFighterSelectionPanel(Rfighters,Lfighters));
        formBuilder.addComponent(fighterPanelWithOptions(Rfighters,Lfighters));
        mainPanel = formBuilder.getPanel();
    }


    private JPanel createTitlePanel() {
        final JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridBagLayout());
        final GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.WEST;
        left.weightx = 0.5;
        JLabel title= new JLabel("DragonBall ProgressBar Settings");
        title.setFont(title.getFont().deriveFont(Font.BOLD));
        titlePanel.add(title, left);
        return titlePanel;
    }

    private JPanel createHieghtPanel() {
        final JPanel hieghtPanel = new JPanel();
        hieghtPanel.setBorder(BorderFactory.createTitledBorder("[ Adjust progressbar height ]"));
        hieghtPanel.setLayout(new GridLayout(2,1));

        final JPanel checkBoxPanelEsterno = new JPanel();
        String title = "  Resize height: ";
        checkBoxPanelEsterno.setLayout(new BorderLayout());
        hieghtPanel.add(checkBoxPanelEsterno, BorderLayout.WEST); //Riga1
        //JLabel jtxa = new JLabel(title);
        //hieghtPanel.add(jtxa, BorderLayout.WEST);
        hieghtPanel.add(sliderForHeight);    //Riga2


        JBCheckBox checkbox = new JBCheckBox();
        checkbox.setText(title);
        checkBoxPanelEsterno.add(checkbox,BorderLayout.WEST);
        //Sta parte ti fa mette il testo affianco la checkbox
        checkbox.addItemListener(c -> {
            if (c.getStateChange() == ItemEvent.SELECTED) {
                sliderForHeight.setEnabled(true);
                //sliderForHeight.setValue(DragonBallProgressState.getInstance().getHeight());
                checkbox.setText(title+": " + sliderForHeight.getValue() + "px");
            } else if (c.getStateChange() == ItemEvent.DESELECTED) {
                sliderForHeight.setEnabled(false);
            }
        });
        //Sta parte ti aggiorna in real time il valore dello slider
        sliderForHeight.setEnabled(false);

        //sliderForHeight.setEnabled(true);
        sliderForHeight.addChangeListener(c -> {
            if (sliderForHeight.isEnabled()) {
                //jtxa.setText(title + ": " + sliderForHeight.getValue() + "px");
                checkbox.setText(title + ": " + sliderForHeight.getValue() + "px");
                //sliderForHeight.setValue(sliderForHeight.getValue());
                //setSliderForHeight(sliderForHeight.getValue());
                //DragonBallProgressState.getInstance().setHeight(sliderForHeight.getValue());
                setSelectedHeight(sliderForHeight.getValue());
                //System.out.println("DragonBallProgressState.getInstance().getHeight(): "+DragonBallProgressState.getInstance().getHeight());

                determinateProgressBar.setUI(determinateUi);
                indeterminateProgressBar.setUI(indeterminateUi);
                //setChangedSlider(true);

            }
        });
        setSelectedHeight(sliderForHeight.getValue());

        return hieghtPanel;
    }


    private JPanel createPreviewPanel() {

        final JPanel previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createTitledBorder("[ Progressbar Preview ]"));
        previewPanel.setLayout(new GridLayout(1,2));

        //Primo pannello
        final JPanel determinate = new JPanel();
        //JLabel titleDeterminate = new JLabel("Determinate: ");
        //determinate.add(titleDeterminate);

        determinateProgressBar.setIndeterminate(false);
        determinateProgressBar.setValue(1);
        determinateUi = createProgressBarUi();
        determinateProgressBar.setUI(determinateUi);

        determinate.add(LabeledComponent.create(determinateProgressBar, "Determinate"));
        //Secondo pannello
        final JPanel indeterminate = new JPanel();

        indeterminateProgressBar.setIndeterminate(true);
        indeterminateProgressBar.setValue(1);
        indeterminateUi = createProgressBarUi();
        indeterminateProgressBar.setUI(indeterminateUi);

        indeterminate.add(LabeledComponent.create(indeterminateProgressBar,"Indeterminate"));

        previewPanel.add(determinate);
        previewPanel.add(indeterminate);
        return previewPanel;
    }

    /*private JPanel createFighterSelectionPanel(ArrayList<ImageIcon> rf , ArrayList<ImageIcon> lf){
        final JPanel fighterSelectionPanel = new JPanel();
        fighterSelectionPanel.setBorder(BorderFactory.createTitledBorder("[ Select one fighter for each side ]"));
        fighterSelectionPanel.setLayout(new GridLayout(1,2));

        ButtonGroup groupLfighters = new ButtonGroup();
        ButtonGroup groupRfighters = new ButtonGroup();

        var state  = DragonBallProgressState.getInstance().getState();
        ImageIcon currentSelectedL = generaIco(state.getPathLeftIco());//state.getLeftFighter();
        ImageIcon currentSelectedR = generaIco(state.getPathRightIco());//state.getRightFighter();
        //System.out.println("state.getLeftFighterIco(): "+currentSelectedL.getDescription());
        //System.out.println("state.getRightFighterIco(): "+currentSelectedR.getDescription());

        //Attacking Fighters checkboxes
        JPanel checkPanelLfighetrs = new JPanel(new GridLayout(0,1));
        JLabel LSectiontitle= new JLabel("Attacking fighter: ");
        checkPanelLfighetrs.add(LSectiontitle);
        fighterSelectionPanel.add(checkPanelLfighetrs);
        for (int i=0; i<lf.size(); i++) {
            JRadioButtonMenuItem ljb = new JRadioButtonMenuItem(null,lf.get(i));
            //System.out.println("Ico della lista #"+i+": "+lf.get(i));
            if(lf.get(i).getDescription().equals(currentSelectedL.getDescription())){
                ljb.setSelected(true);
            }else{
                ljb.setSelected(false);
            }

            checkPanelLfighetrs.add(ljb);
            ljb.addItemListener(itemListener);
            groupLfighters.add(ljb);
        }

        //Defending Fighters checkboxes
        JPanel checkPanelRfighetrs = new JPanel(new GridLayout(0,1));
        JLabel RSectiontitle= new JLabel("Defending fighter: ");
        checkPanelRfighetrs.add(RSectiontitle);
        fighterSelectionPanel.add(checkPanelRfighetrs);
        for (int i=0; i<rf.size(); i++) {
            JRadioButtonMenuItem rjb = new JRadioButtonMenuItem(null,rf.get(i));

            //System.out.println("Ico della lista #"+i+": "+rf.get(i).getDescription());
            if(rf.get(i).getDescription().equals(currentSelectedR.getDescription())){
                rjb.setSelected(true);
            }else{
                rjb.setSelected(false);
            }

            checkPanelRfighetrs.add(rjb);
            rjb.addItemListener(itemListener);
            groupRfighters.add(rjb);

        }

        fighterSelectionPanel.add(checkPanelLfighetrs);
        fighterSelectionPanel.add(checkPanelRfighetrs);
        return fighterSelectionPanel;
    }*/


    private JPanel fighterPanelWithOptions(ArrayList<ImageIcon> rfList , ArrayList<ImageIcon> lfList){
        //Carico le impostazioni correnti - Start
        var state  = DragonBallProgressState.getInstance().getState();
        ImageIcon currentSelectedL = generaIco(state.getPathLeftIco(), 'l');
        ImageIcon currentSelectedR = generaIco(state.getPathRightIco(), 'r');
        //Carico le impostazioni correnti - End

        final JPanel fighterSelectionPanelContainer = new JPanel();
        fighterSelectionPanelContainer.setLayout(new GridLayout(1,2));
        fighterSelectionPanelContainer.setBorder(BorderFactory.createTitledBorder("[ Select one fighter for each side ]"));

        Map<String, ArrayList<ImageIcon>> mapLeftIcons = organizeIcons(lfList);
        Map<String, ArrayList<ImageIcon>> mapRightIcons = organizeIcons(rfList);

        JPanel leftPanel = createOrganizedPanel(mapLeftIcons, currentSelectedL, "l");
        JPanel rightPanel = createOrganizedPanel(mapRightIcons, currentSelectedR, "r");

        fighterSelectionPanelContainer.add(leftPanel);
        fighterSelectionPanelContainer.add(rightPanel);

        return fighterSelectionPanelContainer;
    }

    public Map<String, ArrayList<ImageIcon>> organizeIcons(ArrayList<ImageIcon> iconList){
        //Organizza le icone:
        //Crea una mappa dove la chiave è il nome del personaggio e il value è una lista di icone.
        //Così se l'iesima lista avrà size > 0 significa che si creerà il "componente" bottone-panel-radiobutton per la selezione della versione
        System.out.println("organizeIcons - Start ");

        Map<String, ArrayList<ImageIcon>> mapFighterVersions = new HashMap<>();

        for (ImageIcon ic : iconList) {
            String iconNameTemp = ic.getDescription().split(Icons.basePath)[1]; //dovrei avere il nome del fighter
            String iconName = iconNameTemp.split("_")[1]; //dovrei avere il nome del fighter
                System.out.println("organizeIcons - iconName Found: "+iconName);
                if(mapFighterVersions.containsKey(iconName)){
                    //duplicato -> altra versione
                    mapFighterVersions.get(iconName).add(ic);
                }else{
                    //nuovo
                    ArrayList<ImageIcon> versionList = new ArrayList<>();
                    versionList.add(ic);
                    mapFighterVersions.put(iconName, versionList);
                }
        }
        System.out.println("organizeIcons - end ");

        return mapFighterVersions;
    }

    public JPanel createOrganizedPanel(Map<String, ArrayList<ImageIcon>> mapIcons,ImageIcon currentSelected, String side){
        JPanel organizedPanel = new JPanel(new FlowLayout());
        organizedPanel.setLayout(new BoxLayout(organizedPanel,BoxLayout.Y_AXIS));
        //organizedPanel.setLayout(new GridLayout(1,1));
        for(String fighterToOrder : orderCriteria){
            System.out.println("organizeIcons - fighterDaOrdinare: "+fighterToOrder);
            for(String key : mapIcons.keySet()){
                if(key.contains(fighterToOrder)){
                    System.out.println("Creo pannello/button per icona: "+key);
                    ArrayList<ImageIcon> iconList = mapIcons.get(key);
                    if(iconList.size()>1){
                        //Quindi Bottone che fa apparire il panel contente tutte le varianti
                        System.out.println("Bottone + Pannello a scomparsa");
                        JPanel showOptionPanel = new JPanel(new FlowLayout());
                        showOptionPanel.setLayout(new BoxLayout(showOptionPanel,BoxLayout.Y_AXIS));
                        showOptionPanel.setBorder(BorderFactory.createTitledBorder("[ Fighter Versions ]"));
                        showOptionPanel.setVisible(false);
                        JButton openPanelButton = new JButton("",iconList.get(0));
                        organizedPanel.add(openPanelButton);
                        for(ImageIcon ic : iconList){
                            JRadioButtonMenuItem jbutton = new JRadioButtonMenuItem(null,ic);
                            showOptionPanel.add(jbutton);
                            jbutton.addItemListener(itemListener);
                            if(side == "l"){
                                jbutton.addActionListener(e -> showNotification("Left fighter selected correctly! Click 'Apply' to see it in preview or 'Ok' to save directly the changes."));
                                buttonGroup_LF.add(jbutton);
                            }else if(side == "r"){
                                jbutton.addActionListener(e -> showNotification("Right fighter selected correctly! Click 'Apply' to see it in preview or 'Ok' to save directly the changes."));
                                buttonGroup_RF.add(jbutton);
                            }
                        }
                        // Aggiungi un listener al bottone per gestire l'azione di mostrare/nascondere
                        openPanelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Inverti lo stato di visibilità del secondo JPanel
                                showOptionPanel.setVisible(!showOptionPanel.isVisible());
                            }
                        });
                        organizedPanel.add(showOptionPanel);
                    }else{
                        System.out.println("Solo Bottone");
                        ImageIcon singleOption = iconList.get(0);
                        String singleOptionDescription = singleOption.getDescription();
                        JRadioButtonMenuItem jbutton = new JRadioButtonMenuItem(null,singleOption);
                        if(currentSelected.getDescription() == singleOptionDescription){
                            jbutton.setSelected(true);
                        }else{
                            jbutton.setSelected(false);
                        }
                        organizedPanel.add(jbutton);
                        jbutton.addItemListener(itemListener);
                        if(side == "l"){
                            jbutton.addActionListener(e -> showNotification("Left fighter selected correctly! Click 'Apply' to see it in preview or 'Ok' to save directly the changes."));
                            buttonGroup_LF.add(jbutton);
                        }else if(side == "r"){
                            jbutton.addActionListener(e -> showNotification("Right fighter selected correctly! Click 'Apply' to see it in preview or 'Ok' to save directly the changes."));
                            buttonGroup_RF.add(jbutton);
                        }
                    }
                }
            }
        }

        return organizedPanel;
    }

    private void showNotification(String message) {
        ImageIcon checkMark = Icons.CheckMark;
        Messages.showMessageDialog(message, "Success",checkMark);
    }

    ItemListener itemListener = new ItemListener() {
        public void itemStateChanged(ItemEvent itemEvent) {
            AbstractButton aButton = (AbstractButton) itemEvent.getSource();
            int state = itemEvent.getStateChange();
            ImageIcon selectedImageIcon = (ImageIcon) aButton.getIcon();
            String descriptor = selectedImageIcon.getDescription();
            System.out.println("itemStateChanged - descriptor: "+descriptor);
            //String genericPath = descriptor.split("/Resources")[1];
            String genericPath = descriptor.split("/Sprites")[1];
            if(descriptor.contains("[LeftFighter]_")){
                System.out.println("itemStateChanged --> L");
                setPathLeftIco(Icons.basePath+genericPath); //NEW
            }else if (descriptor.contains("[RightFighter]_")){
                System.out.println("itemStateChanged --> R");
                setPathRightIco(Icons.basePath+genericPath);
            }
            System.out.println("Selected: "+selectedImageIcon);
        }
    };

    public JPanel getPanel() {
        return mainPanel;
    }

    ArrayList<ImageIcon> resourceLoader(String type){
        ArrayList<ImageIcon> l_icons = new ArrayList<>();
        ArrayList<ImageIcon> r_icons = new ArrayList<>();

        if(type.equalsIgnoreCase("L")){
            l_icons.add(Icons.L_Goku_v1);
            l_icons.add(Icons.L_Goku_v2);
            l_icons.add(Icons.L_Goku_v3);
            l_icons.add(Icons.L_Goku_v4);
            l_icons.add(Icons.L_Goku_v5);
            l_icons.add(Icons.L_Goku_v6);
            l_icons.add(Icons.L_Goku_v7);
            l_icons.add(Icons.L_Muten_v1);
            l_icons.add(Icons.L_Muten_v2);
            l_icons.add(Icons.L_Crillin_v1);
            l_icons.add(Icons.L_Radish_v1);
            l_icons.add(Icons.L_Tensing_v1);
            l_icons.add(Icons.L_Vegeta_v1);
            l_icons.add(Icons.L_Vegeta_v2);
            l_icons.add(Icons.L_Vegeta_v3);
            l_icons.add(Icons.L_Vegeta_v4);
            l_icons.add(Icons.L_Vegeta_v5);
            l_icons.add(Icons.L_Vegeta_v6);
            l_icons.add(Icons.L_Gohan_v1);
            l_icons.add(Icons.L_Gohan_v2);
            l_icons.add(Icons.L_Gohan_v3);
            l_icons.add(Icons.L_Gohan_v4);
            l_icons.add(Icons.L_Junior_v1);
            l_icons.add(Icons.L_Trunks_v1);
            l_icons.add(Icons.L_Trunks_v2);
            l_icons.add(Icons.L_Gotenks_v1);
            l_icons.add(Icons.L_Freezer_v1);
            l_icons.add(Icons.L_Freezer_v2);
            l_icons.add(Icons.L_Freezer_v3);
            l_icons.add(Icons.L_C16_v1);
            l_icons.add(Icons.L_C17_v1);
            l_icons.add(Icons.L_C18_v1);
            l_icons.add(Icons.L_C19_v1);
            l_icons.add(Icons.L_C20_v1);
            l_icons.add(Icons.L_Cell_v1);
            l_icons.add(Icons.L_Cell_v2);
            l_icons.add(Icons.L_MajinBu_v1);
            l_icons.add(Icons.L_MajinBu_v2);
            l_icons.add(Icons.L_MajinBu_v3);
            l_icons.add(Icons.L_MajinBu_v4);
            l_icons.add(Icons.L_Hit_v1);
            l_icons.add(Icons.L_Jiren_v1);
            l_icons.add(Icons.L_Jiren_v2);
            l_icons.add(Icons.L_Beerus_v1);
            l_icons.add(Icons.L_Granolah_v1);
            l_icons.add(Icons.L_Shenron_v1);

        }else if (type.equalsIgnoreCase("R")){
            r_icons.add(Icons.R_Goku_v1);
            r_icons.add(Icons.R_Goku_v2);
            r_icons.add(Icons.R_Goku_v3);
            r_icons.add(Icons.R_Goku_v4);
            r_icons.add(Icons.R_Goku_v5);
            r_icons.add(Icons.R_Goku_v6);
            r_icons.add(Icons.R_Goku_v7);
            r_icons.add(Icons.R_Muten_v1);
            r_icons.add(Icons.R_Crillin_v1);
            r_icons.add(Icons.R_Radish_v1);
            r_icons.add(Icons.R_Tensing_v1);
            r_icons.add(Icons.R_Vegeta_v1);
            r_icons.add(Icons.R_Vegeta_v2);
            r_icons.add(Icons.R_Vegeta_v3);
            r_icons.add(Icons.R_Vegeta_v4);
            r_icons.add(Icons.R_Vegeta_v5);
            r_icons.add(Icons.R_Vegeta_v6);
            r_icons.add(Icons.R_Gohan_v1);
            r_icons.add(Icons.R_Gohan_v2);
            r_icons.add(Icons.R_Gohan_v3);
            r_icons.add(Icons.R_Junior_v1);
            r_icons.add(Icons.R_Trunks_v1);
            r_icons.add(Icons.R_Trunks_v2);
            r_icons.add(Icons.R_Gotenks_v1);
            r_icons.add(Icons.R_Freezer_v1);
            r_icons.add(Icons.R_Freezer_v2);
            r_icons.add(Icons.R_Freezer_v3);
            r_icons.add(Icons.R_C16_v1);
            r_icons.add(Icons.R_C17_v1);
            r_icons.add(Icons.R_C18_v1);
            r_icons.add(Icons.R_C19_v1);
            r_icons.add(Icons.R_C20_v1);
            r_icons.add(Icons.R_Cell_v1);
            r_icons.add(Icons.R_Cell_v2);
            r_icons.add(Icons.R_MajinBu_v1);
            r_icons.add(Icons.R_MajinBu_v2);
            r_icons.add(Icons.R_MajinBu_v3);
            r_icons.add(Icons.R_MajinBu_v4);
            r_icons.add(Icons.R_Hit_v1);
            r_icons.add(Icons.R_Jiren_v1);
            r_icons.add(Icons.R_Jiren_v2);
            r_icons.add(Icons.R_Beerus_v1);
            r_icons.add(Icons.R_Granolah_v1);
            r_icons.add(Icons.R_Shenron_v1);
        }

        return (type.equalsIgnoreCase("r")?r_icons:l_icons);
    }

    public ImageIcon generaIco(String path, char side){
        try{
            ImageIcon Icon = new ImageIcon(Icons.class.getClassLoader().getResource(path));
            return Icon;
        }catch(Exception e){
            System.out.println("CONFIG COMP - generaIco - exception: "+e.getMessage());
            System.out.println("generaIco - setto default: "+e.getMessage());
            if(side=='l'){
                //path = "Resources/Sprites/[LeftFighter]_Goku_v1.png";
                path = Icons.basePath+"[LeftFighter]_Goku_v1.png";
            }else if(side == 'r'){
                //path="Resources/Sprites/[RightFighter]_Vegeta_v1.gif";
                path=Icons.basePath+"[RightFighter]_Vegeta_v1.gif";
            }
            ImageIcon Icon = new ImageIcon(Icons.class.getClassLoader().getResource(path));
            return Icon;
        }
    }


    private DragonBallProgressBarUi createProgressBarUi() {
        return new DragonBallProgressBarUi();
    }

    public int getSelectedHeight() {
        return selectedHeight;
    }

    public void setSelectedHeight(int selectedHeight) {
        this.selectedHeight = selectedHeight;
    }

    public String getPathLeftIco() {
        return pathLeftIco;
    }

    public void setPathLeftIco(String pathLeftIco) {
        this.pathLeftIco = pathLeftIco;
    }

    public String getPathRightIco() {
        return pathRightIco;
    }

    public void setPathRightIco(String pathRightIco) {
        this.pathRightIco = pathRightIco;
    }
}
