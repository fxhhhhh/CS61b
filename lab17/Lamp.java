

public class Lamp {
    int brightness;

    Lamp(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public int hashCode() {
        return brightness;
    }

    @Override
    public boolean equals(Object o) {
        return ((Lamp) o).brightness == brightness;
    }

    public static void main(String[] args) {
        Lamp a = new Lamp(1);
        Lamp b = new Lamp(2);

        HashMap<Lamp, Integer> map = new HashMap<>();

        map.put(b, 0);
        map.put(a, 1);
        map.put(a, 2);

        System.out.println(map.get(a));  // print statement 1
        System.out.println(map.get(b));  // print statement 2

        map.put(b, 3);
        a.brightness = 2;
        map.put(b, 4);

        System.out.println(map.get(a));  // print statement 3
        System.out.println(map.get(b));  // print statement 4
        System.out.println(map.get(new Lamp(1))); // print statement 5
    }
}
