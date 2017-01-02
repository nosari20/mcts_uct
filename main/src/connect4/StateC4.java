package connect4;

import algorithms.tree.Action;
import algorithms.tree.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACH02 on 07/12/2016.
 */
public class StateC4 extends State {

	private int[][] plate;
	private int player;

	public StateC4(int player) {
		super(player == 1);
		plate = new int[6][7];

		for (int i = 0; i < plate.length; i++) {
			for (int j = 0; j < plate[0].length; j++) {
				plate[i][j] = -1;
			}
		}
		this.player = player;
	}

	public StateC4(int player, int[][] plate) {
		super(player == 1);
		this.plate = plate;
		this.player = player;
	}

	public boolean isNonTerminal() {
		return this.end() == End.NO;
	}

	public End end() {
		int i, j, k, n = 0;

		boolean draw = true;
		for (j = 0; j < plate[0].length; j++) {
			draw = draw && (plate[0][j] != -1);
		}
		if (draw) {
			return End.DRAW;
		}

		for (i = 0; i < plate.length; i++) {
			for (j = 0; j < plate[0].length; j++) {

				if (this.plate[i][j] != -1) {
					n++; // nb coups joués

					// plate.length
					k = 0;
					while (k < 4 && j + k < plate[0].length
							&& this.plate[i][j + k] == this.plate[i][j])
						k++;
					if (k == 4) {
						return this.plate[i][j] == 1 ? End.COMPUTER_WIN
								: End.HUMAN_WIN;
					}

					// plate[0].length
					k = 0;
					while (k < 4 && i + k < plate.length
							&& this.plate[i + k][j] == this.plate[i][j])
						k++;
					if (k == 4) {
						return this.plate[i][j] == 1 ? End.COMPUTER_WIN
								: End.HUMAN_WIN;
					}

					// diagonales
					k = 0;
					while (k < 4 && i + k < plate.length
							&& j + k < plate[0].length
							&& this.plate[i + k][j + k] == this.plate[i][j])
						k++;
					if (k == 4) {
						return this.plate[i][j] == 1 ? End.COMPUTER_WIN
								: End.HUMAN_WIN;
					}

					k = 0;
					while (k < 4 && i + k < plate.length && j - k >= 0
							&& this.plate[i + k][j - k] == this.plate[i][j])
						k++;
					if (k == 4) {
						return this.plate[i][j] == 1 ? End.COMPUTER_WIN
								: End.HUMAN_WIN;
					}

				}
			}
		}

		return End.NO;
	}

	public List<Action> validActions() {

		List<Action> actions = new ArrayList<Action>();

		for (int j = 0; j < plate[0].length; j++) {
			if (plate[0][j] == -1) {
				actions.add(new ActionC4(j));
			}
		}
		return actions;
	}

	public State use(Action action) {

		int j = ((ActionC4) (action)).getColumn();
		int i;
		for (i = 1; i < plate.length; i++) {
			if (plate[i][j] != -1) {
				break;
			}
		}
		int posi = i - 1;
		int posj = j;
		int[][] newPlate = new int[plate.length][plate[0].length];

		for (i = 0; i < newPlate.length; i++) {
			for (j = 0; j < newPlate[0].length; j++) {
				newPlate[i][j] = this.plate[i][j];
			}
		}

		newPlate[posi][posj] = this.player;

		return new StateC4(this.nextPlayer(), newPlate);

	}

	public double reward() {
		if (this.end() != End.COMPUTER_WIN) {
			return 0;
		} else {
			return 1;
		}
	}

	private int nextPlayer() {
		if (this.player == 0) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean isValideAction(Action action) {

		return (((ActionC4) (action)).getColumn() >= 0
				&& ((ActionC4) (action)).getColumn() < this.plate[0].length && this.plate[0][((ActionC4) (action))
					.getColumn()] == -1);
	}

	public String toString() {
		int i, j, k;
		String str = "";
		str += "|";
		for (j = 0; j < plate[0].length; j++)
			str += " " + j + " |";
		str += "\n";
		for (k = 0; k < plate[0].length; k++)
			str += "----";
		str += "\n";

		for (i = 0; i < plate.length; i++) {
			str += "|";
			for (j = 0; j < plate[0].length; j++) {
				int p = this.plate[i][j];
				char c;
				if (p == 0) {
					c = 'H';
				} else if (p == 1) {
					c = 'C';
				} else {
					c = ' ';
				}
				str += " " + c + " |";

			}
			str += "\n";
			for (k = 0; k < plate[0].length; k++)
				str += "----";
			str += "\n";
		}

		return str;
	}

	public int getPlayer() {
		return this.player;
	}
}
