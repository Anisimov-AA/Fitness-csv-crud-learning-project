package fitnesscsvlogger;

/**
 * This class represents a single fitness data entry for a specific date
 */
public class FitnessEntry {
  // Declare private fields for fitness data
  private String date;
  private int heartRate;
  private int steps;
  private int calories;
  private double sleep;
  private double weight;

  // Constructor with validation
  public FitnessEntry(String date, int heartRate, int steps, int calories, double sleep, double weight) {
    if (date == null || date.trim().isEmpty()) {
     throw new IllegalStateException("Date cannot be null or empty");
    }
    if (heartRate <= 0) {
      throw new IllegalArgumentException("Heart rate must be positive");
    }
    if (steps < 0) {
      throw new IllegalArgumentException("Steps cannot be negative");
    }
    if (calories < 0) {
      throw new IllegalArgumentException("Calories cannot be negative");
    }
    if (sleep < 0 || sleep > 24) {
      throw new IllegalArgumentException("Sleep must be between 0 and 24 hours");
    }
    if (weight <= 0) {
      throw new IllegalArgumentException("Weight must be positive");
    }

    this.date = date.trim();
    this.heartRate = heartRate;
    this.steps = steps;
    this.calories = calories;
    this.sleep = sleep;
    this.weight = weight;
  }

  // Getter methods for all fields
  public String getDate() {
    return date;
  }
  public int getHeartRate() {
    return heartRate;
  }
  public int getSteps() {
    return steps;
  }
  public int getCalories() {
    return calories;
  }
  public double getSleep() {
    return sleep;
  }
  public double getWeight() {
    return weight;
  }

  // Overriding toString() method
  @Override
  public String toString() {
    return String.format(
        "%s | HR: %d | Steps: %d | Calories: %d | Sleep: %.1fh | Weight: %.1fkg",
        date, heartRate, steps, calories, sleep, weight
    );
  }

  // Convert this entry to CSV format (one line)
  public String toCsvLine(){
    return String.format(
        java.util.Locale.US,
        "%s,%d,%d,%d,%.1f,%.1f",
        date, heartRate, steps, calories, sleep, weight
    );
  }

  // Get the CSV header line
  public static String getCsvHeader(){
    return "Date,HeartRate,Steps,Calories,Sleep,Weight";
  }

//  public static void main(String[] args) {
//    FitnessEntry entry = new FitnessEntry("2024-01-01",70,7500,400,8.0,71.0);
//    System.out.println(entry);
//    System.out.println(entry.toCsvLine());
//    System.out.println(FitnessEntry.getCsvHeader());
//  }
}
