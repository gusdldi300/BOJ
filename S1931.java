package algorithm.greedy;

//import java.util.Scanner;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class S1931 {
    public static void testCode(String[] args) throws IOException {
        int mostRoom = getMostRooms();
        System.out.println(mostRoom);
    }

    private static int getMostRooms() throws IOException {
        //Scanner scanner = new Scanner(System.in);
        //int countRoom = scanner.nextInt();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int countRoom = Integer.parseInt(br.readLine());

        assert (countRoom >= 1 && countRoom <= 100000);

        MeetingRoom[] meetingRooms = new MeetingRoom[countRoom];

        for (int index = 0; index < countRoom; ++index) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int startTime = Integer.parseInt(st.nextToken());
            int endTime = Integer.parseInt(st.nextToken());

            meetingRooms[index] = new MeetingRoom(startTime, endTime);
        }

        quickSortAscending(meetingRooms);

        int countMostRoom = 1; // the first room is always inserted
        MeetingRoom lastRoom = meetingRooms[0];
        for (int index = 1; index < countRoom; ++index) {
            if (lastRoom.getEndTime() <= meetingRooms[index].getStartTime()) {
                countMostRoom++;

                lastRoom = meetingRooms[index];
            }
        }

        return countMostRoom;
    }

    private static void quickSortAscending(final MeetingRoom[] meetingRooms) {
        quickSortAscendingRecursive(0, meetingRooms.length - 1, meetingRooms);
    }

    private static void quickSortAscendingRecursive(int leftIndex, int rightIndex, final MeetingRoom[] meetingRooms) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int completeIndex = partition(leftIndex, rightIndex, meetingRooms);
        quickSortAscendingRecursive(leftIndex, completeIndex - 1, meetingRooms);
        quickSortAscendingRecursive(completeIndex + 1, rightIndex, meetingRooms);
    }

    private static int partition(int leftIndex, int rightIndex, final MeetingRoom[] meetingRooms) {
        int pivot = meetingRooms[rightIndex].getEndTime();

        int swapIndex = leftIndex;
        for (int index = leftIndex; index < rightIndex; ++index) {
            if (meetingRooms[index].getEndTime() < pivot) {
                swap(swapIndex, index, meetingRooms);

                ++swapIndex;
            }
        }

        swap(swapIndex, rightIndex, meetingRooms);

        return swapIndex;
    }

    private static void swap(final int leftIndex, final int rightIndex, final MeetingRoom[] meetingRooms) {
        MeetingRoom tempRoom = meetingRooms[leftIndex];
        meetingRooms[leftIndex] = meetingRooms[rightIndex];
        meetingRooms[rightIndex] = tempRoom;
    }
}

class MeetingRoom {
    private int startTime;
    private int endTime;

    public MeetingRoom(final int startTime, final int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }
}
