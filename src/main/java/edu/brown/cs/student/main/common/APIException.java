package edu.brown.cs.student.main.common;
/** Exception for API Calls */
public class APIException extends Exception {
  private final ResultInfo resultInfo;
  private final String message;

  public APIException(ResultInfo resultInfo, String message) {
    super();
    this.resultInfo = resultInfo;
    this.message = message;
  }

  public ResultInfo getResultInfo() {
    return this.resultInfo;
  }

  public String getMessage() {
    return message;
  }
}
