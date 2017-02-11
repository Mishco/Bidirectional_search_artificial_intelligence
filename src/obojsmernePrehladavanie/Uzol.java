package obojsmernePrehladavanie;

import java.util.Vector;

/**
 *
 * @author Michal
 */
public class Uzol extends Object {

    public static int count = 1; //aktualne rieseni uzol
    public int[][] stav_uzol;// // pole ulozenych hodnot
    public int hlbka_uzol; // hlbka uzla
    public int number_uzol; // cislo uzla vo vypocte
    public int previous_uzol; // predchodca uzla
    public String nameOfDirection; // DOLE, HORE, VLAVO, VPRAVO

    public int vyska;
    public int sirka;
    
    // konstruktor, nastavi hodnoty na nulu
    Uzol(int height, int width) {
        number_uzol = count++;
        hlbka_uzol = 0;
        previous_uzol = 0;
        vyska = height;
        sirka = width;
        stav_uzol = new int[vyska][sirka]; 
    }

    // konstruktor konkretneho objektu
    Uzol(Uzol u) {
        number_uzol = count++;
        hlbka_uzol = u.hlbka_uzol;
        previous_uzol = u.previous_uzol;
        nameOfDirection = u.nameOfDirection;
        vyska = u.vyska;
        sirka = u.sirka;
        
        stav_uzol = new int[vyska][sirka]; 
        
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                stav_uzol[i][j] = u.stav_uzol[i][j];
            }
        }
    }

    // nastavenie funkcie na nove hodnoty
    public void set(int data[][]) {
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                stav_uzol[i][j] = data[i][j];
            }
        }
    }

    // porovnania uzla s dalsim uzlom 
    public boolean equals(Uzol tmp) {
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                if (tmp.stav_uzol[i][j] != stav_uzol[i][j]) {
                    return false;
                }
            }
        }
        return true; //plati rovnost inac vracia false
    }

    // rozvinie uzol, prejde vsetky styri varianty
    public Vector posun() {
        Uzol tmp;
        Vector vec = new Vector();
        int i = 0, j = 0;
        for (i = 0; i < vyska; i++) {
            for (j = 0; j < sirka; j++) {
                if (stav_uzol[i][j] == 0) //hladame prazdne miesto
                {
                    break;
                }
            }
            if (j < 3) {
                break;
            }
        }

        //prechadzam do vsetkych smerov
        if ((tmp = change(i, j, i - 1, j, "DOLE")) != null) {
            vec.add(tmp); // DOLE
        }
        if ((tmp = change(i, j, i + 1, j, "HORE")) != null) {
            vec.add(tmp); // HORE
        }
        if ((tmp = change(i, j, i, j - 1, "VPRAVO")) != null) {
            vec.add(tmp); // VLAVO
        }
        if ((tmp = change(i, j, i, j + 1, "VLAVO")) != null) {
            vec.add(tmp); // VPRAVO
        }
        return vec;
    }

    protected Uzol change(int x, int y, int x2, int y2, String nameOfDirection) {
        //kontrola spravnosti suradnic
        if (x < 0 || x >  vyska-1) {
            return null;
        }
        if (x2 < 0 || x2 > vyska-1) {
            return null;
        }
        if (y < 0 || y > sirka-1) {
            return null;
        }
        if (y2 < 0 || y2 > sirka-1) {
            return null;
        }

        Uzol tmp = new Uzol(this);
        int temp_a;

        // SWAP hodnot
        temp_a = tmp.stav_uzol[x][y];
        tmp.stav_uzol[x][y] = tmp.stav_uzol[x2][y2];
        tmp.stav_uzol[x2][y2] = temp_a;

        tmp.hlbka_uzol = this.hlbka_uzol + 1; // presli sme hlbsie
        tmp.previous_uzol = this.number_uzol;
        tmp.nameOfDirection = nameOfDirection; // zapamatanie smeru pohybu
        
      //  System.out.println("Rozvinutie: \nPosledny Smer:" + this.toString() + "  ->\nNovy Smer: " +tmp.toString());
        
        return tmp;
    }

    // vypis
    @SuppressWarnings("override")
    public String toString() {
        StringBuilder str = new StringBuilder();
        int deep = 0;
        boolean preklop = false; // po prekroceni stredu, musi vypisovat smery v opacnom poradi
        if (number_uzol == 1) {
            str.append("START: \n");
        } else {
            if (nameOfDirection != null) {
                str.append(nameOfDirection + "\n");
            } else {
                if (number_uzol == 2) {
                    str.append("END: \n");
                    preklop = true;
                } else if (number_uzol > deep) {
                    str.append("spojovaci uzol\n");
                    deep = number_uzol; // najhlbsi
                } else
                str.append("nebol pohyb\n");
            }
        }
            for (int i = 0; i < vyska; i++) {
                for (int j = 0; j < sirka; j++) {
                    str.append(" " + stav_uzol[i][j]);
                }
                str.append("\n");
            }
            //  str.append(number_uzol + "\n");
            String tmp = new String(str);
            System.out.println(tmp);
            return tmp;
        }
    

    public String LongString() {
        String str = "";
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                str += "  " + stav_uzol[i][j];
            }
            str += "\n";
        }
       // str += "Uzol cislo: " + this.number_uzol;
        // str += "\n\tpredchodca " + this.previous_uzol;
        //  str += "\n\thlbka uzla " + this.hlbka_uzol;
        return str;
    }

    public void printAll() {
        System.out.println(this.toString());
    }
    
    
    public String BackString() {
        StringBuilder str = new StringBuilder();
        int deep = 0;
        //presne opacne vypise kvoli prechodu na stav z ENDu
            if (nameOfDirection == "HORE") {
                str.append("DOLE\n");
            } else if (nameOfDirection == "DOLE") {
                str.append("HORE\n");
            } else if (nameOfDirection == "VPRAVO") {
                str.append("VLAVO\n");
            } else if (nameOfDirection == "VLAVO") {
                str.append("VPRAVO\n");
            }
            
            if (number_uzol == 2) // koncovy uzol
                str.append("END: \n");
            
            for (int i = 0; i < vyska; i++) {
                for (int j = 0; j < sirka; j++) {
                    str.append(" " + stav_uzol[i][j]);
                }
                str.append("\n");
            }
            //  str.append(number_uzol + "\n");
            String tmp = new String(str);
            System.out.println(tmp);
            return tmp;
        }
}
