package connect4;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Scanner;

import algorithms.AI;
import algorithms.MTCS_UCT;
import algorithms.MTCS_UCT_Biased;
import algorithms.MTCS_UCT_MaxRob;
import algorithms.RANDOM;
import algorithms.tree.Action;
import algorithms.tree.State;

import java.io.*;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class GameC4 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Choose who start");
		System.out
				.println("Human (default): 0 | Computer : 1 | IA vs IA : 666");
		System.out.println("IA vs IA joue 10 parties (SKYNET vs HAL)"
				+ "\n SKYNET jouant toujour en 1er"
				+ "\n affiche a chaque partie qui a gagner"
				+ "\n le temps de la partie" + "\n son nombre de tour");
		int player = sc.nextInt();
		StateC4 state = new StateC4(0);

		switch (player) {

		case 666:

			// File f = new File("duel_IA.txt");

			/*
			 * for (int z = 1; z < 6; z++) { for (int j = 1; j < 6; j++) {
			 */

			int vsky = 0;
			int vhal = 0;
			int draw = 0;

			// for (int i = 1; i < 2; i++) {
			// System.out.println(0.5*i);

			int temps = (int) System.currentTimeMillis();
			int ts = 1000;
			int th = 1000;

			AI skynet = new MTCS_UCT(sqrt(2), 1);
			AI hal = new MTCS_UCT(sqrt(2), 1);

			boolean irst = false;
			int sky = 0;
			int al = 0;
			String s = "";

			for (int i = 1; i <= 10; i++) {

				temps = (int) System.currentTimeMillis();

				System.out.println("Partie " + i + "/10");

				if (!irst) {

					System.out.println("choix de l'algo de SKYNET");
					System.out
							.println("MCTS UCT AI (default): 0 | MCTS UCT Biased AI : 1");
					sky = sc.nextInt();
					switch (sky) {

					case 1:
						skynet = new MTCS_UCT_Biased(sqrt(2), 1);
						System.out
								.println("temps de reflexion de SKYNET (en milliseconde)");

						ts = sc.nextInt();
						((MTCS_UCT_Biased) skynet).temps = ts;
						break;

					default:
						System.out
								.println("temps de reflexion de SKYNET (en milliseconde)");
						ts = sc.nextInt();
						((MTCS_UCT) skynet).temps = ts;
						break;

					}
					System.out.println("choix de l'algo de HAL");
					System.out
							.println("MCTS UCT AI (default): 0 | MCTS UCT Biased AI : 1");
					al = sc.nextInt();
					switch (al) {

					case 1:
						hal = new MTCS_UCT_Biased(sqrt(2), 1);
						System.out
								.println("temps de reflexion de HAL (en milliseconde)");
						th = sc.nextInt();
						((MTCS_UCT_Biased) hal).temps = th;

						break;

					default:

						((MTCS_UCT) hal).temps = th;
						System.out
								.println("temps de reflexion de HAL (en milliseconde)");
						th = sc.nextInt();
						break;

					}
					irst = true;
				} else {

					switch (sky) {

					case 1:
						skynet = new MTCS_UCT_Biased(sqrt(2), 1);

						((MTCS_UCT_Biased) skynet).temps = ts;
						break;

					default:

						((MTCS_UCT) skynet).temps = ts;
						break;

					}

					switch (al) {

					case 1:
						hal = new MTCS_UCT_Biased(sqrt(2), 1);

						((MTCS_UCT_Biased) hal).temps = th;

						break;

					default:

						((MTCS_UCT) hal).temps = th;

						break;

					}

				}

				StateC4 battle = new StateC4(0);

				do {

					if (battle.getPlayer() == 0) {

						// System.out.print(" \tSKYNET \tis \tplaying...");
						battle = (StateC4) battle.use(skynet.search(battle));
						// System.out.println(skynet.reponse());

					} else {
						// System.out.print(" \tHAL \tis \tplaying...");
						battle = (StateC4) battle.use(hal.search(battle));
						// System.out.println(hal.reponse());
					}

					// System.out.println("\tpartie " + i + "/" + 10);

					// battle = battle;
					// System.out.println(battle);
					// System.out.println('\n');
					System.out.print(".");

				} while (battle.end() == State.End.NO);

				System.out.println("");

				switch (battle.end()) {
				case COMPUTER_WIN:
					System.out.print(" HAL WIN");
					vhal++;
					break;
				case HUMAN_WIN:
					System.out.print(" SKYNET WIN");
					vsky++;
					break;
				default:
					System.out.print(" DRAW");
					draw++;
				}
				temps = (int) System.currentTimeMillis() - temps;
				temps = temps / 1000;
				if (temps / 60 > 0) {
					temps = temps / 60;
				}
				System.out.print(" temps de la partie : " + temps + "\n");

				s = "\n victoire SKYNET " + vsky + "\n victoire HAL " + vhal
						+ "\n victoire DRAW " + draw + "\n SKYNET temps " + ts
						+ "\n HAL temps " + th;

			}

			System.out.println(s + "\n");

			break;

		default:
			if (player == 1) {
				state = new StateC4(1);
			}

			System.out.println("Choose difficulty");
			System.out
					.println("MCTS UCT AI (default): 0 | MCTS UCT Biased AI : 1 | "
							+ "Random AI : 2 | MCTS UCT Biased AI : 3");
			int iaChoosed = sc.nextInt();
			AI ia = null;

			switch (iaChoosed) {
			case 3:
				ia = new MTCS_UCT_MaxRob(sqrt(2), 1);
				System.out.println("temps reflexion algo en milliseconde : ");
				((MTCS_UCT_MaxRob) ia).temps = sc.nextInt();
				break;
			case 2:
				ia = new RANDOM();
				break;
			case 1:
				ia = new MTCS_UCT_Biased(sqrt(2), 1);
				System.out.println("temps reflexion algo en milliseconde : ");
				((MTCS_UCT_Biased) ia).temps = sc.nextInt();
				break;
			case 0:
			default:
				ia = new MTCS_UCT(sqrt(2), 1);
				System.out.println("temps reflexion algo en milliseconde : ");
				((MTCS_UCT) ia).temps = sc.nextInt();
				break;
			}

			if (ia == null) {
				System.out.println("erreur improbable");
				System.exit(13);
			}

			StateC4 nextState;

			System.out.println(state);
			System.out.println('\n');

			do {

				if (state.getPlayer() == 0) {

					System.out.println("Enter column");
					Action action = new ActionC4(sc.nextInt());
					if (state.isValideAction(action)) {
						nextState = (StateC4) state.use(action);
					} else {
						while (!state.isValideAction(action)) {
							System.out.println("Action invalid");
							System.out.println("Enter column");
							action = new ActionC4(sc.nextInt());
						}
						nextState = (StateC4) state.use(action);
					}

				} else {
					System.out.println("Computer is playing...");
					nextState = (StateC4) state.use(ia.search(state));
					System.out.println(ia.reponse());
				}

				state = nextState;
				System.out.println(state);
				System.out.println('\n');

			} while (state.end() == State.End.NO);

			System.out.println(state);
			System.out.println('\n');

			switch (state.end()) {
			case COMPUTER_WIN:
				System.out.println("YOU LOSE");
				break;
			case HUMAN_WIN:
				System.out.println("YOU WIN");
				break;
			default:
				System.out.println("DRAW");
			}

			break;

		}

	}

}
