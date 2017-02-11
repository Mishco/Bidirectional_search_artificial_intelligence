package obojsmernePrehladavanie;

import java.io.Serializable; // kniznica pre ukladanie do suborov
import java.util.Vector;     // kniznica vectorov, teda jednorozmernych poli

/**
 *
 * @author Michal
 */
@SuppressWarnings("UseOfObsoleteCollectionType") // kvoli java.util.Vector
public class Algoritmus implements Serializable {

    protected boolean automat = true;
    protected boolean finish = false; // koniec hladania
    protected boolean findSolution = false;

    protected Uzol start; // startovaci uzol 
    protected Uzol end;   // koncovy uzol
    // start
    protected Vector StartOpen;  //zoznam otvorenych uzlov 
    protected Vector StartClose; //zoznam zatvorenych uzlov
    // end
    protected Vector EndOpen;  // zoznam otvorenych uzlov
    protected Vector EndClose; // zoznam zatvorenych uzlov

    protected int MaxDepth;
    protected Uzol StartAktulUzol = null; // aktualny uzol od startu
    protected Uzol EndAktualUzol = null; // aktualny uzol od konca

    protected int StartDepth; // hlbky pre Start a End
    protected int EndDepth;

    protected Vector result = null; // konecny vysledok
    
    protected int height;
    protected int width;
    

    // prazdny konstruktor
    public Algoritmus() {
    }

    //zisti ci je skoncene alebo nie
    public boolean isFinish() {
        return finish;
    }

    public boolean isFindSolution() {
        return findSolution;
    }

    // aktualny uzol pre START
    public Uzol getStartAktualUzol() {
        return StartAktulUzol;
    }

    // aktualny uzol pre END
    public Uzol getEndAktualUzol() {
        return EndAktualUzol;
    }

    //get pre StartOpen a StartClose
    public Vector getStartOpen() {
        return StartOpen;
    }

    public Vector getStartClose() {
        return StartClose;
    }

    //get pre EndOpen a EndClose
    public Vector getEndOpen() {
        return EndOpen;
    }

    public Vector getEndClose() {
        return EndClose;
    }

    // vrati udaje START = vsetky OPEN a CLOSE
    public Vector getStartData() {
        Vector v = new Vector();
        v.addAll(StartClose);
        v.addAll(StartOpen);
        return v;
    }

    // vrati udaje END = vsetky OPEN a CLOSE
    public Vector getEndData() {
        Vector v = new Vector();
        v.addAll(EndClose);
        v.addAll(EndOpen);
        return v;
    }

    public void zacni(Uzol start, Uzol end, int MaxDepth, int vyska, int sirka) {
        finish = false;

        this.start = start;
        this.end = end;
        this.MaxDepth = MaxDepth;
        
        height = vyska;
        width = sirka;

        //aktualny prehlasi na zaciatocny a aj koncovy kedze prehladavame obojsmerne
        StartAktulUzol = start;
        EndAktualUzol = end;
        // postupne budeme zaplnat vysledkami
        StartOpen = new Vector();
        StartClose = new Vector();
        EndOpen = new Vector();
        EndClose = new Vector();

        StartDepth = 0; // vynuluje obe hlbky na 0 
        EndDepth = 0;
        result = null;
        //Presny postup ako pisali v zadani      
        //1.Vytvor počiatočný uzol a umiestni medzi vytvorené a zatiaľ nespracované uzly

        StartOpen.add(start); // najskor priradime start aj do vektora aj do tabulky 
        EndOpen.add(end);        //podobne spravime aj s Cielovym uzlom.

        // hned aj vyskusame ci sa nerovnaju a teda ukoncime program
        if (start.equals(end)) {
            finish = true; // nasli sme riesenie
            findSolution = true;
        }
    }

