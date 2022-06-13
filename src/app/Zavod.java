package app;


import app.Tymy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class Zavod {

    private ArrayList<Tymy> muzi;
    private ArrayList<Tymy> zeny;

    private String jmenoZavodu;
    private int rokKonani;
    private int rocnik;
    private String[] rozhodci = new String[6];
    
    private String[] split;
    
    public Zavod(String jmenoZavodu, int rokKonani, int rocnik) {
        this.jmenoZavodu = jmenoZavodu;
        this.rokKonani = rokKonani;
        this.rocnik = rocnik;

        muzi = new ArrayList<>();
        zeny = new ArrayList<>();
    }

    public void setHlavniRozhodci(String jmeno) {
        rozhodci[0] = jmeno;
    }
    
    public String getHlavniRozhodci(){
        return rozhodci[0];
    }

    public void setStarter(String jmeno) {
        rozhodci[1] = jmeno;
    }

    public void setRozhodciZakladny(String jmeno) {
        rozhodci[2] = jmeno;
    }

    public void setRozhodciTercu(String jmeno1, String jmeno2) {
        rozhodci[3] = jmeno1;
        rozhodci[4] = jmeno2;
    }

    public void setRozhodciMereniHadic(String jmeno) {
        rozhodci[5] = jmeno;
    }

    public void registerTym(String nazev, char kategorie) {
        Tymy tym = new Tymy(nazev, kategorie);
        if (tym.getKategorie() == 'M') {
            muzi.add(tym);
        } else {
            zeny.add(tym);
        }
    }

    public void prohoditTymy(int tym1, int tym2, char kategorie) {
        if (kategorie == 'M') {
            muzi.forEach(tym -> {
                if (tym.getPoradi() == tym1) {
                    tym.setPoradi(tym2);
                } else if (tym.getPoradi() == tym2) {
                    tym.setPoradi(tym1);
                }
            });
        } else {
            zeny.forEach(tym -> {
                if (tym.getPoradi() == tym1) {
                    tym.setPoradi(tym2);
                } else if (tym.getPoradi() == tym2) {
                    tym.setPoradi(tym1);
                }
            });
        }
    }

    public void deleteTym(int regNum, char kategorie) {
        boolean tymRemoved;
        if (kategorie == 'M') {
            Tymy tym = findTym(regNum, kategorie);
            tymRemoved = muzi.remove(tym);
        } else {
            Tymy tym = findTym(regNum, kategorie);
            tymRemoved = zeny.remove(tym);
        }
        if (!tymRemoved) {
            throw new NoSuchElementException("Tým nebyl nalezen!");
        }
    }

    public Tymy findTym(int regNum, char kategorie) {
        if (kategorie == 'M') {
            for (Tymy tym : muzi) {
                if (tym.getPoradi() == regNum) {
                    return tym;
                }
            }
        } else {
            for (Tymy tym : zeny) {
                if (tym.getPoradi() == regNum) {
                    return tym;
                }
            }
        }
        throw new NoSuchElementException("Tým nenalezen!");
    }

    public void setLP(int regNum, char kategorie, double cas) {
        findTym(regNum, kategorie).setLP(cas);
    }

    public void setPP(int regNum, char kategorie, double cas) {
        findTym(regNum, kategorie).setPP(cas);
    }

    public void setBoth(int regNum, char kategorie, double casLP, double casPP) {
        findTym(regNum, kategorie).setBoth(casLP, casPP);
    }

    public void setNeplatny(int regNum, char kategorie) {
        findTym(regNum, kategorie).setNeplatnyPokus();
    }

    public void kontrolaPlatnosti() {
        muzi.forEach(tym -> {
            tym.kotrolaPlatnosti();
        });
        zeny.forEach(tym -> {
            tym.kotrolaPlatnosti();
        });
    }

    public void sortByPoradi() {
        Collections.sort(muzi, (Tymy o1, Tymy o2) -> o1.getPoradi() - o2.getPoradi());
        Collections.sort(zeny, (Tymy o1, Tymy o2) -> o1.getPoradi() - o2.getPoradi());
    }

    public String startovniListina() {
        StringBuilder s = new StringBuilder();
        s.append("Muži:").append("\n");
        muzi.forEach(tym -> {
            s.append(tym.getPoradi()).append(". ").append(tym.getTym()).append("\n");
        });
        s.append("\n");
        s.append("Ženy:").append("\n");
        zeny.forEach(tym -> {
            s.append(tym.getPoradi()).append(". ").append(tym.getTym()).append("\n");
        });
        return s.toString();
    }

    public String nejlepiSestriky() {
        double nejlepsiM = 0;
        int tymM = 1;
        for (Tymy tym : muzi) {
            if (tym.lepsiCas() != 9999.99 && tym.lepsiCas() != 0.0) {
                if (nejlepsiM == 0) {
                    nejlepsiM = tym.lepsiCas();
                    tymM = tym.getPoradi();
                } else if (nejlepsiM > tym.lepsiCas()) {
                    nejlepsiM = tym.lepsiCas();
                    tymM = tym.getPoradi();
                }
            }
        }
        double nejlepsiZ = 0;
        int tymZ = 1;
        for (Tymy tym : zeny) {
            if (tym.lepsiCas() != 9999.99 && tym.lepsiCas() != 0.0) {
                if (nejlepsiZ == 0) {
                    nejlepsiZ = tym.lepsiCas();
                    tymZ = tym.getPoradi();
                } else if (nejlepsiZ > tym.lepsiCas()) {
                    nejlepsiZ = tym.lepsiCas();
                    tymZ = tym.getPoradi();
                }
            }
        }
        StringBuilder s = new StringBuilder();
        if (findTym(tymM, 'M').getLP() == nejlepsiM) {
            s.append("Muži: ").append(findTym(tymM, 'M').getTym()).append(" LP: ").append(nejlepsiM).append("\n");
        } else {
            s.append("Muži: ").append(findTym(tymM, 'M').getTym()).append(" PP: ").append(nejlepsiM).append("\n");
        }
        if (findTym(tymZ, 'Z').getLP() == nejlepsiZ) {
            s.append("Ženy: ").append(findTym(tymZ, 'Z').getTym()).append(" LP: ").append(nejlepsiZ);
        } else {
            s.append("Ženy: ").append(findTym(tymZ, 'Z').getTym()).append(" PP: ").append(nejlepsiZ);
        }
        return s.toString();
    }

    public String nejlepsiSoustriky() {
        int pom = 0;
        double nejlepsiM = 0;
        int tymM = 1;
        for (Tymy tym : muzi) {
            if (tym.getLP() != 9999.99 && tym.getPP() != 9999.99 && tym.getLP() != 0.0 && tym.getPP() != 0.0) {
                if (pom == 0) {
                    nejlepsiM = tym.rozdilovyCas();
                    tymM = tym.getPoradi();
                    pom = 1;
                } else if (nejlepsiM > tym.rozdilovyCas()) {
                    nejlepsiM = tym.rozdilovyCas();
                    tymM = tym.getPoradi();
                }
            }
        }
        pom = 0;
        double nejlepsiZ = 0;
        int tymZ = 1;
        for (Tymy tym : zeny) {
            if (tym.getLP() != 9999.99 && tym.getPP() != 9999.99 && tym.getLP() != 0.0 && tym.getPP() != 0.0) {
                if (pom == 0) {
                    nejlepsiZ = tym.rozdilovyCas();
                    tymZ = tym.getPoradi();
                    pom = 1;
                } else if (nejlepsiZ > tym.rozdilovyCas()) {
                    nejlepsiZ = tym.rozdilovyCas();
                    tymZ = tym.getPoradi();
                }
            }
        }
        StringBuilder s = new StringBuilder();
        if (findTym(tymM, 'M').getLP() == nejlepsiM) {
            s.append("Muži: ").append(findTym(tymM, 'M').getTym()).append(" ").append(nejlepsiM).append("\n");
        } else {
            s.append("Muži: ").append(findTym(tymM, 'M').getTym()).append(" ").append(nejlepsiM).append("\n");
        }
        if (findTym(tymZ, 'Z').getLP() == nejlepsiZ) {
            s.append("Ženy: ").append(findTym(tymZ, 'Z').getTym()).append(" ").append(nejlepsiZ);
        } else {
            s.append("Ženy: ").append(findTym(tymZ, 'Z').getTym()).append(" ").append(nejlepsiZ);
        }
        return s.toString();
    }
    
    public void vyslednePoradiM(){
        double[] vysledne = new double[100];
        int i = 0;
        for(Tymy tym : muzi){
            vysledne[i] = tym.lepsiCas();
            i++;
        }
        Arrays.sort(vysledne);
        int pom;
        for(Tymy tym : muzi){
            pom = 0;
            for (int h = 0; h < vysledne.length; h++) {
                if(vysledne[h] == 0){
                    pom++;
                }
                if(tym.lepsiCas() == vysledne[h]){
                    tym.setVyslednePoradi(h+1-pom);
                }
            }
        }
    }
    
    public void vyslednePoradiZ(){
        double[] vysledne = new double[100];
        int i = 0;
        for(Tymy tym : zeny){
            vysledne[i] = tym.lepsiCas();
            i++;
        }
        Arrays.sort(vysledne);
        int pom;
        for(Tymy tym : zeny){
            pom = 0;
            for (int h = 0; h < vysledne.length; h++) {
                if(vysledne[h] == 0){
                    pom++;
                }
                if(tym.lepsiCas() == vysledne[h]){
                    tym.setVyslednePoradi(h+1-pom);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        vyslednePoradiM();
        vyslednePoradiZ();
        s.append("Muži:").append("\n");
        muzi.forEach(tym -> {
            s.append(tym.getPoradi()).append(". ").append(tym.toString()).append(" ").append(tym.getVyslednePoradi()).append("\n");
        });
        s.append("\n");
        s.append("Ženy:").append("\n");
        zeny.forEach(tym -> {
            s.append(tym.getPoradi()).append(". ").append(tym.toString()).append(" ").append(tym.getVyslednePoradi()).append("\n");
        });
        return s.toString();
    }
    
    public int getPocetTymuM(){
        int pocet = 0;
        pocet = muzi.stream().map(_item -> 1).reduce(pocet, Integer::sum);
        return pocet;
    }
    
    public int getPocetTymuZ(){
        int pocet = 0;
        pocet = zeny.stream().map(_item -> 1).reduce(pocet, Integer::sum);
        return pocet;
    }
    
    public void seznamRozhodcich(File rozhodci) throws FileNotFoundException, IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(rozhodci))){
            String line, jmeno;
            int pom = 0;
            while((line = reader.readLine()) != null){
                jmeno = line;
                
                this.rozhodci[pom] = jmeno;
                pom++;
            }
        }
    }
    
    public void stratovniListina(File startovka) throws FileNotFoundException, IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(startovka))){
            String line,nazev, kategorie;
            
            while((line = reader.readLine()) != null){
                split = line.split(";");
                
                nazev = split[0];
                kategorie = split[1];
                
                if("M".equals(kategorie)){
                    muzi.add(new Tymy(nazev, 'M'));
                }else if("Z".equals(kategorie)){
                    zeny.add(new Tymy(nazev, 'Z'));
                }
            }
            reader.close();
        }
    }
    
    public void vysledkovaListina(File vysledkovka) throws IOException{
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(vysledkovka)))){
            vyslednePoradiM();
            vyslednePoradiZ();
            writer.println("Pořadí;Název;Kategorie;LP;PP;Výsledný čas;Výsledné pořadí");
            for(Tymy tym : muzi){
                writer.print(tym.getPoradi());
                writer.print(";");
                writer.print(tym.getTym());
                writer.print(";");
                writer.print(tym.getKategorie());
                writer.print(";");
                writer.print(tym.getLP());
                writer.print(";");
                writer.print(tym.getPP());
                writer.print(";");
                writer.print(tym.vyslednyCas());
                writer.print(";");
                writer.print(tym.getVyslednePoradi());
                writer.print("\n");
            }
            for(Tymy tym : zeny){
                writer.print(tym.getPoradi());
                writer.print(";");
                writer.print(tym.getTym());
                writer.print(";");
                writer.print(tym.getKategorie());
                writer.print(";");
                writer.print(tym.getLP());
                writer.print(";");
                writer.print(tym.getPP());
                writer.print(";");
                writer.print(tym.vyslednyCas());
                writer.print(";");
                writer.print(tym.getVyslednePoradi());
                writer.print("\n");
            }
        }
    }
    
    public void saveToBinary(File result) throws FileNotFoundException, IOException{
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(result, true))){
            out.writeInt(muzi.size());
            for(Tymy tym : muzi){
                out.writeUTF(tym.getTym());
                out.writeChar(tym.getKategorie());
                out.writeDouble(tym.getLP());
                out.writeDouble(tym.getPP());
                out.writeDouble(tym.vyslednyCas());
            }
            for(Tymy tym : zeny){
                out.writeUTF(tym.getTym());
                out.writeChar(tym.getKategorie());
                out.writeDouble(tym.getLP());
                out.writeDouble(tym.getPP());
                out.writeDouble(tym.vyslednyCas());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Zavod zavod = new Zavod("Kosmonosy", 2022, 25);
        //zavod.registerTym("Obrubce", 'M');
        //zavod.registerTym("DC", 'm');
        //zavod.registerTym("Kosmonosy", 'M');
        //zavod.registerTym("Kosmonosy", 'Z');
        //zavod.registerTym("Bukovno", 'Z');
        //zavod.deleteTym(2, 'M');
        //zavod.prohoditTymy(1, 2, 'M');
        //zavod.setBoth(3, 'M', 16.45, 16.46);
        //zavod.setNeplatny(2, 'M');
        //zavod.setBoth(1, 'M', 16.98, 17.05);
        //zavod.setBoth(1, 'Z', 16.11, 18.11);
        //zavod.sortByPoradi();
        //zavod.kontrolaPlatnosti();
        zavod.seznamRozhodcich(new File("Rozhodci.csv"));
        System.out.println(zavod.getHlavniRozhodci());
        zavod.stratovniListina(new File("Start.csv"));
        System.out.println(zavod.startovniListina());
        //System.out.println(zavod);
        //System.out.println(zavod.nejlepiSestriky());
        //System.out.println(zavod.nejlepsiSoustriky());
    }
}
