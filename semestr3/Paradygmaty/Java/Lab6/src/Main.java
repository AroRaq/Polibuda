public class Main {
    static int[] insert(int[] tab, int val) {
        int[] newTab = new int[tab.length + 1];
        int idx = 0;
        for ( ; idx < tab.length && tab[idx] < val; idx++) {
            newTab[idx] = tab[idx];
        }
        newTab[idx++] = val;
        for ( ; idx < newTab.length; idx++) {
            newTab[idx] = tab[idx-1];
        }
        return newTab;
    }

    public static void main(String[] args) {
        int[] tab = {1,2,3,4,5,6,8};
        int[] newTab = insert(tab, 8);
        for (int i : newTab)
            System.out.println(i);
    }
}
