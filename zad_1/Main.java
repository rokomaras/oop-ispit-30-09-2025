package SOLUTIONS.src.zad_1;

public class Main {
    public static void main(String[] args) {

        SpaceShip enterprise = new SpaceShip("Enterprise");
        enterprise.setLaunchDate(SpaceShip.MAX_LAUNCH_DATE);

        Fleet federationFleet = new Fleet("Federation Fleet");

        federationFleet.addSpaceShip(enterprise);

        federationFleet.displayFleet();

        String filePath = "data/FederationBoats.txt";
        federationFleet.addSpaceShipsFromFile(filePath);

        federationFleet.displayFleet();
    }
}