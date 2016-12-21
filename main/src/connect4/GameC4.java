package connect4;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Scanner;

import algorithms.AI;
import algorithms.MTCS_UCT;
import algorithms.MTCS_UCT_Biased;
import algorithms.RANDOM;
import algorithms.tree.Action;
import algorithms.tree.State;

import java.io.*;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class GameC4 {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("Choose who start");
		System.out.println("Human (default): 0 | Computer : 1 | IA vs IA : 666");
		int player = sc.nextInt();
		StateC4 state = new StateC4(0);

		switch (player) {

		case 666:

			File f = new File("duel_IA.txt");

			/*for (int z = 1; z < 6; z++) {
				for (int j = 1; j < 6; j++) {*/

					int vsky = 0;
					int vhal = 0;
					int draw = 0;

					//for (int i = 1; i < 2; i++) {
						// System.out.println(0.5*i);

						int temps = (int) System.currentTimeMillis();

						AI skynet = new MTCS_UCT(sqrt(2), 1);
						
						int ts=3000;
						int th=3000;

						((MTCS_UCT) skynet).temps = ts ;

						AI hal = new MTCS_UCT(sqrt(2), 1);

						((MTCS_UCT) hal).temps = th ;

						StateC4 battle = new StateC4(0);

						do {

							if (battle.getPlayer() == 0) {

								// System.out.print(" \tSKYNET \tis \tplaying...");
								battle = (StateC4) battle.use(skynet
										.search(battle));
								// System.out.println(skynet.reponse());

							} else {
								// System.out.print(" \tHAL \tis \tplaying...");
								battle = (StateC4) battle.use(hal
										.search(battle));
								// System.out.println(hal.reponse());
							}

							// System.out.println("\tpartie " + i + "/" + 10);

							// battle = battle;
							 System.out.println(battle);
							// System.out.println('\n');
							//System.out.print(".");

						} while (battle.end() == State.End.NO);

						System.out.println("");

						switch (battle.end()) {
						case COMPUTER_WIN:
							// System.out.println("HAL WIN");
							vhal++;
							break;
						case HUMAN_WIN:
							// System.out.println("SKYNET WIN");
							vsky++;
							break;
						default:
							// System.out.println("DRAW");
							draw++;
						}
						temps = (int) System.currentTimeMillis() - temps;
						temps = temps / 1000;
						if (temps / 60 > 0) {
							temps = temps / 60;
						}
						// System.out.println("temps de la partie : " + temps);

					//}

					String s = "\n victoire SKYNET " + vsky
							+ "\n victoire HAL " + vhal + "\n victoire DRAW "
							+ draw + "\n SKYNET temps " + ts 
							+ "\n HAL temps " + th ;
					

					try {
						FileWriter fw = new FileWriter(f);

						fw.write(s);
						fw.write("\r\n\n\n");
						System.out.println(s);

						fw.close();
					} catch (IOException exception) {
						System.out.println("Erreur lors de la lecture : "
								+ exception.getMessage());
					}
				/*}
			}*/

			// System.out.println("victoire SKYNET " + vsky);
			// System.out.println("victoire HAL " + vhal);
			// System.out.println("victoire DRAW " + draw);

			break;

		default:
			if (player == 1) {
				state = new StateC4(1);
			}

			System.out.println("Choose difficulty");
			System.out.println("MCTS UCT AI (default): 0 | MCTS UCT Biased AI : 1 | Random AI : 2 ");
			int iaChoosed = sc.nextInt();
			AI ia = new MTCS_UCT(sqrt(2), 1);
			if (iaChoosed == 2) {
				ia = new RANDOM();
			}
			if(iaChoosed == 1){
				ia = new MTCS_UCT_Biased(sqrt(2), 1);
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

	public void tableau(ArrayList<Valeur> alv) {

	}

	public class Valeur {

		int sky;
		int hal;
		int egal;

		public Valeur(int s, int h, int d) {

			sky = s;
			hal = h;
			egal = d;

		}

	}
}
