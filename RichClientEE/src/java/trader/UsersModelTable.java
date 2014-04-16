/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trader;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Manixab
 */
public class UsersModelTable extends AbstractTableModel {

    private Object donnees[][];
    private String titres[];

    public UsersModelTable(Object[][] donnees, String[] titres) {
        this.donnees = donnees;
        this.titres = titres;
    }
   
    @Override
    public int getRowCount() {
        return donnees.length; 
    }

    @Override
    public int getColumnCount() {
        return donnees[0].length; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return donnees[rowIndex][columnIndex];
    }
    
}
