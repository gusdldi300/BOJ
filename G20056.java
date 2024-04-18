package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class G20056 {
    private static class Fireball {
        private int row;
        private int col;
        private int mass;
        private int speed;
        private int direction;

        public Fireball(int row, int col, int mass, int speed, int direction) {
            this.row = row;
            this.col = col;

            this.mass = mass;
            this.speed = speed;
            this.direction = direction;
        }
    }

    private static Queue<Fireball>[][] sMap;
    private static Queue<Fireball> sFireballs = new LinkedList<>();
    private static int sMapSize;
    private static int sFireballSize;
    private static int sTotalMoveCount;

    private static int[] sRowDirs = new int[] {-1, -1, 0, 1, 1, 1, 0, -1};
    private static int[] sColDirs = new int[] {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        sMapSize = Integer.parseInt(st.nextToken());
        sFireballSize = Integer.parseInt(st.nextToken());
        sTotalMoveCount = Integer.parseInt(st.nextToken());

        sMap = new LinkedList[sMapSize][sMapSize];
        for (int row = 0; row < sMapSize; ++row) {
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = new LinkedList<>();
            }
        }

        for (int i = 0; i < sFireballSize; ++i) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken()) - 1;
            int col = Integer.parseInt(st.nextToken()) - 1;

            int mass = Integer.parseInt(st.nextToken());
            int speed = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());

            sFireballs.add(new Fireball(row, col, mass, speed, direction));
        }

        int moveCount = 0;
        while (moveCount < sTotalMoveCount) {
            while (!sFireballs.isEmpty()) {
                Fireball fireball = sFireballs.remove();
                int wrappedRow = getWrappedIndex(fireball.row, sRowDirs[fireball.direction] * fireball.speed);
                int wrappedCol = getWrappedIndex(fireball.col, sColDirs[fireball.direction] * fireball.speed);

                fireball.row = wrappedRow;
                fireball.col = wrappedCol;
                sMap[wrappedRow][wrappedCol].add(fireball);
            }

            for (int row = 0; row < sMapSize; ++row) {
                for (int col = 0; col < sMapSize; ++col) {
                    Queue<Fireball> curFireballs = sMap[row][col];
                    if (curFireballs.size() == 0) {
                        continue;
                    }

                    if (curFireballs.size() == 1) {
                        sFireballs.add(curFireballs.remove());
                        continue;
                    }

                    int massSum = 0;
                    int speedSum = 0;
                    int curSize = curFireballs.size();

                    int directionResult = curFireballs.peek().direction % 2;
                    boolean isDirectionsEqual = true;
                    while (!curFireballs.isEmpty()) {
                        Fireball combineFireball = curFireballs.remove();

                        massSum += combineFireball.mass;
                        speedSum += combineFireball.speed;
                        if (combineFireball.direction % 2 != directionResult) {
                            isDirectionsEqual = false;
                        }
                    }

                    massSum /= 5;
                    if (massSum <= 0) {
                        continue;
                    }

                    speedSum /= curSize;
                    int startDirIndex = 1;
                    if (isDirectionsEqual) {
                        startDirIndex = 0;
                    }

                    for (int count = 0; count < 4; ++count) {
                        sFireballs.add(new Fireball(row, col, massSum, speedSum, startDirIndex));

                        startDirIndex += 2;
                    }
                }
            }

            ++moveCount;
        }

        int massSum = 0;
        while (!sFireballs.isEmpty()) {
            massSum += sFireballs.remove().mass;
        }

        System.out.print(massSum);
    }
    private static int getWrappedIndex(int index, int offset) {
        int newOffset = offset;

        while (newOffset < 0) {
            newOffset += sMapSize;
        }

        return (index + newOffset) % sMapSize;
    }
}
