package SOLUTIONS.src.zad_1;

import java.io.*;
import java.util.*;

public class AUX_FLEET_CLS {
    private AUX_FLEET_CLS() {}

    public static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty())
                    lines.add(line.trim());
            }
        }
        return lines;
    }

    public static SpaceShip createSpaceShipsFromFileString(String fileLine) {
        return new SpaceShip(fileLine, true);
    }
}