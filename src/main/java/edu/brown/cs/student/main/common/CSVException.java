package edu.brown.cs.student.main.common;

public class CSVException extends Exception {
    private final ResultInfo resultType;
  private final String message;

  public CSVException(ResultInfo resultType, String message) {
    super();
    this.resultType = resultType;
    this.message = message;
  }

  public ResultInfo getResultInfo() {
    return resultType;
  }

  public String getMessage() {
    return message;
}
}
