package pl.sebcel.genealogy.gui.pedigree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.pedigree.FamilyTreeElement;
import pl.sebcel.genealogy.dto.pedigree.PersonTreeElement;
import pl.sebcel.genealogy.gui.IDrawOptionsListener;

public class PedigreeChartFrame extends JFrame implements ActionListener, IDrawOptionsListener {

    public final static long serialVersionUID = 0l;

    private static Color personColor = Color.RED;
    private static Color spouseColor = Color.BLUE;
    private static Color personInfoColor = Color.GRAY;
    private static Color childrenColor = Color.BLACK;
    private static Color spouseLineColor = new Color(200, 200, 200);
    private static Color marriageInfoColor = new Color(0, 200, 0);

    private final JPanel buttonsPanel = new JPanel();
    private final PedigreeChartOptionsPanel chartOptionsPanel = new PedigreeChartOptionsPanel();
    private final JButton saveButton = new JButton("Zapisz");
    private final JButton closeButton = new JButton("Zamknij");
    private final JButton optionsButton = new JButton("Opcje");
    private final PedigreeTreeComponent treeComponent = new PedigreeTreeComponent();
    private final JScrollPane scrollPane = new JScrollPane(new JLabel("Proszę czekać...", JLabel.CENTER));

    private DiagramInfoStruct diagramInfo;

    public PedigreeChartFrame() {
        buttonsPanel.add(saveButton);
        buttonsPanel.add(closeButton);
        buttonsPanel.add(optionsButton);

        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        optionsButton.addActionListener(this);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        chartOptionsPanel.setDrawOptionsListener(this);
    }

    public void drawPedigreeChart(final DiagramInfoStruct diagramInfo) {
        this.setSize(800, 600);
        this.setLocation(100, 100);
        this.setTitle(diagramInfo.getDescription());
        this.setVisible(true);
        this.diagramInfo = diagramInfo;

        new Thread() {
            public void run() {
                draw(new PedigreeChartOptions());
            }
        }.start();
    }

    @Override
    public void updateDrawing(PedigreeChartOptions chartOptions) {
        draw(chartOptions);
    }

    private void draw(PedigreeChartOptions chartOptions) {
        System.out.println("rysuj�");
        Image pedigreeTreeImage = drawTree(diagramInfo.getRootId(), new Font("Times", Font.PLAIN, 12 * chartOptions.getZoom()), 20, chartOptions);
        treeComponent.setImage(pedigreeTreeImage);
        scrollPane.setViewportView(PedigreeChartFrame.this.treeComponent);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(closeButton)) {
            this.setVisible(false);
        }

        if (e.getSource().equals(optionsButton)) {
            chartOptionsPanel.setVisible(true);
        }

