package pl.sebcel.genealogy.gui.pedigree;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.sebcel.genealogy.gui.IDrawOptionsListener;

public class PedigreeChartOptionsPanel extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private PedigreeChartOptions chartOptions = new PedigreeChartOptions();

    private JButton buttonClose = new JButton("Zamknij");
    private JButton buttonApply = new JButton("Zastosuj");
    private JCheckBox checkBoxShowIds = new JCheckBox();
    private JCheckBox checkBoxShowBirthData = new JCheckBox();
    private JCheckBox checkBoxShowDeathData = new JCheckBox();
    private JCheckBox checkBoxShowOccupation = new JCheckBox();
    private JCheckBox checkBoxShowLocation = new JCheckBox();
    private JCheckBox checkBoxShowMeetingInfo = new JCheckBox();
    private JCheckBox checkBoxShowMarriageInfo = new JCheckBox();
    private JCheckBox checkBoxShowSeparationInfo = new JCheckBox();
    private JCheckBox checkBoxShowDivorceInfo = new JCheckBox();
    private PedigreeChartZoomPanel zoomPanel = new PedigreeChartZoomPanel();

    private IDrawOptionsListener drawOptionsListener;

    public PedigreeChartOptionsPanel() {
        this.setLayout(new BorderLayout());
        this.setSize(600, 400);
        this.setLocation(100, 100);
        this.setTitle("Opcje rysowania drzew");
        JPanel buttonPanel = new JPanel();
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridBagLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(optionsPanel, BorderLayout.CENTER);
        buttonPanel.add(buttonApply);
        buttonPanel.add(buttonClose);

        optionsPanel.add(new JLabel("Identyfikatory"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowIds, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o urodzeniu"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowBirthData, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o śmierci"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowDeathData, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o zawodzie"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowOccupation, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o zamieszkaniu"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowLocation, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o poznaniu się"), new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowMeetingInfo, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o małżeństwie"), new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowMarriageInfo, new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o rozstaniu się"), new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowSeparationInfo, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Dane o rozwodzie"), new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(checkBoxShowDivorceInfo, new GridBagConstraints(1, 8, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(new JLabel("Powiększenie"), new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));
        optionsPanel.add(zoomPanel, new GridBagConstraints(1, 9, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1));

        checkBoxShowIds.addActionListener(this);
        checkBoxShowBirthData.addActionListener(this);
        checkBoxShowDeathData.addActionListener(this);
        checkBoxShowOccupation.addActionListener(this);
        checkBoxShowLocation.addActionListener(this);
        checkBoxShowMeetingInfo.addActionListener(this);
        checkBoxShowMarriageInfo.addActionListener(this);
        checkBoxShowSeparationInfo.addActionListener(this);
        checkBoxShowDivorceInfo.addActionListener(this);
        zoomPanel.addActionListener(this);

        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PedigreeChartOptionsPanel.this.setVisible(false);
            }
        });

        buttonApply.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                drawOptionsListener.updateDrawing(chartOptions);
            }
        });

        this.pack();
    }

    public void setDrawOptionsListener(IDrawOptionsListener drawOptionsListener) {
        this.drawOptionsListener = drawOptionsListener;
    }

    public PedigreeChartOptions getChartOptions() {
        return chartOptions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        chartOptions.setShowIdentifiers(checkBoxShowIds.isSelected());
        chartOptions.setShowBirthInfo(checkBoxShowBirthData.isSelected());
        chartOptions.setShowDeathInfo(checkBoxShowDeathData.isSelected());
        chartOptions.setShowResidenceInfo(checkBoxShowLocation.isSelected());
        chartOptions.setShowOccupationInfo(checkBoxShowOccupation.isSelected());
        chartOptions.setShowFirstMetInfo(checkBoxShowMeetingInfo.isSelected());
        chartOptions.setShowMarriageInfo(checkBoxShowMarriageInfo.isSelected());
        chartOptions.setShowSeparationInfo(checkBoxShowSeparationInfo.isSelected());
        chartOptions.setShowDivorceInfo(checkBoxShowDivorceInfo.isSelected());
        chartOptions.setZoom(zoomPanel.getZoom());
    }
}