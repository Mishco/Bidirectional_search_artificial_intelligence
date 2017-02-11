package obojsmernePrehladavanie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;   // ukladanie do suborov
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import java.util.Scanner;   // praca so vstupom
import java.util.Vector;    //vektor pre nacitanie pola

/**
 *
 * @author Michal
 */
public class Central implements Serializable {

    private Calculate first_calc = null;
    Algoritmus alg = null;
    Vector result = null;
    static int[][] StartPosition;// new int[][10];
    static int[][] EndPosition;// new int[10][10];
    static int height;
    static int width;

    // prazdny konstruktor
    public Central() {
    }

    //vnorena trieda na vypocet
    private class Calculate extends Thread {

        int count = 0;
        boolean run = false;

        // na zrusenie vypoctu uprostred
        public void cancel() {
            run = false;
        }

        // ak metoda bezi, vracia true, inak false
        public boolean isRunning() {
            return run;
        }

        public int getCount() {
            return count;
        }

        public void run(int vyska, int sirka) {
            Uzol tmp_Start = new Uzol(vyska, sirka);
            Uzol tmp_End = new Uzol(vyska, sirka);

            // obe hodnoty ziskavame zo vstupu
            // teda ich musi zadat uzivatel
            tmp_Start.set(StartPosition);
            tmp_End.set(EndPosition);

            tmp_Start.hlbka_uzol = tmp_End.hlbka_uzol = 0;
            tmp_Start.previous_uzol = tmp_End.previous_uzol = 0;

            run = true;
            alg = new Algoritmus();

            // maximalna hlbka je nastavena na 50, ale mozeme ju pytat aj od uzivatela
            alg.zacni(tmp_Start, tmp_End, 100, vyska, sirka);

            while (run && !alg.isFindSolution()) {
                alg.StepByStep();
                if (!alg.getStartAktualUzol().equals(tmp_Start)) {
                    tmp_Start = alg.getStartAktualUzol();
                    //    System.out.println("start:  \n" + tmp_Start.LongString());
                } else if (!alg.getEndAktualUzol().equals(tmp_End)) {
                    tmp_End = alg.getEndAktualUzol();
                    //    System.out.println("koniec: \n" + tmp_End.LongString());
                }

            }
            result = alg.getResult();
            tmp_Start = alg.getStartAktualUzol();
            tmp_End = alg.getEndAktualUzol();

            //   System.out.println("Aktualny zaciatocny stav: \n" + tmp_Start.LongString());
            //   System.out.println("Aktualny koncovy stav: \n" + tmp_End.LongString());        
            tmp_End.set(EndPosition);
            tmp_Start.set(StartPosition);
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String args[]) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadaj cislo: \n" + "1 - pre nacitanie z konzoly");
        System.out.println("2 - pre nacitanie zo subora");
        System.out.println("3 - pre koniec");
        int key = sc.nextInt();
        Central tmp = new Central();

        switch (key) {
            case 1:

                tmp.inputData();  //nacita z konzoly  
                tmp.Begin();
                break;
            case 2:
                tmp.inputFromFileData();
                tmp.Begin();
                break;
            case 3:
                break;

        }
   }

    void Begin() {
        first_calc = new Calculate();
        first_calc.run(height, width);
    }

    protected void inputData() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Riesenie 8-hlavolamu: \n");
        System.out.println("Zadaj rozmery hry(2*3,4*2,3*3), prvy rozmer je vyska");
        height = sc.nextInt(); // vyska
        width = sc.nextInt();  // sirka

        StartPosition = new int[height][width];
        EndPosition = new int[height][width];
        System.out.println("Zadaj startovaciu poziciu: ");
        for (int i = 0; i < height; i++) {
            System.out.printf("Zadaj %d-riadok: ", i + 1);
            for (int j = 0; j < width; j++) {
                StartPosition[i][j] = sc.nextInt();
            }
        }
        System.out.println("Zadaj koncovy stav: ");

        for (int i = 0; i < height; i++) {
            System.out.printf("Zadaj %d-riadok: ", i + 1);
            for (int j = 0; j < width; j++) {
                EndPosition[i][j] = sc.nextInt();
            }
        }
    }

    protected void inputFromFileData() throws IOException {
        FileReader fr = null;
        File file = new File("test.txt");
        StringBuffer buf = new StringBuffer();

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            char[] cbuf = new char[(int) file.length()];
            br.read(cbuf);
            buf.append(cbuf); // precita cele po znakoch
            br.close();
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
        String str = new String(buf);
        str = str.replaceAll(" ", "");
        str = str.replaceAll("(\\r|\\n)", "");
        
        height = Character.getNumericValue(str.charAt(0));
        width = Character.getNumericValue(str.charAt(1));

        int index = 2;
        int num = 0;
        char key;
        StartPosition = new int[height][width];
        EndPosition = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                key = str.charAt(index);
                if (key == 'm') {
                    num = 0;
                    StartPosition[i][j] = num;
                } else {
                    num = Character.getNumericValue(key);
                    StartPosition[i][j] = num;
                }
                index++;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                key = str.charAt(index);
                if (key == 'm') {
                    num = 0;
                    EndPosition[i][j] = num;
                } else {
                    num = Character.getNumericValue(key);
                    EndPosition[i][j] = num;
                }
                index++;
            }
        }
    }
}
