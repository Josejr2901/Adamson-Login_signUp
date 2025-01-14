
package javaux;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class UserDataLoader {
    public static HashMap<String, String> loadLoginInfoFromCsv(String filePath) {
        HashMap<String, String> loginInfo = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                // Ensure there are enough columns (at least username and password)
                if (data.length >= 3) {
                    String username = data[0].trim();
                    String password = data[2].trim();
                    loginInfo.put(username, password);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading user data: " + e.getMessage());
        }

        return loginInfo;
    }
}
