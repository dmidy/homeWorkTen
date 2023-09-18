import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class WordFrequencyCounter {
    public static void countWordFrequency(String fileName) {
        Map<String, Integer> wordFrequency = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.trim().split("\\s+");
                for (String word : words) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PriorityQueue<Map.Entry<String, Integer>> sortedWordFrequency = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        sortedWordFrequency.addAll(wordFrequency.entrySet());

        while (!sortedWordFrequency.isEmpty()) {
            Map.Entry<String, Integer> entry = sortedWordFrequency.poll();
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        String fileName = "words.txt";
        countWordFrequency(fileName);
    }
}
