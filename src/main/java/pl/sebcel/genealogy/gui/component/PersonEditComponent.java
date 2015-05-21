package pl.sebcel.genealogy.gui.component;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import pl.sebcel.genealogy.db.DatabaseDelegate;
import pl.sebcel.genealogy.dto.DiagramInfoStruct;
import pl.sebcel.genealogy.dto.FamilyElementForPersonEditData;
import pl.sebcel.genealogy.dto.edit.PersonEditData;
import pl.sebcel.genealogy.dto.list.ReferenceListElement;
import pl.sebcel.genealogy.gui.control.Tree;
import pl.sebcel.genealogy.gui.control.Label;
import pl.sebcel.genealogy.gui.control.MultiValueReference;
import pl.sebcel.genealogy.gui.control.TextArea;
import pl.sebcel.genealogy.gui.control.TextField;
import pl.sebcel.genealogy.gui.control.SingleValueReference;

public class PersonEditComponent extends AbstractEditComponent {

    public final static long serialVersionUID = 0l;

    private PersonEditData daneOsoby;

    private Label lSex = new Label("P�e�:");
    private Label lNames = new Label("Names:");
    private Label lSurname = new Label("Surname:");
    private Label lBirthDate = new Label("Data urodzenia:");
    private Label lBirthPlace = new Label("Miejsce urodzenia:");
    private Label lDeathDate = new Label("Data �mierci:");
    private Label lDeathPlace = new Label("Miejsce �mierci:");
    private Label lBurialDate = new Label("Data pochowania:");
    private Label lBurialPlace = new Label("Miejsce pochowania:");
    private Label lMiejsceZamiekszania = new Label("Miejsce zamieszkania:");
    private Label lEducation = new Label("Wykszta�cenie");
    private Label lOccupation = new Label("Zawody wykonywane");
    private Label lParents = new Label("Parents");
    private Label lDescription = new Label("Description");
    private Label lRodzina = new Label("Rodzina");
    private Label lDokumenty = new Label("Dokumenty");

    private SingleValueReference tSex = new SingleValueReference();
    private TextField tNames = new TextField();
    private TextField tSurname = new TextField();
    private TextField tBirthDate = new TextField();
    private TextField tBirthPlace = new TextField();
    private TextField tDeathDate = new TextField();
    private TextField tDeathPlace = new TextField();
    private TextField tBurialDate = new TextField();
    private TextField tBurialPlace = new TextField();
    private TextField tResidence = new TextField();
    private TextField tEducation = new TextField();
    private TextField tOccupation = new TextField();
    private TextArea tDescription = new TextArea();
    private SingleValueReference tParents = new SingleValueReference();
    private Tree tRodzina = new Tree();
    private MultiValueReference tDokumenty = new MultiValueReference();

