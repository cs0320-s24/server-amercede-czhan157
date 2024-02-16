package edu.brown.cs.student.main.datasource;

public interface Datasource {
  BroadbandData getBroadbandPercentage(TargetLocation location)
      throws DatasourceException, IllegalArgumentException;
}
