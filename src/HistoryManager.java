package Scientific_Calculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HistoryManager {

    private  String filename = "history.txt";
    private ArrayList<String> history = new ArrayList<>();

    public HistoryManager(String filename) {  
        this.filename = filename;
        loadHistory();
    }

    public void addRecord(String record) {
        history.add(record);
        appendToFile(record);
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No history available.");
            return;
        }

        System.out.println("\n--- History ---");
        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + "] " + history.get(i));
        }
    }

    public void clearHistory() {
        history.clear();
        saveAll();
        System.out.println("History cleared.");
    }

    public void undoLast() {
        if (history.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        history.remove(history.size() - 1);
        saveAll();
        System.out.println("Undo successful.");
    }

    private void appendToFile(String record) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(record + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Failed to save history.");
        }
    }

    private void saveAll() {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (String line : history) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Failed to save history.");
        }
    }

    private void loadHistory() {
        File file = new File(filename);
        if (file.exists()) {
            try {
                history = new ArrayList<>(Files.readAllLines(Paths.get(filename)));
            } catch (IOException e) {
                System.out.println("History load failed.");
            }
        }
    }

    public boolean hasHistory() {
        return !history.isEmpty();
    }

}
