package sebcel.genealogia.gui.component;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sebcel.genealogia.gui.lista.MultiValueReferenceModel;
import sebcel.genealogia.struct.ReferenceListElement;

public class MultiValueReferenceField extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JPanel buttonPanel = new JPanel();
    private JScrollPane scrollPanel = new JScrollPane();
    private JList listPanel = new JList();
    private JButton buttonAdd = new JButton("+");
    private JButton buttonDelete = new JButton("-");

    private MultiValueReferenceModel listModel = new MultiValueReferenceModel();
    private List<ReferenceListElement> allItems = new ArrayList<ReferenceListElement>();

    public MultiValueReferenceField() {
        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(scrollPanel, BorderLayout.CENTER);
        this.scrollPanel.setViewportView(listPanel);
        this.buttonPanel.add(buttonAdd);
        this.buttonPanel.add(buttonDelete);
        this.listPanel.setModel(listModel);
        this.buttonAdd.addActionListener(this);
        this.buttonDelete.addActionListener(this);
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public GridBagConstraints getConstraints(int x, int y) {
        return new GridBagConstraints(x, y, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 1, 1);
    }

    public void setSelectedItems(List<ReferenceListElement> selectedItems) {
        listModel.setData(selectedItems);
        listPanel.repaint();
    }

    public List<ReferenceListElement> getSelectedItems() {
        return listModel.getData();
    }

    public void setAllItems(List<ReferenceListElement> allItems) {
        this.allItems = allItems;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAdd) {
            addItem();
        }
        if (e.getSource() == buttonDelete) {
            removeItem();
        }
    }

    private void addItem() {
        ReferenceSelector wybieraczka = new ReferenceSelector(allItems);
        ReferenceListElement selectedObject = wybieraczka.getSelectedItem();
        if (selectedObject != null) {
            listModel.addElement(selectedObject);
        }
    }

    private void removeItem() {
        if (!listPanel.isSelectionEmpty()) {
            ReferenceListElement selectedObject = listModel.getElementObjectAt(listPanel.getSelectedIndex());
            listModel.removeElement(selectedObject);
        }
    }
}