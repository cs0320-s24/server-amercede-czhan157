// package edu.brown.cs.student;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import edu.brown.cs.student.main.parser.CreatorFromRow;
// import edu.brown.cs.student.main.parser.DefaultFormatter;
// import edu.brown.cs.student.main.csv.CSVParser;

// import java.io.*;
// import java.util.List;

// import org.junit.jupiter.api.Test;

// public class ParserTests {

//   @Test
//   void whenReturnParserIsGivenNullArgumentNullThrowNullPointerException()
//       throws FileNotFoundException {

//     String pathDirectory =
//         ("/Users/alexandramercedes/csv-amercede/data/census/dol_ri_earnings_disparity.csv");
//     try (Reader dirReader = new FileReader(pathDirectory)) {
//             CreatorFromRow createdRow = new DefaultFormatter();
//             CSVParser<List<List<String>>> workingParser = new CSVParser<>(dirReader, createdRow,
// null, null);
//             // Your code to use the CSV parser
//             catch (FileNotFoundException e) {
//               // Handle file not found exception
//               e.printStackTrace();
//           } catch (IOException e) {
//               // Handle IO exception
//               e.printStackTrace();
//           }

//             assertThrows(
//         NullPointerException.class, () -> {
//           workingParser.parse(null, null);
//         });

// }
// }
