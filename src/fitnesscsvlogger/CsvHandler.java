package fitnesscsvlogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all file I/O operations for fitness data
 */
public class CsvHandler {


  /**
   * This method saves a list of fitness entries to a CSV file
   * @param entries the list of fitness entries
   * @param filename the CSV file name
   * @return boolean - true for success, false for failure
   */
  public static boolean saveToFile(List<FitnessEntry> entries, String filename) {
    // try-with-resources with BufferedWriter and FileReader
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      // write the CSV header line first
      writer.write(FitnessEntry.getCsvHeader());
      writer.newLine();

      // loop through each fitness entry and write its CSV representation
      for(FitnessEntry entry : entries) {
        writer.write(entry.toCsvLine());
        writer.newLine();
      }

      return  true;

    } catch (IOException e) {
      System.out.println("Save failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * This method loads fitness entries from a CSV file
   * @param filename the CSV file name
   * @return the populated list of entries
   */
  public static List<FitnessEntry> loadFromFile(String filename) {
    // ArrayList to store the loaded entries
    List<FitnessEntry> entries = new ArrayList<>();

    // try-with-resources with BufferedReader and FileReader
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

      // read and discard the first line
      reader.readLine(); // skip header

      // a while loop with readLine() to process each data line
      String line;
      while ((line = reader.readLine()) != null) {
        // skip empty or whitespace-only lines to avoid errors
        if(!line.trim().isEmpty()) {
          try {
            entries.add(parseLine(line)); // parseLine() helper method to convert line to a FitnessEntry
          } catch (Exception e) {
            System.out.println("Warning: Skipping invalid line - " + e.getMessage());
          }
        }
      }

    } catch (IOException e) {
      System.out.println("Load failed: " + e.getMessage());
    }

    return entries;
  }

  /**
   * This method converts a CSV line to a FitnessEntry object
   * @param line the line to parse
   * @return new FitnessEntry with parsed values
   */
  private static FitnessEntry parseLine(String line) {
    // handle null/empty input
    if(line == null || line.trim().isEmpty()) {
      throw new IllegalArgumentException("Line cannot be null or empty");
    }

    // split the input line using comma as delimiter
    String[] parts = line.split(",");

    // validate that we have exactly 6 fields
    if(parts.length != 6) {
      throw new IllegalArgumentException("Invalid CSV format - expected 6 columns, got " + parts.length);
    }

    try {
      // create and return new FitnessEntry with parsed values
      return new FitnessEntry(
          parts[0].trim(),
          Integer.parseInt(parts[1].trim()),
          Integer.parseInt(parts[2].trim()),
          Integer.parseInt(parts[3].trim()),
          Double.parseDouble(parts[4].trim()),
          Double.parseDouble(parts[5].trim())
      );
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number format in line: " + line);
    }
  }

  /**
   * Simple helper to check if a file exists
   * @param filename the CSV file name
   * @return boolean - true if a file exists, false if not
   */
  public static boolean fileExists(String filename) {
    return new File(filename).exists();
  }

//  public static void main(String[] args) {
//    List<FitnessEntry> entries = new ArrayList<>();
//    entries.add(new FitnessEntry("2024-01-01",70,7500,400,8.0,71.0));
//    entries.add(new FitnessEntry("2024-01-02",73,8200,435,7.5,70.9));
//    saveToFile(entries, "test.csv");
//
//    entries = loadFromFile("test.csv");
//    for(FitnessEntry entry : entries) {
//      System.out.println(entry);
//    }
//
//    System.out.println(fileExists("test.csv"));
//  }
}