    // postupne prechadza krok po kroku
    // aj zacina od Startu a potom
    // uplne rovnakym postupom od konca
    // takze v strede najde spravne riesenie
    public void StepByStep() {
        Vector expand;
        boolean direction = true; // v pripade ze chceme ist do zaciatku mame true, od konca false
        result = null;

//2.Ak neexistuje žiadny vytvorený a zatiaľ nespracovaný uzol, skonči s neúspechom - riešenie neexistuje
        // ak sme minuly vsetk prazdne uzly tak sme nenasli riesenie
        if (StartOpen.isEmpty() && EndOpen.isEmpty()) {
            finish = true;
            findSolution = false; // sice sme presli, ale nenasli sme riesenie
            return;
        }

        // musime vybrat spravny smer
        if (StartAktulUzol.hlbka_uzol < MaxDepth && EndAktualUzol.hlbka_uzol < MaxDepth) {
            if (StartAktulUzol.hlbka_uzol <= EndAktualUzol.hlbka_uzol && !StartOpen.isEmpty()) {
                direction = true; // zacina od startu, pretoze hlbka od zaciatku je mensia ako od konca
            } else if (EndAktualUzol.hlbka_uzol < StartAktulUzol.hlbka_uzol && !EndOpen.isEmpty()) {
                direction = false; // pojde od konca, pretoze hlbka od konca je mensia ako od zaciatku
            } else if (!StartOpen.isEmpty()) {
                // End Open je prazdny a teda ideme od zaciatku
                direction = true;
            } else {
                direction = false;
            }

        } else { // hlbsie sa uz ist neda a kedze sme znova nenasli riesenie ukonci neuspesne
            finish = true;
            findSolution = false;
            return;
        }
      //  System.out.println("\n--------------------------------------------------\n");
     /*   if(direction) {
             System.out.println("Smer: " + direction + " startu\n");
         } else
            System.out.println("Smer: " + direction + " konca\n");
        */
         
        // ak ideme od zaciatku, co sme zistili pred chvilou
        if (direction) { //START
            int i;
            StartAktulUzol = (Uzol) StartOpen.get(0); //vrati prvy uzol
            StartOpen.remove(0); // a vymaze ho zo zoznamu

            StartClose.add(StartAktulUzol); // prida ho do ukonceneho, teda ho "oznacime"
            //   System.out.println("Start Aktualny: \n" + StartAktulUzol.toString());

            // skusi posunut(rozsirit) aktualny vrchol
            expand = StartAktulUzol.posun();

            int zaradane = 0;

            //zaradi vsetkych potomkov(nasledovnikov) , ktori este nie su v STARTzozname close
            while (!expand.isEmpty()) { // kym neprejde cez vsetkych
                Uzol u = (Uzol) expand.remove(0);
                //expand.remove(0); // samotny remove vracia hodnotu, ktoru vymazal

              //   System.out.println("Strom: \n" + u.toString());
                
                for (i = 0; i < StartClose.size(); i++) {
                    if (((Uzol) StartClose.get(i)).equals(u)) {
                        break;
                    }
                }
                if (i == StartClose.size()) { // novy prvok sa nenachadza v StartClose
                    StartOpen.add(0, u); // zaradime na uplny zaciatok
                    //      System.out.println("zaradeny do StarOpen: \n" + u.toString());
                    zaradane++;
                }
            }
            // 

            for (int x = 0; x < zaradane; x++) {
                Uzol tmp_u = (Uzol) StartOpen.get(x);

                for (i = 0; i < EndOpen.size(); i++) {
                    if (((Uzol) EndOpen.get(i)).equals(tmp_u)) {
                        EndAktualUzol = (Uzol) EndOpen.get(i);
                        break;
                    }
                }
                if (i == EndOpen.size()) { // nie je v EndOpen,
                    for (int k = 0; k < zaradane; k++) {
                        tmp_u = (Uzol) StartOpen.get(k);
                        for (i = 0; i < EndClose.size(); i++) {
                            if (((Uzol) EndClose.get(i)).equals(tmp_u)) {
                                if (EndOpen.size() != 0) {
                                    EndAktualUzol = (Uzol) EndOpen.get(i);
                                }
                                break;
                            }
                        }
                        if (i != EndClose.size()) { // aktualny stav uz je v EndClose
                            //teda nasli sme cestu
                            StartAktulUzol = tmp_u;

                            //       System.out.println("Nasli sme rozvinutie v EndClose: \n" + tmp_u.toString());
                            //       System.out.println("Nasli sme rozvinutie v EndClose Aktual: \n" + StartAktulUzol.toString());
                            finish = true;
                            findSolution = true;
                            return;
                        }
                    }
                } else { // aktualny stav uz je v EndOpen, znova sme nasli cestu
                    StartAktulUzol = tmp_u;
                    finish = true;
                    findSolution = true;

                    //     System.out.println("nasli sme rozvinutie v EndOpen: \n" + tmp_u.toString());
                    //    System.out.println("nasli sme rozvinutie v EndClose aktual: \n" + StartAktulUzol.toString());
                    return;
                }
            }
        } else { // ide od konca
            EndAktualUzol = (Uzol) EndOpen.remove(0); // vymaze a vrati
            EndClose.add(EndAktualUzol); // vlozime do oznacenych 
            //   System.out.println("End Aktualny: \n" + EndAktualUzol.toString());

            expand = EndAktualUzol.posun();

            int zaradene = 0; // podobne ako ked sme sli od zaciatku

            //   DefaultMutableTreeNode X_node = (DefaultMutableTreeNode) 
            //           EndNodeHash.get(new Integer (EndAktualUzol.number_uzol));
            // prechadzame cez vsetkych potomkov, ktori este nie su v zozname
            while (!expand.isEmpty()) {
                int i;
                Uzol u = (Uzol) expand.remove(0);  // maze aj vracia hodnotu
                //        DefaultMutableTreeNode Y_node = new DefaultMutableTreeNode(u);

                //        X_node.add(Y_node);
                //       EndNodeHash.put(new Integer(u.number_uzol), Y_node);
                for (i = 0; i < EndClose.size(); i++) {
                    if (((Uzol) EndClose.get(i)).equals(u)) {
                        break;
                    }
                }
                if (i == EndClose.size()) { //novy stav sa nenachadza v EndClose
                    //   System.out.println("zaradeny do EndOpen: \n"  + u.toString());

                    EndOpen.add(0, u);
                    zaradene++;
                }
            }

            //podobne ako pri prechadzani od zaciatku ak bol 
            // vygenerovany uzol, ktory sa nachadza v StartOpen preskoci
            // alebo StartClose, else pokracuj
            for (int x = 0; x < zaradene; x++) {
                int i;
                Uzol tmp_u = (Uzol) EndOpen.get(x);
                for (i = 0; i < StartOpen.size(); i++) {
                    if (((Uzol) StartOpen.get(i)).equals(tmp_u)) {
                        StartAktulUzol = (Uzol) StartOpen.get(i);
                        break;
                    }
                }
                if (i == StartOpen.size()) { // stav nie je V startOpen, prechadza cez StarClose
                    for (int k = 0; k < zaradene; k++) {
                        tmp_u = (Uzol) EndOpen.get(k);
                        for (i = 0; i < StartClose.size(); i++) {
                            if (((Uzol) StartClose.get(i)).equals(tmp_u)) {
                                StartAktulUzol = (Uzol) StartOpen.get(i);
                                break;
                            }
                        }
                        if (i != StartClose.size()) { //nasli sme cestu
                            // pretoze aktualny stav uz je v StartClose
                            EndAktualUzol = tmp_u;
                            EndClose.add(tmp_u);
                            //   System.out.println("nasli sme rozvinutie v StartClose: \n" + tmp_u.toString());
                            finish = true;
                            findSolution = true;
                            return;
                        }
                    }
                } else { // aktual nie je StarOpen, taktiez sme nasli cestu
                    EndAktualUzol = tmp_u;
                    //  System.out.println("nasli sme rozvinutie v StartOpen: \n" + tmp_u.toString());
                    finish = true;
                    findSolution = true;
                    return;
                }
            }
        }
    } // koniec funkcie krok po kroku

