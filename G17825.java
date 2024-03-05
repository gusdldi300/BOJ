package backtracking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class G17825 {
    private static enum Color {
        RED,
        BLUE
    }

    private static class Space {
        private int score;
        private Color color;
        private Space nextSpace;

        private HashSet<Integer> players = new HashSet<>();

        public Space(final int score) {
            this.score = score;
            this.color = Color.RED;
        }

        protected Space(final int score, final Color color) {
            this.score = score;
            this.color = color;
        }
    }

    private static class BlueSpace extends Space {
        private Space nextSpecialSpace;

        public BlueSpace(final int score) {
            super(score, Color.BLUE);
        }
    }
    private static final int MAX_EXPECTED_MOVE_COUNT = 10;

    private static int[] sExpectedMoves = new int[MAX_EXPECTED_MOVE_COUNT];
    private static int sMaxScore = Integer.MIN_VALUE;

    private static final int START = 0;
    private static final int END = -1;

    private static int MAX_PLAYER_COUNT = 4;
    private static Space[] sPlayersStatus = new Space[MAX_PLAYER_COUNT];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < MAX_EXPECTED_MOVE_COUNT; ++i) {
            sExpectedMoves[i] = Integer.parseInt(st.nextToken());
        }

        sBoard = createBoard();
        for (int i = 0; i < MAX_PLAYER_COUNT; ++i) {
            sPlayersStatus[i] = sBoard.start;
        }

        getMaxScoreRecursive(0, 0);
        System.out.print(sMaxScore);
    }
    private static void getMaxScoreRecursive(int curScore, int moveCount) {
        if (moveCount == MAX_EXPECTED_MOVE_COUNT || sBoard.end.players.size() == MAX_PLAYER_COUNT) {
            sMaxScore = Math.max(sMaxScore, curScore);

            return;
        }

        int move = sExpectedMoves[moveCount];
        for (int player = 0; player < MAX_PLAYER_COUNT; ++player) {
            if (sPlayersStatus[player].equals(sBoard.end)) {
                continue;
            }

            int nextScore = 0;
            Space curSpace = sPlayersStatus[player];
            Space nextSpace = getNextSpace(curSpace, move);
            if (!nextSpace.equals(sBoard.end)) {
                if (!nextSpace.players.isEmpty()) {
                    continue;
                }

                nextScore = nextSpace.score;
            }

            curSpace.players.remove(player);
            nextSpace.players.add(player);
            sPlayersStatus[player] = nextSpace;

            getMaxScoreRecursive(curScore + nextScore, moveCount + 1);

            sPlayersStatus[player] = curSpace;
            nextSpace.players.remove(player);
            curSpace.players.add(player);
        }
    }

    private static Space getNextSpace(final Space startSpace, final int count) {
        assert (!startSpace.equals(sBoard.end));

        Space nextSpace = startSpace.nextSpace;
        if (startSpace.color == Color.BLUE) {
            nextSpace = ((BlueSpace) startSpace).nextSpecialSpace;
        }

        for (int i = 1; i < count; ++i) {
            if (nextSpace.score == END) {
                break;
            }

            nextSpace = nextSpace.nextSpace;
        }

        return nextSpace;
    }

    private static class Board {
        private Space start;
        private Space end;

        public Board(final Space start, final Space end) {
            this.start = start;
            this.end = end;
        }
    }

    private static Board sBoard;

    private static Board createBoard() {
        Space boardStart = new Space(START);

        Space centerSpace = new Space(25);

        Space startSpace = boardStart;
        BlueSpace blueSpace = new BlueSpace(10);
        makePath(startSpace, blueSpace, 2, 2, 4);

        blueSpace.nextSpecialSpace = new Space(13);
        startSpace = blueSpace.nextSpecialSpace;
        makePath(startSpace, centerSpace, 16, 3, 2);

        startSpace = blueSpace;
        blueSpace = new BlueSpace(20);
        makePath(startSpace, blueSpace, 12, 2, 4);

        blueSpace.nextSpecialSpace = new Space(22);
        startSpace = blueSpace.nextSpecialSpace;
        makePath(startSpace, centerSpace, 24, 2, 1);

        startSpace = blueSpace;
        blueSpace = new BlueSpace(30);
        makePath(startSpace, blueSpace, 22, 2, 4);

        blueSpace.nextSpecialSpace = new Space(28);
        startSpace = blueSpace.nextSpecialSpace;
        makePath(startSpace, centerSpace, 27, -1, 2);

        startSpace = blueSpace;
        Space lastSpace = new Space(40);
        makePath(startSpace, lastSpace, 32, 2, 4);

        startSpace = centerSpace;
        makePath(startSpace, lastSpace, 30, 5, 2);

        Space boardEnd = new Space(END);
        lastSpace.nextSpace = boardEnd;

        return new Board(boardStart, boardEnd);
    }

    private static void makePath(final Space start, final Space end, final int startScore, final int scoreOffset, final int count) {
        Space lastSpace = start;
        int score = startScore;
        for (int i = 0; i < count; ++i) {
            lastSpace.nextSpace = new Space(score);
            score += scoreOffset;

            lastSpace = lastSpace.nextSpace;
        }

        lastSpace.nextSpace = end;
    }
}
