package mcts_uct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class Node {

    private Action  action;

    private State state;

    private Node parent;
    private List<Node> childrens;

    private int nb_victory;
    private int nb_simulation;
    private int no;


    private static int nbNodes = 0;


    public Node(Node parent, State state, Action action){
        this.parent = parent;
        this.childrens = new ArrayList<Node>();
        this.state = state;
        this.action = action;

        this.no = nbNodes;
        nbNodes ++;
    }

    public Node expand(){

        List<Action>  actions = this.state.validActions();
        List<Node> bros = this.childrens();
        for (Node bro:bros ) {
            actions.remove(bro.action());
        }
        Random rand = new Random();
        Action action = actions.get(rand.nextInt(actions.size())); //TODO
        Node child = this.addChild(action);
        return child;
    }

    public  Node addChild(Action action){
        Node child = new Node(this, this.state.use(action), action);
        this.childrens.add(child);
        return child;
    }

    public List<Node> brothers(){
        return this.parent().childrens();
    }

    public List<Node> childrens(){
        return this.childrens;
    }

    public boolean isFinal(){
        return this.state.end() != State.End.NO;
    }

    public State.End getStateStatus(){
        return this.state.end();
    }

    public String showStatus(){
        return this.state.toString();
    }

    public int getNo(){
        return this.no;
    }


    public boolean isFullyExpanded(){
        return this.state.end() == State.End.DRAW;
    }

    public boolean hasChild(){
        return this.childrens.size() != 0;
    }

    public Node parent(){
        return this.parent;
    }

    public Action action(){
        return this.action;
    }

    public void incrementSimulation() {
        this.nb_simulation += 1;
    }

    public int getNb_simulation(){
        return this.nb_simulation;
    }

    public void addReward(double c) {
        this.nb_victory += c;
    }

    public Node best(double c){
        double tmpB;
        double b = -Double.MAX_VALUE;
        Node node;
        Node best = null;
        for (Node child : this.childrens) {
            tmpB =  child.B(c);
            if(tmpB > b) {
                b = tmpB;
                best = child;
            }
        }
        return best;
    }

    public double B(double c){
        double coeff = 1;
        if(!this.state.isComputer()){
            coeff = -1;
        }
        double b;
        if(this.nb_simulation > 0) {
            b = coeff * (this.nb_victory / nb_simulation) + c * sqrt(log(this.parent.getNb_simulation()) / this.nb_simulation);
        }else{
            b = 0;
        }
        return b;
    }

    public double reward(){
        if(this.state.end() != State.End.COMPUTER_WIN){
            return 0;
        }else{
            return 1;
        }
    }


    public State getStatus() {
        return this.state;
    }


    public String toString(String space,double c){

        String str = "";

        //str+="\n"+space+"Node : " + no;
        str+="\n"+space+"Simus : " + this.nb_simulation;
        space += " ";

        for (Node child:
             this.childrens) {
            str+=child.toString(space,c);
        }

        return str;
    }
}


