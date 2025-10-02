package SOLUTIONS.src.zad_1;

import java.util.TreeSet;
import java.util.List;

public class Fleet {
    private String fleetName;
    private TreeSet<SpaceShip> fleetSet;

    public Fleet(String fleetName) {
        this.fleetName = fleetName;
        this.fleetSet = new TreeSet<>();
    }

    public void addSpaceShip(SpaceShip ship) {
        fleetSet.add(ship);
        System.out.println("SpaceShip added to the fleet.");
    }

    public void addSpaceShipsFromFile(String filePath) {
        try {
            List<String> lines = AUX_FLEET_CLS.readLinesFromFile(filePath);
            for (String line : lines) {
                SpaceShip ship = AUX_FLEET_CLS.createSpaceShipsFromFileString(line);
                addSpaceShip(ship);
            }
            System.out.println("All SpaceShips are added from file: " + filePath);
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    public void displayFleet() {
        System.out.println("================================");
        System.out.println("Fleet Name: " + fleetName);
        for (SpaceShip ship : fleetSet) {
            System.out.println(ship);
        }
        System.out.println("================================");
    }
}