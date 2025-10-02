package SOLUTIONS.src.zad_1;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;
import java.text.SimpleDateFormat;

public class SpaceShip implements Comparable<SpaceShip> {
    private int id;
    private static int cntId = 99;
    private String spaceShipName;
    private Calendar launchDate;

    public static final Calendar MIN_LAUNCH_DATE;
    public static final Calendar MAX_LAUNCH_DATE;

    static {
        MIN_LAUNCH_DATE = Calendar.getInstance();
        MIN_LAUNCH_DATE.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        MIN_LAUNCH_DATE.set(Calendar.MILLISECOND, 0);

        MAX_LAUNCH_DATE = Calendar.getInstance();
        MAX_LAUNCH_DATE.set(2075, Calendar.JANUARY, 1, 0, 0, 0);
        MAX_LAUNCH_DATE.set(Calendar.MILLISECOND, 0);
    }

    public SpaceShip(String spaceShipName) {
        this.id = ++cntId;
        this.spaceShipName = spaceShipName;
        generateNewLaunchDate();
    }

    public SpaceShip(String fileLine, boolean fromFile) {
        String[] parts = fileLine.split(",");
        if (parts.length < 3)
            throw new IllegalArgumentException("Invalid file line: " + fileLine);

        this.id = Integer.parseInt(parts[0].trim());
        this.spaceShipName = parts[1].trim();

        String[] dateParts = parts[2].trim().split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[2]), 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        this.launchDate = cal;
    }

    public void generateNewLaunchDate() {
        long minMillis = MIN_LAUNCH_DATE.getTimeInMillis();
        long maxMillis = MAX_LAUNCH_DATE.getTimeInMillis();
        long randomMillis = minMillis + (long) (new Random().nextDouble() * (maxMillis - minMillis));
        Calendar randomDate = Calendar.getInstance();
        randomDate.setTimeInMillis(randomMillis);
        this.launchDate = randomDate;
    }

    public void setLaunchDate(Calendar date) {
        this.launchDate = date;
    }

    public int getId() {
        return id;
    }

    public String getSpaceShipName() {
        return spaceShipName;
    }

    public Calendar getLaunchDate() {
        return launchDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(launchDate.getTime());
        return String.format("SpaceShip ID: %d, SpaceShip Name: %s, Launch Date: %s", id, spaceShipName, dateStr);
    }

    @Override
    public int compareTo(SpaceShip o) {
        if (this.launchDate.equals(o.launchDate)) {
            return Integer.compare(this.id, o.id);
        }
        return this.launchDate.compareTo(o.launchDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceShip)) return false;
        SpaceShip that = (SpaceShip) o;
        return id == that.id &&
                Objects.equals(spaceShipName, that.spaceShipName) &&
                Objects.equals(launchDate, that.launchDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, spaceShipName, launchDate);
    }
}