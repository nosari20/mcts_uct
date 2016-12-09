package connect4;

import mcts_uct.Action;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class ActionC4 extends mcts_uct.Action {

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

    public boolean equals(Action a) {
        return ((ActionC4)a).getColumn() == this.column;
    }
}
