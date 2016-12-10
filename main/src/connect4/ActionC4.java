package connect4;

import algorithms.tree.Action;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class ActionC4 extends Action {

    private int column;

    public ActionC4(int column){
        this.column = column;
    }

    public int getColumn(){
        return  column;
    }

    public String toString() {
        return "Column " +this.column;
    }

    public boolean equals(Object a) {
        return ((ActionC4)a).getColumn() == this.column;
    }
}
