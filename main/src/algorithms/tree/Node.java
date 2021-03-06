package algorithms.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class Node {

    private Action  action;

    private State state;

    private Node parent;
    private List<Node> childrens;

    private int nb_victories;
    private int nb_simulations;
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

    public int id(){
        return this.no;
    }

    public Node parent(){
        return this.parent;
    }

    public State state(){
        return this.state;
    }

    public List<Node> childrens(){
        return this.childrens;
    }

    public boolean isNonTerminal(){
        return this.state.isNonTerminal();
    }

    public boolean isFullyExpanded(){
        return this.childrens.size() == this.state.validActions().size();
    }

    public Action action(){
        return this.action;
    }

    public double simulations(){
        return this.nb_simulations;
    }

    public void visit(){
        this.nb_simulations++;
    }

    public double victories(){
        return this.nb_victories;
    }

    public void reward(double rk){
        this.nb_victories += rk;
    }

    public  void addChild(Node i){
        this.childrens.add(i);
    }

    public String toString(){
        return this.toString("", false);
    }

    public String toStringFull(){
        return this.toString("", true);
    }

    private String toString(String space, boolean full){

        String str = "";

        str+="\n"+space+"| -> Node : " + no + " Simus : " + this.nb_simulations + " Children :" + this.childrens.size();
        space += " ";

        for (Node child:
                this.childrens) {
            if(child.simulations()>0 || full){
                str+=child.toString(space, full);
            }else{
                str+="\n"+space+"|~~>";
            }

        }

        return str;
    }

}


