
public class Test {
    int zawartość = 0;
    static void argNiemodyfikowalny(final Test zmienna) {
        zmienna.zawartość = 1;
        zmienna = null;
    }
    static void argModyfikowalny(Test zmienna) {
        zmienna.zawartość = 1;
        zmienna = null;
    }
    public static void main(String[] args) {
        Test modyfikowalna = new Test();
        final Test niemodyfikowalna = new Test();
// tutaj wstaw instrukcje
    }
}