    public PersonEditComponent() {
        this.setLayout(new GridBagLayout());

        int y = 0;
        this.add(lSex, lSex.getConstraints(0, y++));
        this.add(lNames, lNames.getConstraints(0, y++));
        this.add(lSurname, lSurname.getConstraints(0, y++));
        this.add(lBirthDate, lBirthDate.getConstraints(0, y++));
        this.add(lBirthPlace, lBirthPlace.getConstraints(0, y++));
        this.add(lDeathDate, lBirthDate.getConstraints(0, y++));
        this.add(lDeathPlace, lDeathPlace.getConstraints(0, y++));
        this.add(lBurialDate, lBirthDate.getConstraints(0, y++));
        this.add(lBurialPlace, lBurialPlace.getConstraints(0, y++));
        this.add(lMiejsceZamiekszania, lMiejsceZamiekszania.getConstraints(0, y++));
        this.add(lEducation, lEducation.getConstraints(0, y++));
        this.add(lOccupation, lOccupation.getConstraints(0, y++));
        this.add(lParents, lParents.getConstraints(0, y++));
        this.add(lDescription, lDescription.getConstraints(0, y++));
        this.add(lRodzina, lRodzina.getConstraints(0, y++));
        this.add(lDokumenty, lDokumenty.getConstraints(0, y++));

        y = 0;
        this.add(tSex, tSex.getConstraints(1, y++));
        this.add(tNames, tNames.getConstraints(1, y++));
        this.add(tSurname, tSurname.getConstraints(1, y++));
        this.add(tBirthDate, tBirthDate.getConstraints(1, y++));
        this.add(tBirthPlace, tBirthPlace.getConstraints(1, y++));
        this.add(tDeathDate, tDeathDate.getConstraints(1, y++));
        this.add(tDeathPlace, tDeathPlace.getConstraints(1, y++));
        this.add(tBurialDate, tBurialDate.getConstraints(1, y++));
        this.add(tBurialPlace, tBurialPlace.getConstraints(1, y++));
        this.add(tResidence, tResidence.getConstraints(1, y++));
        this.add(tEducation, tEducation.getConstraints(1, y++));
        this.add(tOccupation, tOccupation.getConstraints(1, y++));
        this.add(tParents, tParents.getConstraints(1, y++));
        this.add(tDescription, tDescription.getConstraints(1, y++));
        this.add(tRodzina, tRodzina.getConstraints(1, y++));
        this.add(tDokumenty, tDokumenty.getConstraints(1, y++));

        tSex.removeAllItems();
        tSex.addItem("<Wybierz>");
        tSex.addItem("M�czyzna");
        tSex.addItem("Female");
    }

    public void wczytajDane(Long idOsoby) {
        odswiezListy();

        if (idOsoby == null) {
            wyczyscPola();
            return;
        }
        daneOsoby = DatabaseDelegate.getPersonEditData(idOsoby);

        if (daneOsoby.getSex() == null) {
            tSex.setSelectedIndex(0);
        } else if (daneOsoby.getSex().equalsIgnoreCase("male")) {
            tSex.setSelectedIndex(1);
        } else if (daneOsoby.getSex().equalsIgnoreCase("female")) {
            tSex.setSelectedIndex(2);
        } else {
            tSex.setSelectedIndex(0);
        }
        tNames.setText(daneOsoby.getNames());
        tSurname.setText(daneOsoby.getSurname());
        tBirthDate.setText(daneOsoby.getBirthDate());
        tBirthPlace.setText(daneOsoby.getBirthPlace());
        tDeathDate.setText(daneOsoby.getDeathDate());
        tDeathPlace.setText(daneOsoby.getDeathPlace());
        tBurialDate.setText(daneOsoby.getBurialDate());
        tBurialPlace.setText(daneOsoby.getBurialPlace());
        tEducation.setText(daneOsoby.getEducation());
        tOccupation.setText(daneOsoby.getOccupation());
        tResidence.setText(daneOsoby.getResidence());
        tDescription.setText(daneOsoby.getDescription());
        if (daneOsoby.getParents() != null) {
            tParents.setSelectedItem(daneOsoby.getParents());
        } else {
            tParents.setSelectedIndex(0);
        }

        List<FamilyElementForPersonEditData> rodziny = daneOsoby.getFamilies();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        if (rodziny != null && rodziny.size() > 0) {
            for (FamilyElementForPersonEditData rodzina : rodziny) {
                DefaultMutableTreeNode nodeRodzina = new DefaultMutableTreeNode(rodzina.getSpouse());
                root.add(nodeRodzina);
                List<ReferenceListElement> dzieci = rodzina.getChildren();
                if (dzieci != null && dzieci.size() > 0) {
                    for (ReferenceListElement dziecko : dzieci) {
                        DefaultMutableTreeNode nodeDziecko = new DefaultMutableTreeNode(dziecko);
                        nodeRodzina.add(nodeDziecko);
                    }
                }
            }
        }
        TreeModel treeModel = new DefaultTreeModel(root);
        tRodzina.setModel(treeModel);

        List<ReferenceListElement> dokumentyWybrane = new ArrayList<ReferenceListElement>(daneOsoby.getDokumenty());
        tDokumenty.setSelectedItems(dokumentyWybrane);
    }

