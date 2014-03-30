/*
 * LogList.java
 *
 * Created on 12 fevrier 2007, 10:38
 *
 */

package market;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Cet objet contient la liste des evenments de marche
 */
public class LogList extends ArrayList implements Serializable{
    /**
     * Creates a new instance of LogList
     */
    public LogList() {
        super();
    }
    
    /**
     * Cette methode retourne la liste inversee des evennements de sorte a l'afficher a l'envers dans la JTable
     */
    public boolean addReverse(Object o) {
        int kingSize = this.size();
        this.add(this.get(kingSize-1));
        for(int i=kingSize-1;i>0;i--){
            this.set(i,this.get(i-1));
        }
        this.set(0,o);
        return true;
    }
}
