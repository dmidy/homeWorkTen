import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            String[] headers = null;
            while ((line = reader.readLine()) != null) {
                if (headers == null) {
                    headers = line.split(" ");
                } else {
                    String[] values = line.split(" ");
                    User user = createUser(headers, values);
                    if (user != null) {
                        userList.add(user);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeToJsonFile(userList, "user.json");
    }

    private static User createUser(String[] headers, String[] values) {
        if (headers.length != values.length) {
            return null;
        }

        String name = null;
        int age = 0;

        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase("name")) {
                name = values[i];
            } else if (headers[i].equalsIgnoreCase("age")) {
                try {
                    age = Integer.parseInt(values[i]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }

        if (name == null) {
            return null;
        }

        return new User(name, age);
    }

    private static void writeToJsonFile(List<User> userList, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}