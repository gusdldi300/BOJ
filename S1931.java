package algorithm.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class S1931 {
    public static void testCode(String[] args) throws IOException {
        int mostRoom = getMostRooms();
        System.out.println(mostRoom);
    }

    private static int getMostRooms() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int countRoom = Integer.parseInt(br.readLine());

        assert (countRoom >= 1 && countRoom <= 100000);

        MeetingRoom[] meetingRooms = new MeetingRoom[countRoom];

        for (int index = 0; index < countRoom; ++index) {
            String[] time = br.readLine().split(" ");
            
            long startTime = Long.parseLong(time[0]);
            long endTime = Long.parseLong(time[1]);

            meetingRooms[index] = new MeetingRoom(startTime, endTime);
        }

        if (countRoom == 1) {
            return 1;
        }

        Arrays.sort(meetingRooms, new Comparator<MeetingRoom>() {
            @Override
            public int compare(MeetingRoom o1, MeetingRoom o2) {
                int result = (int) (o1.getEndTime() - o2.getEndTime());
                if (result == 0) {
                    result = (int) (o1.getStartTime() - o2.getStartTime());
                }

                return result;
            }
        });
        printMeetingRooms(meetingRooms);

        int countMostRoom = 1; // the first room is always inserted
        MeetingRoom lastRoom = meetingRooms[0];
        for (int index = 1; index < countRoom; ++index) {
            if (meetingRooms[index].getStartTime() >= lastRoom.getEndTime()) {
                countMostRoom++;

                lastRoom = meetingRooms[index];
            }
        }

        return countMostRoom;
    }

    private static void printMeetingRooms(final MeetingRoom[] meetingRooms) {
        for (int index = 0; index < meetingRooms.length; ++index) {
            System.out.format("%d: (%d, %d)%s", index + 1, meetingRooms[index].getStartTime(), meetingRooms[index].getEndTime(), System.lineSeparator());
        }
    }

    private static class MeetingRoom {
        private long startTime;
        private long endTime;

        public MeetingRoom(final long startTime, final long endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public long getStartTime() {
            return this.startTime;
        }

        public long getEndTime() {
            return this.endTime;
        }
    }

}
