package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class G14500 {
    private static boolean[][][] sBlocks = new boolean[][][] {
            /*
            -----
            */
            {
                    {true, true, true, true}
            },
            {
                    {true},
                    {true},
                    {true},
                    {true}
            },

            /*
            --
            --
            */
            {
                    {true, true},
                    {true, true}
            },

            /*
            -
            -
            --
            */
            {
                    {true, false},
                    {true, false},
                    {true, true}
            },
            {
                    {false, true},
                    {false, true},
                    {true, true}
            },
            {
                    {true, true, true},
                    {true, false, false}
            },
            {
                    {true, true, true},
                    {false, false, true}
            },
            {
                    {true, true},
                    {false, true},
                    {false, true}
            },
            {
                    {true, true},
                    {true, false},
                    {true, false}
            },
            {
                    {false, false, true},
                    {true, true, true}
            },
            {
                    {true, false, false},
                    {true, true, true}
            },

            /*
            -
            --
             -
            */
            {
                    {true, false},
                    {true, true},
                    {false, true}
            },
            {
                    {false, true},
                    {true, true},
                    {true, false}
            },
            {
                    {false, true, true},
                    {true, true, false},
            },
            {
                    {true, true, false},
                    {false, true, true},
            },

            /*
            ---
             -
            */
            {
                    {true, true, true},
                    {false, true, false}
            },
            {
                    {false, true},
                    {true, true},
                    {false, true}
            },
            {
                    {false, true, false},
                    {true, true, true}
            },
            {
                    {true, false},
                    {true, true},
                    {true, false}
            },
    };

    private static int[][] sPaper;
    private static int sRowSize;
    private static int sColSize;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        sRowSize = Integer.parseInt(inputs[0]);
        sColSize = Integer.parseInt(inputs[1]);

        sPaper = new int[sRowSize][sColSize];
        for (int row = 0; row < sRowSize; ++row) {
            inputs = br.readLine().split(" ");
            for (int col = 0; col < sColSize; ++col) {
                sPaper[row][col] = Integer.parseInt(inputs[col]);
            }
        }

        int maxSum = Integer.MIN_VALUE;
        for (int row = 0; row < sRowSize; ++row) {
            for (int col = 0; col < sColSize; ++col) {
                for (int blockIndex = 0; blockIndex < sBlocks.length; ++blockIndex) {
                    if (row + sBlocks[blockIndex].length > sRowSize || col + sBlocks[blockIndex][0].length > sColSize) {
                        continue;
                    }

                    int sum = 0;
                    for (int blockRow = 0; blockRow < sBlocks[blockIndex].length; ++blockRow) {
                        for (int blockCol = 0; blockCol < sBlocks[blockIndex][0].length; ++blockCol) {
                            if (!sBlocks[blockIndex][blockRow][blockCol]) {
                                continue;
                            }

                            sum += sPaper[row + blockRow][col + blockCol];
                        }
                    }


                    if (sum > maxSum) {
                        maxSum = sum;
                    }
                }
            }
        }

        System.out.print(maxSum);
    }
}