        if (e.getSource().equals(saveButton)) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    String end = f.getName().substring(f.getName().length() - 4).toLowerCase();
                    if (end.equals(".png")) {
                        return true;
                    }
                    if (end.equals(".jpg")) {
                        return true;
                    }
                    if (end.equals(".gif")) {
                        return true;
                    }
                    if (end.equals(".bmp")) {
                        return true;
                    }
                    if (end.equals(".png")) {
                        return true;
                    }
                    return false;
                }

                @Override
                public String getDescription() {
                    return "Supported graphics formats";
                }

            });

            int returnVal = fileChooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    String filename = fileChooser.getSelectedFile().getCanonicalPath();
                    Image image = treeComponent.getImage();
                    
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(this), image.getHeight(this), BufferedImage.TYPE_INT_RGB);
                    bufferedImage.getGraphics().drawImage(image, 0, 0, null);
                    ImageIO.write(bufferedImage, "png", new File(filename));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Wystąpił błąd:\n" + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private Image drawTree(Long personId, Font font, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        Dimension bufferDimensions = new Dimension(10 * font.getSize() * widthOfGeneration, 500 * font.getSize());
        BufferedImage image = new BufferedImage(bufferDimensions.width, bufferDimensions.height, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bufferDimensions.width, bufferDimensions.height);
        g.setFont(font);
        Dimension dimension = draw(personId, g, 0, 0, widthOfGeneration, chartOptions);
        BufferedImage newImage = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_BYTE_INDEXED);
        newImage.getGraphics().drawImage(image, 0, 0, new ImageObserver() {

            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return true;
            }

        });
        return newImage;
    }

    private Dimension draw(Long personId, Graphics g, int x, int y, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFont().getSize();
        int width = widthOfGeneration * fontSize;
        int height = fontSize;

        PersonTreeElement person = DatabaseDelegate.getPersonDataForPedigree(personId);
        String personName = person.getDescription();
        if (chartOptions.isShowIdentifiers()) {
            personName += " (" + person.getId() + ")";
        }

        g.setColor(personColor);
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, fontSize));
        g.drawString(personName, x, y + height);
        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));

        g.setColor(personInfoColor);
        String birthInfo = person.getBirthData();
        if (birthInfo.length() > 0 && chartOptions.isShowBirthInfo()) {
            height += fontSize;
            g.drawString(birthInfo, x, y + height);
        }
        String deathInfo = person.getDeathData();
        if (deathInfo.length() > 0 && chartOptions.isShowDeathInfo()) {
            height += fontSize;
            g.drawString(deathInfo, x, y + height);
        }
        String residenceInfo = person.getResidenceData();
        if (residenceInfo.length() > 0 && chartOptions.isShowResidenceInfo()) {
            height += fontSize;
            g.drawString(residenceInfo, x, y + height);
        }
        String occupationInfo = person.getOccupationData();
        if (occupationInfo.length() > 0 && chartOptions.isShowOccupationInfo()) {
            height += fontSize;
            g.drawString(occupationInfo, x, y + height);
        }
        if (birthInfo.length() > 0 || deathInfo.length() > 0 || occupationInfo.length() > 0 || residenceInfo.length() > 0) {
            height += fontSize / 2;
        }

        int personNameWidth = (int) g.getFont().getStringBounds(personName, new FontRenderContext(new AffineTransform(), false, false)).getWidth();
        Dimension familiesDimension = drawFamilies(person, g, x, y + height, personNameWidth, height, widthOfGeneration, chartOptions);
        height += familiesDimension.height;
        if (familiesDimension.width > width) {
            width = familiesDimension.width;
        }

        return new Dimension(width, height);
    }

    private Dimension drawFamilies(PersonTreeElement person, Graphics g, int x, int y, int spouseInfoWidth, int spouseInfoHeight, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFont().getSize();
        int height = 0;
        int width = widthOfGeneration * fontSize;

        List<FamilyTreeElement> families = person.getFamilies();

        if (families != null && families.size() > 0) {
            int x1 = 0;
            int y1 = 0;
            int counter = 0;
            for (FamilyTreeElement family : families) {
                Long spouseId = family.getSpouseId();
                PersonTreeElement spouse = DatabaseDelegate.getPersonDataForPedigree(spouseId);

                int x0 = x;
                int y0 = y + height;
                if (x1 > 0 && y1 > 0) {
                    g.setColor(spouseLineColor);
                    g.drawLine(x0, y0, x1, y1);
                }

                x1 = x0;
                y1 = y0 + fontSize;
                Dimension familiesDimension = drawFamily(family, spouse, g, x, y + height, counter == 0, spouseInfoWidth, spouseInfoHeight, widthOfGeneration, chartOptions);
                int familiesHeight = familiesDimension.height;
                if (familiesDimension.width > width) {
                    width = familiesDimension.width;
                }
                height += familiesHeight + fontSize;
                counter++;
            }
        }

        return new Dimension(width, height);
    }

    private Dimension drawFamily(FamilyTreeElement family, PersonTreeElement spouse, Graphics g, int x, int y, boolean lowered, int spouseInfoWidth, int spouseInfoHeight, int widthOfGeneration, PedigreeChartOptions chartOptions) {

        int fontSize = g.getFont().getSize();
        int width = fontSize * widthOfGeneration;

        int childrenWidth = 0;
        int childrenHeight = 0;

        Dimension spouseDimension = drawSpouse(family, spouse, g, x, y, widthOfGeneration, chartOptions);

        String spouseName = spouse.getDescription();
        if (chartOptions.isShowIdentifiers()) {
            spouseName += " (" + spouse.getId() + ")";
        }

        int spouseNameWidth = (int) g.getFont().getStringBounds("+ " + spouseName, new FontRenderContext(new AffineTransform(), false, false)).getWidth();
        int margin = 5;

        if (family.getChildrenIds() != null && family.getChildrenIds().size() > 0) {
            int yy = y;
            int xx = x + spouseNameWidth + margin;
            if (lowered) {
                yy -= spouseInfoHeight;
                xx = x + spouseInfoWidth + margin;
            }
            Dimension childrenDimension = drawChildren(family.getChildrenIds(), g, x + width, yy, widthOfGeneration, chartOptions);
            childrenHeight += childrenDimension.height;

            if (childrenDimension.width > childrenWidth) {
                childrenWidth = childrenDimension.width;
            }
            g.setColor(childrenColor);
            g.drawLine(xx, yy + fontSize / 2, x + width - margin, yy + fontSize / 2);
        }

        if (lowered) {
            childrenHeight -= spouseInfoHeight;
        }
        int familiesHeight = Math.max(spouseDimension.height, childrenHeight);

        return new Dimension(width + childrenWidth, familiesHeight);
    }

    private Dimension drawSpouse(FamilyTreeElement family, PersonTreeElement spouse, Graphics g, int x, int y, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFont().getSize();
        int height = fontSize;

        String spouseName = spouse.getDescription();
        if (chartOptions.isShowIdentifiers()) {
            spouseName += " (" + spouse.getId() + ")";
        }

        g.setColor(spouseColor);
        int width = fontSize * widthOfGeneration;
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, fontSize));
        g.drawString("+ " + spouseName, x, y + fontSize);
        g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));

        g.setColor(personInfoColor);
        String birthInfo = spouse.getBirthData();
        if (birthInfo.length() > 0 && chartOptions.isShowBirthInfo()) {
            g.setColor(personInfoColor);
            height += fontSize;
            g.drawString(birthInfo, x, y + height);
        }
        String deathInfo = spouse.getDeathData();
        if (deathInfo.length() > 0 && chartOptions.isShowDeathInfo()) {
            g.setColor(personInfoColor);
            height += fontSize;
            g.drawString(deathInfo, x, y + height);
        }
        String residenceInfo = spouse.getResidenceData();
        if (residenceInfo.length() > 0 && chartOptions.isShowResidenceInfo()) {
            height += fontSize;
            g.drawString(residenceInfo, x, y + height);
        }
        String occupationInfo = spouse.getOccupationData();
        if (occupationInfo.length() > 0 && chartOptions.isShowOccupationInfo()) {
            height += fontSize;
            g.drawString(occupationInfo, x, y + height);
        }

        height += fontSize / 2;

        if (chartOptions.isShowIdentifiers()) {
            g.setColor(marriageInfoColor);
            String relationshipInfo = "Id związku: " + family.getRelationshipId();
            height += fontSize;
            g.drawString(relationshipInfo, x, y + height);
        }

        String firstMetInfo = family.getFirstMetData();
        if (firstMetInfo.length() > 0 && chartOptions.isShowFirstMetInfo()) {
            g.setColor(marriageInfoColor);
            height += fontSize;
            g.drawString(firstMetInfo, x, y + height);
        }
        String marriageInfo = family.getMarriageData();
        if (marriageInfo.length() > 0 && chartOptions.isShowMarriageInfo()) {
            g.setColor(marriageInfoColor);
            height += fontSize;
            g.drawString(marriageInfo, x, y + height);
        }
        String separationInfo = family.getSeparationData();
        if (separationInfo.length() > 0 && chartOptions.isShowSeparationInfo()) {
            g.setColor(marriageInfoColor);
            height += fontSize;
            g.drawString(separationInfo, x, y + height);
        }
        String divorceInfo = family.getDivorceData();
        if (divorceInfo.length() > 0 && chartOptions.isShowDivorceInfo()) {
            g.setColor(marriageInfoColor);
            height += fontSize;
            g.drawString(divorceInfo, x, y + height);
        }

        return new Dimension(width, height);
    }

    private Dimension drawChildren(List<Long> childrenIds, Graphics g, int x, int y, int widthOfGeneration, PedigreeChartOptions chartOptions) {
        int fontSize = g.getFont().getSize();
        int height = 0;
        int width = widthOfGeneration * fontSize;
        int oldX = 0;
        int oldY = 0;

        if (childrenIds != null && childrenIds.size() > 0) {
            for (Long childId : childrenIds) {
                Dimension childDimension = draw(childId, g, x + fontSize, y + height, widthOfGeneration, chartOptions);
                int childHeight = childDimension.height;
                if (childDimension.width > width) {
                    width = childDimension.width;
                }
                int x0 = x - fontSize / 2;
                int y0 = y + height + fontSize / 2;
                int x1 = x + fontSize / 2;
                int y1 = y + height + fontSize / 2;
                g.setColor(childrenColor);
                g.drawLine(x0, y0, x1, y1);
                if (oldX > 0 && oldY > 0) {
                    g.drawLine(x0, y0, oldX, oldY);
                }
                height += childHeight + fontSize;
                oldX = x0;
                oldY = y0;
            }
        }

        return new Dimension(width, height);
    }
}