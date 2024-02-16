package edu.brown.cs.student.main.parser;

/**
 * Argument Parser Class to support Main utility
 *
 * <p>example usage: ./run -filePath ./testing_csvs/test1.csv -searchFor "Tim" -header -columnIndex
 * ./run -filePath ./testing_csvs/test1.csv -searchFor "Tim" -header -columnIndex 2 ./run -filePath
 * ./testing_csvs/test1.csv -searchFor "Tim" -header -columnName "LastName" 1
 */
public class ArgParse {

  public String filePath = null; // default is no filepath
  public String searchFor = null; // default is no search term
  public Boolean withHeader = false; // default is without a header
  public Integer columnIdx = -1; // default is to not search within specific column index
  public String columnName = null; // default is to not search within specific column name

  /**
   * creates instance of argparse class
   *
   * @param args: array of command-line arguments
   */
  public ArgParse(String[] args) {

    // Do nothing if no arguments are provided
    if (args == null) {
      return;
    }
    int argIdx;
    for (argIdx = 0; argIdx < args.length; ) {
      argIdx = addArgs(argIdx, args);
    }
  }

  /**
   * Process the next argument pointed to by argIndex and advance argIndex
   *
   * @param argIdx: index of the command-line argument to be processed.
   * @param args: string array of the command-line arguments.
   * @return new value of argIndex to be used in the next call.
   */
  private Integer addArgs(Integer argIdx, String[] args) {
    String usageMessage =
        "usage: -fileName path_to_file -searchFor text_to_search [-columnIdx column_index | -columnName column_name]"
            + " [-withHeader]";
    String arg = args[argIdx];
    String value;
    if (argIdx + 1 < args.length) {
      value = args[argIdx + 1];
    } else {
      value = null;
    }
    Boolean required = true;
    Integer nextArgIdx = 0;
    //
    switch (arg) {
      case "-filePath":
        this.filePath = value;
        nextArgIdx = argIdx + 2;
        break;
      case "-searchFor":
        this.searchFor = value;
        nextArgIdx = argIdx + 2;
        break;
      case "-columnIndex":
        try { // Integer Index
          this.columnIdx = Integer.parseInt(value);

        } catch (Exception e) {
          this.columnIdx = -1;
        }
        if (this.columnIdx < 0) { // Negative Index
          System.err.println("Column index (" + value + ") must be a number >= 0.");
          System.exit(1);
        }
        nextArgIdx = argIdx + 2;
        break;
      case "-columnName":
        this.withHeader = true;
        nextArgIdx = argIdx + 2;
        this.columnName = value;
        break;
      case "-header":
        this.withHeader = true;
        nextArgIdx = argIdx + 1;
        required = false;
        break;
      default:
        System.err.println("Argument unrecognized " + arg);
        System.err.println(usageMessage);
        System.exit(1);
    }

    if (required && ((value == null) || value.startsWith("-"))) {
      System.err.println("Invalid value for argument: " + arg);
      System.err.println(usageMessage);
      System.exit(1);
    }
    return nextArgIdx;
  }
  /** prints the user's task description */
  public void printExecution() {

    System.out.println(
        "Searching file '" + this.filePath + "for this search term: " + this.searchFor);

    if (this.columnIdx >= 0) {
      System.out.println("Search is contained within column number " + this.columnIdx);
    } else if (this.columnName != null) {
      System.out.println("Search is contained within column name " + this.columnName);
    }
  }
}
