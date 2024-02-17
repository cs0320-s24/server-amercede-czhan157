package edu.brown.cs.student.main.datasource;

/** Datasource is an interface that BroadbandHandler uses so that it can have a state passed in */
public interface Datasource {
  BroadbandData getBroadbandPercentage(TargetLocation location)
      throws DatasourceException, IllegalArgumentException;
}
