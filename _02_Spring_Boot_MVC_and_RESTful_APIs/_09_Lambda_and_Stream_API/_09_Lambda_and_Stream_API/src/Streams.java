import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {
        // Creating a List
        List<String> fruits = List.of("Kiwi", "Apple", "Banana", "Mango");

        // Converting List to Stream
        Stream<String> str = fruits.stream();

        // 1. forEach loop on Stream
//        str.forEach((fruit) -> {              // forEach loop on stream
//            System.out.println(fruit);
//        });

        // 2. Sorting a stream
//        str
//                .sorted()                     // Sorted the array
//                .forEach(fruit -> System.out.println(fruit));
//                // .forEach(System.out::println);   // Method Reference which comes up with lambda, in which we don't need to provide variable name and provide :: between System.out and println. Method Reference works on such conditions where we are directly printing the result inside of method

        // 3. Sorting and getting length of Stream
//        str
//                .sorted()
//                .map(fruit -> fruit.length())     // Use to get length of the stream
//                .forEach(fruit -> System.out.println(fruit));

        // 4. Sorting and getting length and multiplying it with 2
//        str
//                .sorted()
//                .map(fruit -> fruit.length())
//                .map(fruitLength -> 2*fruitLength)    // Multiple same methods
//                .forEach(fruit -> System.out.println(fruit));

        // 5. Filtering Stream on basis of the length
//        str
//                .filter(fruit -> fruit.length() < 5)  // use to filter on basis of the condition
//                .sorted()
//                .map(fruit -> fruit.length())
//                .map(fruitLength -> 2*fruitLength)
//                .forEach(fruit -> System.out.println(fruit));

        // 6. Create new Stream of List from List and then get its length by map method and then collect length of its elements in an array of List.
//        List<Integer> fruitList = fruits
//                .stream()
//                .map(fruit -> fruit.length())
//                .collect(Collectors.toList());        // Use to collect element in List
//        System.out.println(fruitList);

        // 7. Create new Stream of Set from List and then get its length by map method and then collect length of its elements in an array of Set.
//        Set<Integer> fruitSet = fruits
//                .stream()
//                .map(fruit -> fruit.length())
//                .collect(Collectors.toSet());         // Use to collect element in Set
//        System.out.println(fruitSet);

        // 8. Create new Stream of Map from List and then collect its key value pairs by key and value.length()
//        Map<String, Integer> fruitMap = fruits
//                .stream()
//                .collect(Collectors.toMap(            // Use to collect element in Map
//                        key -> key,
//                        value -> value.length()
//                ));
//        System.out.println(fruitMap);
    }
}
