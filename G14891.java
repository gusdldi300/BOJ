package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class G14891 {
    private static class Rotate {
        private int wheelIndex;
        private int direction;

        public Rotate(int wheelIndex, int direction) {
            this.wheelIndex = wheelIndex;
            this.direction = direction;
        }
    }

    private static final int MAX_WHEEL_COUNT = 4;
    private static final int LEFT_TEETH_INDEX = (1 << 1);
    private static final int RIGHT_TEETH_INDEX = (1 << 5);
    private static short[] sWheels = new short[MAX_WHEEL_COUNT];
    private static int sRotateCount;
    private static ArrayList<Rotate> sRotates = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < MAX_WHEEL_COUNT; ++i) {
            sWheels[i] = Short.parseShort(br.readLine(), 2);
        }

        sRotateCount = Integer.parseInt(br.readLine());
        for (int i = 0; i < sRotateCount; ++i) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int wheelIndex = Integer.parseInt(st.nextToken()) - 1;
            int direction = Integer.parseInt(st.nextToken());

            sRotates.add(new Rotate(wheelIndex, direction));
        }

        for (Rotate rotate : sRotates) {
            short wheel = sWheels[rotate.wheelIndex];
            sWheels[rotate.wheelIndex] = rotate(wheel, rotate.direction);

            rotateRightWheels(rotate.wheelIndex, wheel, rotate.direction);
            rotateLeftWheels(rotate.wheelIndex, wheel, rotate.direction);
        }

        int totalScore = 0;
        for (int i = 0; i < MAX_WHEEL_COUNT; ++i) {
            int score = (int) Math.pow(2, i);
            if ((sWheels[i] & (1 << 7)) > 0) {
                totalScore += score;
            }
        }

        System.out.print(totalScore);
    }

    private static void rotateLeftWheels(int lastIndex, short lastWheel, int lastRotateDirection) {
        for (int i = lastIndex - 1; i >= 0; --i) {
            short lastWheelLeftTeeth = (short) ((lastWheel & LEFT_TEETH_INDEX) >> 1);

            short wheel = sWheels[i];
            short rightTeeth = (short) ((wheel & RIGHT_TEETH_INDEX) >> 5);

            if (lastWheelLeftTeeth == rightTeeth) {
                break;
            }

            int rotateDirection = (lastRotateDirection == 1) ? -1 : 1;
            sWheels[i] = rotate(wheel, rotateDirection);

            lastRotateDirection = rotateDirection;
            lastWheel = wheel;
        }
    }

    private static void rotateRightWheels(int lastIndex, short lastWheel, int lastRotateDirection) {
        for (int i = lastIndex + 1; i < MAX_WHEEL_COUNT; ++i) {
            short lastWheelRightTeeth = (short) ((lastWheel & RIGHT_TEETH_INDEX) >> 5);

            short wheel = sWheels[i];
            short leftTeeth = (short) ((wheel & LEFT_TEETH_INDEX) >> 1);

            if (lastWheelRightTeeth == leftTeeth) {
                break;
            }

            int rotateDirection = (lastRotateDirection == 1) ? -1 : 1;
            sWheels[i] = rotate(wheel, rotateDirection);

            lastRotateDirection = rotateDirection;
            lastWheel = wheel;
        }
    }

    private static short rotate(short wheel, int direction) {
        short rotatedWheel;

        short wrappedTeeth;
        if (direction == -1) {
            short checkTeeth = 0xff >> 1;
            wrappedTeeth = (short) ((wheel & (1 << 7)) >> 7);
            rotatedWheel = (short) (((wheel & checkTeeth) << 1) | wrappedTeeth);
        } else {
            wrappedTeeth = (short) ((wheel & 1) << 7);
            rotatedWheel = (short) ((wheel >> 1) | wrappedTeeth);
        }

        return rotatedWheel;
    }
}
