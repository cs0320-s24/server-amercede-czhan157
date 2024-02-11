package csv;

import parser.FactoryFailureException;

import java.io.FileNotFoundException;
import java.util.Iterator;
public interface InterfaceCSV {

default CSVParser getParser(Iterator<CSVParser> parserSet) throws FileNotFoundException {

    try{
        CSVParser csvParser = parserSet.next();
        return csvParser;
    }
    catch (NullPointerException e) {
        throw new FileNotFoundException("invalid CSVParser file");
    }

}}
    

