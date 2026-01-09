public class Lambda {
    public static void main(String[] args) {

//        // 1. Traditional way to create object of class who will only have single method in it or in it's parent's interface
//        Walkable walkable = new WalkFast();
//        walkable.walk(5);

        // 2. Lambda Expression
        Walkable walkable1 = (steps, isEnabled) -> {
            System.out.println("Walking " + steps + " steps");
            return 2*steps;
        };
        walkable1.walk(6, true);

        // 3. Direct way of Lambda expression
        Walkable walkable2 = ((steps, isEnabled) -> 2*steps);
        System.out.println(walkable2.walk(8,false));
    }
}

@FunctionalInterface
interface Walkable{
    int walk(int steps, boolean isEnabled);
}

//  This class is for Traditional way (for 1)
//class WalkFast implements Walkable{
//    @Override
//    public int walk(int steps) {
//        System.out.println("Walking " + steps + " steps");
//        return 2*steps;
//    }
//}