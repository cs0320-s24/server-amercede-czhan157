// package edu.brown.cs.student;

// import static org.junit.jupiter.api.Assertions.assertThrows;

// import edu.brown.cs.student.main.parser.CreatorFromRow;
// import edu.brown.cs.student.main.parser.DefaultFormatter;
// import edu.brown.cs.student.main.parser.FactoryFailureException;
// import java.util.ArrayList;
// import java.util.List;
// import org.junit.jupiter.api.Test;

// public class CreatorFromRowTests {

//   @Test
//   void whenCreatorFromRowListOfStringsIsNullThrowFactoryFailureException() {
//     CreatorFromRow c = new DefaultFormatter();
//     List<String> myList = new ArrayList<>();
//     assertThrows(
//         FactoryFailureException.class,
//         () -> {
//           c.create(myList);
//         });
//   }
// }
