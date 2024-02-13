package edu.brown.cs.student.main.csv;

<<<<<<< Updated upstream
import parser.FactoryFailureException;
=======
import edu.brown.cs.student.main.parser.FactoryFailureException;
>>>>>>> Stashed changes

import java.io.FileNotFoundException;
import java.util.Iterator;
public interface InterfaceCSV {

<<<<<<< Updated upstream
default CSVParser getParser(Iterator<CSVParser> parserSet) throws FileNotFoundException {

    try{
        CSVParser csvParser = parserSet.next();
        return csvParser;
    }
    catch (NullPointerException e) {
        throw new FileNotFoundException("invalid CSVParser file");
    }

}}
=======
    default CSVParser getParser(Iterator<CSVParser> parserSet) throws FileNotFoundException {

        try{
            CSVParser csvParser = parserSet.next();
            return csvParser;
        }
        catch (NullPointerException e) {
            throw new FileNotFoundException("invalid CSVParser file");
        }

    }}

>>>>>>> Stashed changes
