package connect4;

import algorithms.AI;
import algorithms.MTCS_UCT;
import algorithms.RANDOM;
import algorithms.tree.Action;
import algorithms.tree.State;

import java.util.Scanner;

import static java.lang.Math.sqrt;


/**
 * Created by ACH02 on 07/12/2016.
 */
public class GameC4 {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.println("Choose who start");
        System.out.println("Human (default): 0 | Computer : 1");
        int player = sc.nextInt();
        StateC4 state = new StateC4(0);
        if(player == 1){
            state = new StateC4(1);
        }


        System.out.println("Choose difficulty");
        System.out.println("MCTS UCT AI (default): 0 | Random AI : 1");
        int iaChoosed = sc.nextInt();
        AI ia = new MTCS_UCT(sqrt(2),1);
        if(iaChoosed == 1){
            ia = new RANDOM();
        }

        StateC4 nextState;

        System.out.println(state);
        System.out.println('\n');


        do{


            if(state.getPlayer() == 0){

                System.out.println("Enter column");
                Action action = new ActionC4(sc.nextInt());
                if(state.isValideAction(action)){
                    nextState = (StateC4) state.use(action);
                }else{
                    while(!state.isValideAction(action)){
                        System.out.println("Action invalid");
                        System.out.println("Enter column");
                        action = new ActionC4(sc.nextInt());
                    }
                    nextState = (StateC4) state.use(action);
                }

            }else{
                System.out.println("Computer is playing...");
                nextState = (StateC4) state.use(ia.search(state));
            }





            state = nextState;
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
