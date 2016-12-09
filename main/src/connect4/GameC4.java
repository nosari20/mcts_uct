package connect4;

import mcts_uct.Action;
import mcts_uct.Mcts_uct;
import mcts_uct.State;

import java.util.List;
import java.util.Scanner;

import static java.lang.StrictMath.sqrt;


/**
 * Created by ACH02 on 07/12/2016.
 */
public class GameC4 {

    public static void main(String[] args){
        StateC4 state = new StateC4(0);
        Scanner sc = new Scanner(System.in);

        StateC4 nextState;

        System.out.println(state);
        System.out.println('\n');
        do{


            if(state.getPlayer() == 0){
                System.out.println("Entrez un colonne à jouer");
                Action action = new ActionC4(sc.nextInt());
                if(state.isValideAction(action)){
                    nextState = (StateC4) state.use(action);
                }else{
                    while(!state.isValideAction(action)){
                        System.out.println("Coup impossible");
                        System.out.println("Entrez un colonne à jouer");
                        action = new ActionC4(sc.nextInt());
                    }
                    nextState = (StateC4) state.use(action);
                }

            }else{
                System.out.println("Computer is playing...");
                nextState = (StateC4) state.use(Mcts_uct.execute(state,5, sqrt(2)));
            }





            state = nextState;

            System.out.println("**************************  GAME  **************************");
            System.out.println(state);
            System.out.println('\n');

        }while(state.end() == State.End.NO);


        System.out.println(state);
        System.out.println('\n');

        switch (state.end()){
            case COMPUTER_WIN:
                System.out.println("YOU LOSE");
                break;
            case HUMAN_WIN:
                System.out.println("YOU WIN");
                break;
            default:
                System.out.println("DRAW");
        }
    }


}
