package algorithms;

import algorithms.tree.Action;
import algorithms.tree.State;

import java.util.List;
import java.util.Random;

/**
 * Created by ACH02 on 11/12/2016.
 */
public class RANDOM implements AI {
    @Override
    public Action search(State s0) {

        List<Action> va = s0.validActions();
        return va.get(new Random().nextInt(va.size()));
    }
}
