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
public class MTCS_UCT_Biased implements AI {

	private double budget;
	private double compromise;

	public int temps = 3000;
	public int tour = 0;
	Node best = null;

	public MTCS_UCT_Biased(double budget) {
		this.budget = budget;
		this.compromise = sqrt(2);
	}

	public MTCS_UCT_Biased(double budget, double compromise) {
		this.budget = budget;
		this.compromise = compromise;
	}

	public Action search(State s0) {
		int a = temps;

		int b;// = 1000;

		b = a;

		long startTime = System.currentTimeMillis() / a;

		// Create root node i0 with state s0
		Node i0 = new Node(null, s0, null);

		Node il;

		int loop = 0; // nombre de simulation

		do {

			Node ie = treePolicy(i0);
			if(ie.state().end() == State.End.COMPUTER_WIN){
				best = bestChild(i0);
				return ie.action();  // max
			}
			double rk = defaultPolicy(ie);
			backup(ie, rk);

			loop++;
			// System.out.println(startTime +" > "+ ((System.currentTimeMillis()
			// / b) - this.budget));

		} while (startTime > ((System.currentTimeMillis() / b) - this.budget));
		tour = loop;
		best = bestChild(i0);
		return bestChild(i0).action();
	}

	private Node treePolicy(Node i0) {
		Node i = i0;
		while (i.isNonTerminal()) {
			if (!i.isFullyExpanded()) {
				return expand(i);
			} else {
				i = bestChild(i);
			}
		}
		return i;
	}

	private Node expand(Node ie) {
		List<Node> is = ie.childrens();
		List<Action> va = ie.state().validActions();
		for (Node i : is) {
			va.remove(i.action());
		}
		//Regarder s'il existe une action gagnante si oui la choisir sinon random
		Action ua = va.get(new Random().nextInt(va.size()));
		for(Action a : va){
			if(ie.state().use(a).end()== State.End.COMPUTER_WIN){
				ua = a;
				break;
			}
		}

		Node in = new Node(ie, ie.state().use(ua), ua);
		ie.addChild(in);
		return in;
	}

	private double defaultPolicy(Node ie) {
		Node i = ie;
		while (i.isNonTerminal()) {
			i = expand(i);
			i.visit();
		}
		return i.state().reward();
	}

	private void backup(Node il, double rk) {
		Node i = il;
		while (i != null) {
			i.visit();
			i.reward(rk);
			i = i.parent();
		}
	}

	private Node bestChild(Node i0) {
		List<Node> ic = i0.childrens();
		double tmpb;
		double bmax = -Double.MAX_VALUE;
		Node ib = null;

		for (Node i : ic) {
			tmpb = B(i);
			if (tmpb > bmax) {
				bmax = tmpb;
				ib = i;
			}
		}
		return ib;
	}

	private double B(Node i) {
		double coeff = 1;
		if (i.state().isComputer()) {
			coeff = -1;
		}

		return coeff * (i.victories() / i.simulations()) + this.compromise
				* sqrt(log(i.parent().simulations()) / i.simulations());

	}

	public String reponse() {
		
		if(best==null){
			return "";
		}
		String s = "\n";
		s = s + "nombre de simulation : " + tour;
		String tmp = (B(best) * 100) + " ";
		s = s +("\ntaux de victoire : "
				+ tmp.substring(0, tmp.indexOf(".")) + " % \n");

		return s;
	}
}
