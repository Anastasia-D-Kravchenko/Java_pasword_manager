import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final List<Entry> ENTRIES;
    private File currentStorageFile; // Just saving password, not opening them here,
                                                             // but there's possibility to append to the end new entries
    public Storage() {
        this.ENTRIES = new ArrayList<>();
        loadEntries();
    }

    public List<Entry> getAllEntries() {
        return ENTRIES;
    }

    public Entry getEntry(int index) {
        if (index >= 0 && index < ENTRIES.size()) {
            return ENTRIES.get(index);
        }
        return null;
    }

    public void addEntry(String source, String username, String password, String url, String notes, int position) {
        String hashedPassword = HashGenerator.encode(password);
        Entry newEntry = new Entry(
                source.isEmpty() ? "null" : source,
                username.isEmpty() ? "null" : username,
                hashedPassword,
                url.isEmpty() ? "null" : url,
                notes.isEmpty() ? "null" : notes
        );
        if (position >= 0 && position <= this.ENTRIES.size()) {
            this.ENTRIES.add(position, newEntry); // +1 to add after entry
        } else {
            this.ENTRIES.add(newEntry);
        }
        saveAllEntries();
    }

    public void addEntry(String source, String username, String password, String url, String notes) {
        this.addEntry(source, username, password, url, notes, this.ENTRIES.size());
    }

    private String sanitize(String value) {
        return value.isEmpty() ? "null" : value;
    }

    private void writeEntry(BufferedWriter writer, Entry entry) throws IOException {
        writer.write(sanitize(entry.getSource()));
        writer.write(",");
        writer.write(sanitize(entry.getUsername()));
        writer.write(",");
        writer.write(entry.getPassword());
        writer.write(",");
        writer.write(sanitize(entry.getUrl()));
        writer.write(",");
        writer.write(sanitize(entry.getNotes()));
        writer.newLine();
    }

    protected void saveEntry(Entry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE, true))) {
            writeEntry(writer, entry);
        } catch (IOException e) {
            System.err.println("Error writing entry to file: " + e.getMessage());
        }
    }

    protected void saveAllEntries() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE))) {
            for (Entry entry : ENTRIES) {
                writeEntry(writer, entry);
            }
        } catch (IOException e) {
            System.err.println("Error writing all entries to file: " + e.getMessage());
        }
    }

    private void loadEntries() {
        try (Scanner fileScanner = new Scanner(new File(STORAGE))) {
            this.ENTRIES.clear();
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                List<String> parts = new ArrayList<>();
                for (String entry : line.split(",")) {
                    parts.add(entry.trim());
                }
                if (parts.size() == 5) {
                    for (int i = 0; i < parts.size(); i++) {
                        if (parts.get(i).trim().equalsIgnoreCase("null")) {
                            parts.set(i, "");
                        }
                    }
                    this.ENTRIES.add(new Entry(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parts.get(4)));
                } else {
                    System.err.println("Skipping reading of invalid line in password file: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Password storage file could not be found. A new one will be created.");
        }
    }
}