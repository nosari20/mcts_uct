package mcts_uct;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class Mcts_uct {

    private static boolean debug = false;


    public static Action execute(State state, double max_time, double c){

        long startTime = System.currentTimeMillis() / 1000;
        final Node root = new Node(null, state, null);


        int iter = 0;



        do {

            System.out.println("Itération : " + iter);

            if (debug) {
                System.out.println("Root simus : " + root.getNb_simulation());

                System.out.println("*****************************************");
                System.out.println("Itération : " + iter);
                iter++;

                System.out.println("Select a node");


                System.out.println(root.showStatus());
            }
            //System.out.println(root);
            Node node = select(root,c);
            if(debug) {
                System.out.println("Node" + node.getNo());
                System.out.println("Node simus : " + node.getNb_simulation());
                System.out.println("Node" + node.getStateStatus());
                System.out.println(node.showStatus());
                System.out.println("Node reward : " + node.B(c));
                System.out.println("Node " + node + " selected");
            }


            if(debug)
            System.out.println("Expand a child");
            node = expand(node);
            if(debug)
            System.out.println("Child expanded");


            if(debug)
            System.out.println("Simulating...");
            Node endNode = simulate(node);
            if(debug)
            System.out.println("EndNode : "+node.getNb_simulation());
            if(debug)
            System.out.println("Reward : " +endNode.reward());


            if(debug)
            System.out.println("Updating..");
            update(endNode, endNode.reward());
            if(debug)
            System.out.println("Updated");



            iter++;

        }while(startTime > ((System.currentTimeMillis() / 1000) - max_time));

        if(debug)
        System.out.println("********************* BEST ******************");
        if(debug)
        System.out.println(root.best(c).action());

        System.out.println("iterations : " + iter);
        System.out.println(root.toString("",c));
        return root.best(c).action();

    }


    public static Node select(Node n, double c){

        if(n.isFinal()){
            return n;
        }else{
            if(n.isFullyExpanded() || !n.hasChild() ){
                return n;
            }else{

                return select(n.best(c),c);
            }
        }

    }

    public static Node expand(Node n){
        return n.expand();
    }

    public static Node simulate(Node n){
        n.incrementSimulation();
        if(n.isFinal()){
            return n;
        }else{
            return simulate(n.expand());
        }
    }

    public static void update(Node n, double reward){
        if(n != null){

            n.addReward(reward);
            update(n.parent(),reward);
        }
    }



}
