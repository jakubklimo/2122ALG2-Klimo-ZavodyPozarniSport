package ui;


import app.Zavod;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import utils.ExceptionFileNotFound;
import utils.ExceptionInputOutput;
import utils.ExceptionTymNenalezen;

public class Application {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws ExceptionTymNenalezen, ExceptionInputOutput, ExceptionFileNotFound, IOException, FileNotFoundException, DocumentException {
        System.out.println("Zadej název Soutěže:");
        String nazev = sc.nextLine();
        System.out.println("Zadej rok konání:");
        int rokKonani = sc.nextInt();
        System.out.println("Zadej ročník soutěže:");
        int rocnik = sc.nextInt();
        Zavod zavod = new Zavod(nazev, rokKonani, rocnik);
        int volbaRozhodci;
        do {
            System.out.println("Zadej rozhodčí:");
            System.out.println("1) Hlavni rozhodci");
            System.out.println("2) Startér");
            System.out.println("3) Rozhodčí na základně");
            System.out.println("4) Rozhodčí u terčů");
            System.out.println("5) Rozhodčí na měření hadic");
            System.out.println("6) Načíst rozhočí ze souboru");
            System.out.println("0) Hotovo");
            volbaRozhodci = sc.nextInt();
            switch (volbaRozhodci) {
                case 1 -> {
                    System.out.println("Zadej jméno a přijmení:");
                    String rozhodciHl = sc.next();
                    rozhodciHl += sc.next();
                    zavod.setHlavniRozhodci(rozhodciHl);
                }
                case 2 -> {
                    System.out.println("Zadej jméno a přijmení:");
                    String rozhodciS = sc.next();
                    rozhodciS += sc.next();
                    zavod.setStarter(rozhodciS);
                }
                case 3 -> {
                    System.out.println("Zadej jméno a přijmení:");
                    String rozhodciZ = sc.next();
                    rozhodciZ += sc.next();
                    zavod.setRozhodciZakladny(rozhodciZ);
                }
                case 4 -> {
                    System.out.println("Zadej jméno a přijmení:");
                    System.out.println("První:");
                    String rozhodciT1 = sc.next();
                    rozhodciT1 += sc.next();
                    System.out.println("Druhý:");
                    String rozhodciT2 = sc.next();
                    rozhodciT2 += sc.next();
                    zavod.setRozhodciTercu(rozhodciT1, rozhodciT2);
                }
                case 5 -> {
                    System.out.println("Zadej jméno a přijmení:");
                    String rozhodciM = sc.next();
                    rozhodciM += sc.next();
                    zavod.setRozhodciMereniHadic(rozhodciM);
                }
                case 6 -> {
                    zavod.seznamRozhodcich(new File("data/Rozhodci.csv"));
                    volbaRozhodci = 0;
                    break;
                }
                case 0 -> {
                    break;
                }
                default -> {
                    System.out.println("Špatná volba");
                    System.out.println("\n");
                }
            }
        } while (volbaRozhodci != 0);
        System.out.println("1) Zadat týmy ručně");
        System.out.println("2) Náhrát týmy ze souboru");
        int volbaZapsani = sc.nextInt();
        switch (volbaZapsani) {
            case 1:
                int volbaKategorie;
                do {
                    System.out.println("1) Zadat tým mužů");
                    System.out.println("2) Zadat tým žen");
                    System.out.println("0) Hotovo");
                    volbaKategorie = sc.nextInt();
                    switch (volbaKategorie) {
                        case 1:
                            System.out.println("Název: (místo mezer '_')");
                            String nazevTymuM = sc.next();
                            zavod.registerTym(nazevTymuM, 'M');
                            break;
                        case 2:
                            System.out.println("Název: (místo mezer '_')");
                            String nazevTymuZ = sc.next();
                            zavod.registerTym(nazevTymuZ, 'Z');
                            break;
                        default:
                            System.out.println("Špatná volba");
                            System.out.println("\n");
                    }
                } while (volbaKategorie != 0);
                break;
            case 2:
                zavod.stratovniListina(new File("data/Start.csv"));
                break;
            default:
                System.out.println("Špatná volba");
                System.out.println("\n");
        }
        System.out.println(zavod.startovniListina());
        int volbaUprava;
        do {
            System.out.println("1) Prohodit tým");
            System.out.println("2) Smazat tým");
            System.out.println("0) Pokračovat");
            volbaUprava = sc.nextInt();
            switch (volbaUprava) {
                case 1:
                    System.out.println("1) Prohodit týmy mužů");
                    System.out.println("2) Prohodit týmy žen");
                    int volbaKat = sc.nextInt();
                    switch (volbaKat) {
                        case 1:
                            int prvniM;
                            do{
                                System.out.println("Pořadí prvního týmu:");
                                prvniM = sc.nextInt();
                            }while(prvniM >= zavod.getPocetTymuM()+1);
                            int druhyM;
                            do{
                                System.out.println("Pořadí druhého týmu:");
                                druhyM = sc.nextInt();
                            }while(druhyM >= zavod.getPocetTymuM()+1);
                            zavod.prohoditTymy(prvniM, druhyM, 'M');
                            zavod.sortByPoradi();
                            break;
                        case 2:
                            int prvniZ;
                            do{
                                System.out.println("Pořadí prvního týmu:");
                                prvniZ = sc.nextInt();
                            }while(prvniZ >= zavod.getPocetTymuZ()+1);
                            int druhyZ;
                            do{
                                System.out.println("Pořadí druhého týmu:");
                                druhyZ = sc.nextInt();
                            }while(druhyZ >= zavod.getPocetTymuZ()+1);
                            zavod.prohoditTymy(prvniZ, druhyZ, 'Z');
                            zavod.sortByPoradi();
                            break;
                        default:
                            System.out.println("Špatná volba");
                            System.out.println("\n");
                    }
                    System.out.println(zavod.startovniListina());
                    break;
                case 2:
                    System.out.println("1) Smazat tým mužů");
                    System.out.println("2) Smazat tým žen");
                    int volbaKat1 = sc.nextInt();
                    switch (volbaKat1) {
                        case 1:
                            int smazatM;
                            do{
                                System.out.println("Pořádí týmu:");
                                smazatM = sc.nextInt();
                            }while(smazatM >= zavod.getPocetTymuM()+1);
                            zavod.deleteTym(smazatM, 'M');
                            break;
                        case 2:
                            int smazatZ;
                            do{
                                System.out.println("Pořádí týmu:");
                                smazatZ = sc.nextInt();
                            }while(smazatZ >= zavod.getPocetTymuZ()+1);
                            zavod.deleteTym(smazatZ, 'Z');
                            break;
                        default:
                            System.out.println("Špatná volba");
                            System.out.println("\n");
                    }
                    System.out.println(zavod.startovniListina());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Špatná volba");
                    System.out.println("\n");
            }
        } while (volbaUprava != 0);
        System.out.println(zavod.startovniListina());
        System.out.println("Zápis časů mužů:");
        for (int i = 1; i < zavod.getPocetTymuM()+1; i++) {
            System.out.format("%d. tým\n",i);
            System.out.println("1) Platný čas");
            System.out.println("2) Neplatný čas");
            int platM = sc.nextInt();
            switch(platM){
                case 1:
                    System.out.println("Čas levého terče: (xx,xx)");
                    double casLPM = sc.nextDouble();
                    System.out.println("Čas pravého terče: (xx,xx)");
                    double casPPM = sc.nextDouble(); 
                    zavod.setBoth(i, 'M', casLPM, casPPM);
                    break;
                case 2:
                    zavod.setNeplatny(i, 'M');
            }
            System.out.println(zavod.getCasy(i, 'M'));
        }
        System.out.println("Zápis časů žen:");
        for (int i = 1; i < zavod.getPocetTymuZ()+1; i++) {
            System.out.format("%d. tým\n",i);
            System.out.println("1) Platný čas");
            System.out.println("2) Neplatný čas");
            int platZ = sc.nextInt();
            switch(platZ){
                case 1:
                    System.out.println("Čas levého terče: (xx,xx)");
                    double casLPM = sc.nextDouble();
                    System.out.println("Čas pravého terče: (xx,xx)");
                    double casPPM = sc.nextDouble(); 
                    zavod.setBoth(i, 'Z', casLPM, casPPM);
                    break;
                case 2:
                    zavod.setNeplatny(i, 'Z');
            }
            System.out.println(zavod.getCasy(i, 'Z'));
        }
        zavod.kontrolaPlatnosti();
        System.out.println(zavod);
        System.out.println("Nejlepší sestřiky:");
        System.out.println(zavod.nejlepiSestriky());
        System.out.println("Nejlepší soustřiky:");
        System.out.println(zavod.nejlepsiSoustriky());
        String exp;
        boolean platny = false;
        do{
            System.out.println("Exportovat do souboru? (a/n)");
            exp = sc.next();
            if(exp.equalsIgnoreCase("a") || exp.equalsIgnoreCase("n")){
                platny = true;
            }
        }while(platny != true);
        if(exp.equalsIgnoreCase("a")){
            zavod.vysledkovaListina(new File("data/vysledky.csv"));
            zavod.saveToPDF(new File("data/result.pdf"));
        }
        
    }

}
