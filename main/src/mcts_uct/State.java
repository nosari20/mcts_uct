package mcts_uct;

import java.util.List;

/**
 * Created by ACH02 on 07/12/2016.
 */
public  abstract class State {

    private boolean computer;


    public State(boolean player){
        this.computer = player;
    }

    public boolean isComputer(){
        return computer;
    }


    public enum End {NO, DRAW, COMPUTER_WIN, HUMAN_WIN }

    public abstract End end();

    public abstract List<Action> validActions();

    public abstract State use(Action action);

    public abstract boolean isValideAction(Action action);

    public abstract State copy();

    public abstract String toString();




}
