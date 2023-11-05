/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cz.itnetwork.piskvorky;

import java.util.*;

/**
 *
 * @author Martin
 */
public class Piskvorky {

    public static void main(String[] args) {
    int hrac = 2; // Počáteční hráč
    boolean konec = false; // Zda již nastal konec hry
    int[][] plocha = new int[9][9]; // Hrací plocha
    String[] znaky = {" ", "O", "X"}; // Znaky kamenů (prázdno, kolečko, křížek)
    String[] hraci = {"nikdo", "hráč s kolečky", "hráč s křížky"}; // Názvy hráčů

    // Herní smyčka
    while (!konec) {
        // Vykreslení
        // ===================================================

        // První řádek s vodorovnými souřadnicemi
        System.out.print("  ");
        for (int i = 0; i < plocha[0].length; i++) {
            System.out.print((i + 1 + " "));
        }
        System.out.println();

        // Vykreslení hrací plochy
        for (int j = 0; j < plocha[1].length; j++) {
            // Číslo na začátku řádku
            System.out.print(j + 1 + " ");
            for (int i = 0; i < plocha[0].length; i++) {
                int znak = plocha[i][j];
                System.out.print(znaky[znak]);
            }
            System.out.println();
        }

        // Vyhodnocení výhry
        // ===================================================
        int symboluVyhra = 5;
        // Hledání 5ti stejných symbolů hráče
        int zaplneno = 0; // Počet zaplněných polí
        int symboluRadek = 0; // Počet stejných symbolů za sebou v řádku
        int symboluSloupec = 0; // Počet stejných symbolů za sebou ve sloupci

        // 2 vnořené cykly postupně projedou všechna políčka v hrací ploše, tato kontrola je docela jednoduchá
        for (int j = 0; j < plocha[1].length; j++) {
            for (int i = 0; i < plocha[0].length; i++) {
                // Kontrola zaplnění
                if (plocha[i][j] > 0) {
                    zaplneno++;
                }

                if (zaplneno == (plocha[0].length * plocha[1].length)) {
                    System.out.println("Remíza.");
                    konec = true;
                }

                // Počítání souvislých symbolů posledního hráče na tahu v řádku
                if (plocha[i][j] == hrac) {
                    symboluRadek++;
                } else {
                    // Symbol nebyl nalezen, vynulujeme počítadlo nepřerušené řady symbolů
                    symboluRadek = 0;
                }

                // Počítání souvislých symbolů posledního hráče na tahu ve sloupci
                if (plocha[j][i] == hrac) {
                    symboluSloupec++;
                } else {
                    // Symbol nebyl nalezen, vynulujeme počítadlo nepřerušeného sloupce symbolů
                    symboluSloupec = 0;
                }
                // Vyhodnocení výhry řadou nebo sloupcem
                if (symboluRadek == symboluVyhra || symboluSloupec == symboluVyhra) {
                    System.out.println("Vyhrál " + hraci[hrac]);
                    konec = true;
                }

            }
        }
        // Diagonály - tady je to horší :)
        int symboluDiagonalaLeva = 0; // Počet stejných symbolů za sebou v diagonále zleva doprava
        int symboluDiagonalaPrava = 0; // Počet stejných symbolů za sebou v diagonále zprava doleva

        // 2 vnořené cykly postupně projedou všechny diagonály
        for (int j = 0; j < (plocha.length == 0 ? 0 : plocha[0].length) * 2; j++) // Projíždíme 2x více řad než má hrací plocha, jinak bychom nalezli jen polovinu diagonál
        {
            for (int i = 0; i < plocha.length; i++) {
                // Diagonála zprava doleva
                int dy = (plocha.length == 0 ? 0 : plocha[0].length) - 1 - j + i; // Postupujeme od posledního řádku nahoru
                if (dy >= 0 && dy < (plocha.length == 0 ? 0 : plocha[0].length) && (plocha[plocha.length - 1 - i][dy] == hrac)) // Nevyjeli jsme z plochy a našli jsme hráčův kámen
                {
                    symboluDiagonalaLeva++;
                } else {
                    symboluDiagonalaLeva = 0; // Jsme mimo nebo tam hráč nemá kámen
                }

                // Diagonála zleva doprava
                if (dy >= 0 && dy < (plocha.length == 0 ? 0 : plocha[0].length) && (plocha[i][dy] == hrac)) // Nevyjeli jsme z plochy a našli jsme hráčův kámen
                {
                    symboluDiagonalaPrava++;
                } else {
                    symboluDiagonalaPrava = 0; // Jsme mimo nebo tam hráč nemá kámen
                }

                // Vyhodnocení výhry jednou z diagonál
                if (symboluDiagonalaLeva == symboluVyhra || symboluDiagonalaPrava == symboluVyhra) {
                    System.out.println("Vyhrál " + hraci[hrac]);
                    konec = true;
                }
            }
        }
        // Přidání kamenu hráče
        // ===================================================
        if (!konec) {
            // Prohození hráčů
            if (hrac == 1) {
                hrac = 2;
            } else {
                hrac = 1;
            }
            System.out.println("Na řadě je " + hraci[hrac]);
            boolean volno = false;
            int x = 1;
            int y = 1;

            // Načítání souřadnic dokud nezadá takové, kde je volno
            while (!volno) {

                Scanner sc = new Scanner(System.in); //Vytvoření vstupu konzole uživatele
                boolean jeSpravne = false; //Kontolní boolean, který slouží k potvrzení zadání správného vstupu

                while (!jeSpravne) {
                    try {
                        System.out.print("Zadej pozici X kam chceš táhnout: ");
                        x = Integer.parseInt(sc.nextLine()); //Přetypování (Parsování) vstupu uživatele do proměnné x, provádí se překlad ze String do Integer
                        jeSpravne = true; //Nastavení booleanu jeSpravne na true, které ukončení zacyklení cyklu while
                    } catch (Exception e) {
                        System.out.println("Zadaná hodnota není číslo nebo celé číslo, zadejte prosím celé číslo.");
                    }
                }

                jeSpravne = false;
                while (!jeSpravne) {
                    try {
                        System.out.print("Zadej pozici Y kam chceš táhnout: ");
                        y = Integer.parseInt(sc.nextLine()); //Přetypování (Parsování) vstupu uživatele do proměnné x, provádí se překlad ze String do Integer
                        jeSpravne = true; //Nastavení booleanu jeSpravne na true, které ukončení zacyklení cyklu while
                    } catch (Exception e) {
                        System.out.println("Zadaná hodnota není číslo nebo celé číslo, zadejte prosím celé číslo.");
                    }
                }

                // Souřadnice jsou v hrací ploše a není tam hráčův kámen
                if (x >= 1 && y >= 1 && x <= 9 && y <= 9 && plocha[x - 1][y - 1] == 0) {
                    volno = true;
                } else {
                    System.out.println("Neplatná pozice, zadej ji prosím znovu.");
                }

            }
            plocha[x - 1][y - 1] = hrac; // Uložení kamene hráče do hrací plochy
        }

    }
  }
}
