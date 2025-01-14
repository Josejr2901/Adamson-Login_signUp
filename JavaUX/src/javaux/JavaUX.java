
package javaux;

import java.util.HashMap;

public class JavaUX {
    public static void main(String[] args) {
        // Initialize MainPage with user data loaded from the CSV file
        HashMap<String, String> loginInfo = UserDataLoader.loadLoginInfoFromCsv("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\user_data.txt");
        new MainPage(loginInfo);     
    }
}
