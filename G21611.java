package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class G21611 {
    private static class Position {
        private int row;
        private int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class Move {
        private Position position;
        private int dir;

        public Move(Position position, int dir) {
            this.position = position;
            this.dir = dir;
        }
    }

    private static final int BLAST_COUNT = 4;
    private static final int MAX_MARBLE_COUNT = 4;
    private static int[] sMarbleBlastCounts = new int[MAX_MARBLE_COUNT];
    private static int sMapSize;
    private static int sMagicCount;

    private static int[][] sMap;
    private static int[][] sTempMap;
    private static ArrayList<Position> sPaths;

    private static Position[] sDirs = new Position[] {
        new Position(-1, 0),
        new Position(0, 1),
        new Position(1, 0),
        new Position(0, -1)
    };


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        sMapSize = Integer.parseInt(st.nextToken());
        sMagicCount = Integer.parseInt(st.nextToken());

        int totalPathCount = sMapSize * sMapSize - 1;
        sPaths = new ArrayList<>(totalPathCount);
        sMap = new int[sMapSize][sMapSize];
        sTempMap = new int[sMapSize][sMapSize];
        for (int row = 0; row < sMapSize; ++row) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < sMapSize; ++col) {
                sMap[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        boolean[][] visited = new boolean[sMapSize][sMapSize];
        Position sharkPosition = new Position(sMapSize / 2, sMapSize / 2);
        visited[sharkPosition.row][sharkPosition.col] = true;

        Move move = new Move(new Position(sharkPosition.row, sharkPosition.col - 1), 2);

        for (int i = 0; i < totalPathCount; ++i) {
            int nextDir = (sDirs.length + (move.dir - 1)) % sDirs.length;

            int nextRow = move.position.row + sDirs[nextDir].row;
            int nextCol = move.position.col + sDirs[nextDir].col;

            if (visited[nextRow][nextCol]) {
                nextRow = move.position.row + sDirs[move.dir].row;
                nextCol = move.position.col + sDirs[move.dir].col;
            } else {
                move.dir = nextDir;
            }

            sPaths.add(new Position(move.position.row, move.position.col));
            visited[move.position.row][move.position.col] = true;

            move.position.row = nextRow;
            move.position.col = nextCol;
        }

        for (int blizzard = 0; blizzard < sMagicCount; ++blizzard) {
            st = new StringTokenizer(br.readLine());

            int direction = Integer.parseInt(st.nextToken());
            switch (direction) {
                case 1:
                    direction = 0;
                    break;
                case 2:
                    direction = 2;
                    break;
                case 3:
                    direction = 3;
                    break;
                case 4:
                    direction = 1;
                    break;
                default:
                    assert (false);
                    break;
            }

            int distance = Integer.parseInt(st.nextToken());

            // blizzard on
            int curRow = sharkPosition.row;
            int curCol = sharkPosition.col;

            for (int i = 0; i < distance; ++i) {
                int nextRow = curRow + sDirs[direction].row;
                int nextCol = curCol + sDirs[direction].col;

                sMap[nextRow][nextCol] = 0;
                curRow = nextRow;
                curCol = nextCol;
            }

            // move marbles
            moveMarbles();

            int pathIndex = 0;
            while (true) {
                boolean hasBlast = false;

                // blast marbles
                pathIndex = 0;
                while (pathIndex < sPaths.size()) {
                    Position position = sPaths.get(pathIndex);
                    int number = sMap[position.row][position.col];
                    if (number == 0) {
                        break;
                    }

                    int blastCount = getEqualMarbleCount(pathIndex, number);
                    if (blastCount >= BLAST_COUNT) {
                        sMarbleBlastCounts[number] += blastCount;

                        for (int i = pathIndex; i < (pathIndex + blastCount); ++i) {
                            position = sPaths.get(i);
                            sMap[position.row][position.col] = 0;
                            hasBlast = true;
                        }
                    }

                    pathIndex += blastCount;
                }

                if (hasBlast == false) {
                    break;
                }

                // move marbles
                moveMarbles();
            }

            // expand marbles
            pathIndex = 0;
            int tempIndex = 0;
            while (tempIndex < sPaths.size()) {
                Position position = sPaths.get(pathIndex);
                int number = sMap[position.row][position.col];
                if (number == 0) {
                    break;
                }

                int equalCount = getEqualMarbleCount(pathIndex, number);
                addNewMarble(equalCount, tempIndex++);
                addNewMarble(number, tempIndex++);

                pathIndex += equalCount;
            }

            int[][] temp = sMap;
            sMap = sTempMap;
            sTempMap = temp;
        }

        int totalBlastCount = 0;
        for (int i = 1; i < MAX_MARBLE_COUNT; ++i) {
            totalBlastCount += (i * sMarbleBlastCounts[i]);
        }

        System.out.print(totalBlastCount);
    }

    private static void addNewMarble(int number, int index) {
        Position position = sPaths.get(index);
        sTempMap[position.row][position.col] = number;
    }

    private static void moveMarbles() {
        int pathIndex = 0;
        outer:
        while (pathIndex < sPaths.size()) {
            Position position = sPaths.get(pathIndex);
            if (sMap[position.row][position.col] == 0) {
                int slideIndex = -1;
                for (int j = pathIndex + 1; j < sPaths.size(); ++j) {
                    Position slidePosition = sPaths.get(j);
                    if (sMap[slidePosition.row][slidePosition.col] > 0) {
                        slideIndex = j;

                        break;
                    }
                }

                if (slideIndex != -1) {
                    while (slideIndex < sPaths.size()) {
                        Position blankPosition = sPaths.get(pathIndex);
                        Position slidePosition = sPaths.get(slideIndex);

                        if (sMap[slidePosition.row][slidePosition.col] == 0) {
                            break;
                        }

                        sMap[blankPosition.row][blankPosition.col] = sMap[slidePosition.row][slidePosition.col];
                        sMap[slidePosition.row][slidePosition.col] = 0;

                        ++pathIndex;
                        ++slideIndex;
                    }

                    continue outer;
                }
            }

            ++pathIndex;
        }
    }

    private static int getEqualMarbleCount(int startIndex, int number) {
        assert (number != 0);

        int count = 0;
        for (int i = startIndex; i < sPaths.size(); ++i) {
            Position position = sPaths.get(i);

            if (sMap[position.row][position.col] != number) {
                break;
            }

            ++count;
        }

        return count;
    }

}
