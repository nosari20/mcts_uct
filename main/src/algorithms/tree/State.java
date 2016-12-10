package algorithms.tree;

import java.util.List;

/**
 * Created by ACH02 on 07/12/2016.
 */
public  abstract class State {

    private boolean computer;


    public State(boolean player){
        this.computer = player;
    }


    public enum End {NO, DRAW, COMPUTER_WIN, HUMAN_WIN }

    public boolean isComputer(){
        return computer;
    }

    public abstract boolean isNonTerminal();

    public abstract End end();

    public abstract boolean isValideAction(Action action);

    public abstract List<Action> validActions();

    public abstract State use(Action action);

    public abstract double reward();

    public abstract String toString();


}
