package edu.brown.cs.student.main.csv;

public class Broadband {
  //    ["NAME","S2802_C03_022E","state","county"]
  private String NAME;
  private double S2802_C03_022E;
  private String state;
  private String county;

  @Override
  public String toString() {
    return this.NAME + " with " + this.S2802_C03_022E + " broadband percentage.";
  }
}
