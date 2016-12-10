package algorithms;

import algorithms.tree.Action;
import algorithms.tree.Node;
import algorithms.tree.State;

import java.util.List;
import java.util.Random;

import static java.lang.Math.log;
import static java.lang.Math.sqrt;

/**
 * Created by ACH02 on 09/12/2016.
 */
public class MTCS_UCT {

    private double budget;
    private double compromise;

    public MTCS_UCT(double budget) {
        this.budget = budget;
        this.compromise = sqrt(2);
    }

    public MTCS_UCT(double budget, double compromise) {
        this.budget = budget;
        this.compromise = compromise;
    }

    public Action search(State s0){
        long startTime = System.currentTimeMillis() / 1000;

        //Create root node i0 with state s0
        Node i0 = new Node(null,s0,null);

        Node il;

        int loop = 0;

        do{

            System.out.println("\nLoop : " +loop++);
            //System.out.println(i0);
            System.out.println("TreePolicy...");
            Node ie = treePolicy(i0);
            System.out.println("Expand...");
            il = expand(ie);

            System.out.println("DeafaultPolicy...");
            double rk = defaultPolicy(il);
            System.out.println("Backup...");
            backup(il,rk);

        }while(startTime > ((System.currentTimeMillis() / 1000) - this.budget));

        return bestChild(il).action();
    }


    private Node treePolicy(Node i0){
        Node i = i0;
        while(i0.isNonTerminal()){
            if(!i.isFullyExpanded()){
                return expand(i);
            }else{
                System.out.println("Cildren :" +i.childrens().size());
                i = bestChild(i);
            }
        }
        return i;
    }

    private Node expand(Node ie){
        List<Node> is = ie.childrens();
        List<Action> va = ie.state().validActions();
        for (Node i:
                is) {
           va.remove(i.action());
        }
        Action ua = va.get(new Random().nextInt(va.size()));
        Node in = new Node(ie,ie.state().use(ua),ua);
        ie.addChild(in);
        return in;
    }

    private double defaultPolicy(Node ie){
        Node i = ie;
        while(i.isNonTerminal()){
            i = expand(i);
        }
        return i.state().reward();
    }

    private void backup(Node il, double rk){
        Node i = il;
        while(i != null){
            i.visit();
            i.reward(rk);
            i = i.parent();
        }
    }

    private Node bestChild(Node i0){
        List<Node> ic = i0.childrens();
        double tmpb;
        double bmax = -Double.MAX_VALUE;
        Node ib = null;
        System.out.println("++++++++++++++++++++++++");
        System.out.println("Children :" +ic.size());
        for (Node i:
                ic) {

            System.out.println(i);

        }
        System.out.println("++++++++++++++++++++++++");

        for (Node i:
             ic) {
            tmpb = B(i);
            if(tmpb > bmax){
                bmax = tmpb;
                ib = i;
            }
        }
        return ib;
    }

    private double B(Node i){
        double coeff = 1;
        if(!i.state().isComputer()){
            coeff = -1;
        }
        System.out.println("Node for b " + i.id());
        double b;
        if(i.simulations() > 0) {
            b = coeff * (i.victories() / i.simulations()) + this.compromise * sqrt(log(i.parent().simulations()) / i.simulations());
        }else{
            b = 1/0;//fail
        }
        return b;
    }
}
