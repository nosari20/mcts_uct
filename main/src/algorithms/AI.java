package algorithms;

import algorithms.tree.Action;
import algorithms.tree.State;

/**
 * Created by ACH02 on 11/12/2016.
 */
public interface AI {
	
	//int temps = 1000 ;

    public Action search(State s0);
    
    public String reponse();
}