    // najdenie vyslednej cesty vracia Vector, teda pole
    public Vector getResult() {
        if (result == null) {
            Vector vect = new Vector();
            //zoznam uzlov s
            StringBuilder path = new StringBuilder();

            Uzol u_1 = new Uzol(StartAktulUzol);
            Uzol u_2 = new Uzol(EndAktualUzol);
            u_1.number_uzol = StartAktulUzol.number_uzol;
            u_2.number_uzol = EndAktualUzol.number_uzol;

            // vysledna cesta
            // najde ju backtrackovanim
            // teda postupne prechadza od poslednej moznosti, ktoru vytvoril
            // az kym neprejde v START verzii ku startu
            int i = 0;
            while (!u_1.equals(start)) {
                vect.add(0, u_1);
                for (i = 0; i < StartClose.size(); i++) {
                    if (((Uzol) StartClose.get(i)).number_uzol == u_1.previous_uzol) {
                        u_1 = (Uzol) StartClose.get(i);
                        i = StartClose.size() + 1;
                        break;
                    }
                }
            }

            vect.add(0, start);
            vect.toString(); // vypise cast ktoru vyriesil od STARTu

            for (i = 1; i < vect.size(); i++) {
                Uzol temp = (Uzol) vect.get(i);
                path.append(temp.nameOfDirection + ",");
                if (i % 10 == 0) {
                    path.append("\n");
                }
            }
            int kdeSkoncil = vect.size();

            // v END verzii ku koncu , celu cestu stale uklada
            // do vectora VECT, ktory nakoniec vypise
            while (!u_2.equals(end)) {
                vect.add(u_2);
                for (i = 0; i < EndClose.size(); i++) {
                    if (((Uzol) EndClose.get(i)).number_uzol == u_2.previous_uzol) {
                        u_2 = (Uzol) EndClose.get(i);
                        i = EndClose.size() + 1;
                        break;
                    }
                }
            }
            int index = i;
            vect.add(end); //vypise nakoniec 
            // vect.toString();

            result = vect; // vysledny vector neupravovany

            for (i = index + 1; i < vect.size(); i++) {
                Uzol temp = (Uzol) vect.get(i);
                temp.BackString();
            }

         
            // POZOR
            // prehladava do oboch smerov
            // t.z. ze do polovice, resp. bodu kedy sa stretli ide kazdy od toho svojho vrcholu
            // ak chceme zrekonstruovat celu cestu od toho bodu
            // sa musia smery preklopit
            for (i = kdeSkoncil; i < vect.size(); i++) {
                Uzol temp = (Uzol) vect.get(i);
                if (temp.nameOfDirection == "HORE") {
                    path.append("DOLE, ");
                } else if (temp.nameOfDirection == "DOLE") {
                    path.append("HORE,");
                } else if (temp.nameOfDirection == "VPRAVO") {
                    path.append("VLAVO,");
                } else if (temp.nameOfDirection == "VLAVO") {
                    path.append("VPRAVO,");
                }

                if (i % 10 == 0) {
                    path.append("\n");
                }
            }
            
            System.out.println("Cela cesta: ");
            System.out.println(path.toString());
            
            System.out.println("\nCelkovy pocet uzlov: " + vect.size());
            System.out.println("Najvacsia dosiahnuta hlbka uzla od Startu: " + StartAktulUzol.hlbka_uzol);
            System.out.println("Najvacsia dosiahnuta hlbka uzla od Konca: " + EndAktualUzol.hlbka_uzol);
        }
        return result;
    }

}