    private void odswiezListy() {
        List<ReferenceListElement> zwiazki = DatabaseDelegate.getRelationships();
        List<ReferenceListElement> wszystkieDokumenty = DatabaseDelegate.getDocuments();
        tParents.removeAllItems();
        tParents.addItem(new String("<Brak>"));
        for (ReferenceListElement zwiazek : zwiazki) {
            tParents.addItem(zwiazek);
        }

        tDokumenty.setAllItems(wszystkieDokumenty);
    }

    public boolean zapiszDane() {
        if (trybPracy == TrybPracy.DODAWANIE)
            daneOsoby = new PersonEditData();

        if (tSex.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Wybierz p�e�", "B��d", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (tSex.getSelectedIndex() == 1) {
            daneOsoby.setSex("male");
        } else {
            daneOsoby.setSex("female");
        }
        daneOsoby.setNames(tNames.getText().trim());
        daneOsoby.setSurname(tSurname.getText().trim());
        daneOsoby.setBirthDate(tBirthDate.getText().trim());
        daneOsoby.setBirthPlace(tBirthPlace.getText().trim());
        daneOsoby.setDeathDate(tDeathDate.getText().trim());
        daneOsoby.setDeathPlace(tDeathPlace.getText().trim());
        daneOsoby.setBurialDate(tBurialDate.getText().trim());
        daneOsoby.setBurialPlace(tBurialPlace.getText().trim());
        daneOsoby.setEducation(tEducation.getText().trim());
        daneOsoby.setOccupation(tOccupation.getText().trim());
        daneOsoby.setResidence(tResidence.getText().trim());
        daneOsoby.setDescription(tDescription.getText().trim());
        if (tParents.getSelectedIndex() == 0) {
            daneOsoby.setParents(null);
        } else {
            daneOsoby.setParents((ReferenceListElement) tParents.getSelectedItem());
        }
        daneOsoby.setDokumenty(new HashSet<ReferenceListElement>(tDokumenty.getSelectedItems()));

        if (trybPracy == TrybPracy.EDYCJA) {
            DatabaseDelegate.saveEditedPerson(daneOsoby);
        } else {
            DatabaseDelegate.saveNewPerson(daneOsoby);
        }

        return true;
    }

    private void wyczyscPola() {
        tNames.setText("");
        tSurname.setText("");
        tBirthDate.setText("");
        tBirthPlace.setText("");
        tDeathDate.setText("");
        tDeathPlace.setText("");
        tBurialDate.setText("");
        tBurialPlace.setText("");
        tResidence.setText("");
        tEducation.setText("");
        tOccupation.setText("");
        tDescription.setText("");
        tParents.setSelectedIndex(0);
        tRodzina.setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
    }

    @Override
    public void usunObiekt(Long id) {
        int result = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz usun�� t� osob�?", "Usuwanie osoby", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            DatabaseDelegate.deletePersonOsobe(id);
        }
    }

    @Override
    public String getTitle() {
        if (trybPracy == TrybPracy.DODAWANIE) {
            return "Dodawanie nowej osoby";
        } else {
            return "Edycja danych osoby";
        }
    }

    @Override
    public DiagramInfoStruct getDiagramInfo(Long id) {
        PersonEditData daneOsoby = DatabaseDelegate.getPersonEditData(id);
        DiagramInfoStruct diagramInfo = new DiagramInfoStruct();
        diagramInfo.idKorzenia = daneOsoby.getId();
        diagramInfo.nazwa = daneOsoby.getNames() + " " + daneOsoby.getSurname() + " (" + daneOsoby.getId() + ")";
        diagramInfo.description = "Diagram osoby '" + daneOsoby.getNames() + " " + daneOsoby.getSurname() + "'";
        return diagramInfo;
    }
}