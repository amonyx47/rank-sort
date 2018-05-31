import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Pouzitie programu rank sort.");
        System.out.println("Pocitat sa bude pole dlzky 50 000 so vsetkymi aktualne dostupnymi procesormi (simulujeme threadmi), t.j. kolko procesorov - tolko threadov.");
        System.out.println("Najprv sa zadava jedno cislo: kolko prvkov v poli chceme mat.");
        System.out.println("Potom sa zadava jedno cislo: 0 ak chceme nahodne cisla a 1 (alebo ine, rozdielne od 0) ak chceme rovnake cisla v poli.");

        System.out.println("Zadaj dlzku pola: ");
        int elemQuantity = scan.nextInt();
        int threadNum = Runtime.getRuntime().availableProcessors();
        System.out.println("Pocet procesorov (threadov): " + threadNum);
        System.out.println("Vygenerovat pole nahodnych (0) alebo vygenerovat pole rovnakych cisel (1): ");
        int random = scan.nextInt();
        if (random == 0) {
            System.out.println("Generujem pole nahodnych cisel...");
        } else {
            System.out.println("Generujem pole rovnakych cisel...");
        }


        // inicializujeme polia
        System.out.println("Inicializujem polia...");
        int[] arrayToSort = new int[elemQuantity];
        int[] resultArray = new int[elemQuantity];


        // vytvorime pole, ktore budeme sortovat
        System.out.println("Zaplnam pole hodnotami...");
        if (random == 0) {
            for (int i = 0; i < elemQuantity; i++) {
                Random rand = new Random();
                arrayToSort[i] = rand.nextInt();
            }
        } else {
            Random rand = new Random();
            int randomNum = rand.nextInt();
            Arrays.fill(arrayToSort, randomNum);
        }

        // start merania
        long start = System.currentTimeMillis();

        // pocet blokov
        int blockSize = elemQuantity / threadNum;

        // spustime thready
        RankThread threads[] = new RankThread[threadNum];
        for (int i = 0, j = 0; i < threadNum; i++, j += blockSize) {
            threads[i] = new RankThread(arrayToSort, resultArray, threadNum, i);
            threads[i].start();
        }

        // spojime thready
        try {
            for (int i = 0; i < threadNum; i++) {
                threads[i].join();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // zastavime timer
        long elapsedTimeMillis = System.currentTimeMillis() - start;
        float elapsedTimeSec = elapsedTimeMillis / 1000F;

        // vyprintujeme utriedene pole
        System.out.println("\nVysledne pole:");
        for (int i = 0; i < elemQuantity; i++) {
            System.out.print(resultArray[i] + " ");
            if (i % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();

        // vyprintujeme aj dlzku vypoctu
        System.out.println("Cas sortu: " + elapsedTimeSec + "s");

    }

}
