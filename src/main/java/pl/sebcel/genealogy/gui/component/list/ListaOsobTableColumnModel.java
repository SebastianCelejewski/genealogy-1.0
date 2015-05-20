package pl.sebcel.genealogy.gui.component.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import pl.sebcel.genealogy.dto.ElementListyOsobStruct;

class ListaOsobTableColumnModel extends DefaultTableColumnModel implements ComplexTableModel {
	
	public static final long serialVersionUID = 0L;
	
	private List<ElementListyOsobStruct> dane = new ArrayList<ElementListyOsobStruct>();
	private String[] kolumny = new String[] {"Id","Names i surname","Description"};

	public ListaOsobTableColumnModel() {
		super();
		TableColumn columnId = new TableColumn(0,20);
		TableColumn columnNazwa = new TableColumn(1,280); 
		TableColumn columnDescription = new TableColumn(2,400);
		
		columnId.setHeaderValue(kolumny[0]);
		columnNazwa.setHeaderValue(kolumny[1]);
		columnDescription.setHeaderValue(kolumny[2]);
		
		addColumn(columnId);
		addColumn(columnNazwa);
		addColumn(columnDescription);
	}
	
	public void addTableModelListener(TableModelListener l) {
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return kolumny.length;
	}

	public String getColumnName(int columnIndex) {
		return kolumny[columnIndex]; 
	}

	public int getRowCount() {
		return dane.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex==0) return dane.get(rowIndex).id;
		if (columnIndex==1) return dane.get(rowIndex).nazwa;
		if (columnIndex==2) return dane.get(rowIndex).description;
		return "";
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	public void wpiszDane(List<ElementListyOsobStruct> listaOsob) {
		this.dane = listaOsob;
	}
}