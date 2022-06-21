package app;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
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
import utils.ExceptionChybnyRozmer;
import utils.ExceptionFileNotFound;
import utils.ExceptionInputOutput;
import utils.ExceptionTymNenalezen;

public class Zavod {

    private ArrayList<Tym> muzi;
    private ArrayList<Tym> zeny;

    private String jmenoZavodu;
    private int rokKonani;
    private int rocnik;
    private String[] rozhodci = new String[6];
    
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
        Tym tym = new Tym(nazev, kategorie);
        if (tym.getKategorie() == 'M') {
            muzi.add(tym);
        } else {
            zeny.add(tym);
        }
    }

    public void prohoditTymy(int tym1, int tym2, char kategorie) {
        if(kategorie == 'M'){
            if(tym1 > getPocetTymuM() || tym1 <= 0){
                throw new ExceptionChybnyRozmer("Neplatny rozmer " + tym1);
            }else if(tym2 > getPocetTymuM() || tym2 <= 0){
                throw new ExceptionChybnyRozmer("Neplatny rozmer " + tym2);
            }
        }else{
            if(tym1 > getPocetTymuZ() || tym1 <= 0){
                throw new ExceptionChybnyRozmer("Neplatny rozmer " + tym1);
            }else if(tym2 > getPocetTymuZ() || tym2 <= 0){
                throw new ExceptionChybnyRozmer("Neplatny rozmer " + tym2);
            }
        }
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
        if(kategorie == 'M'){
            if(regNum > getPocetTymuM() || regNum <= 0){
                throw new ExceptionChybnyRozmer("Neplatny rozmer " + regNum);
            }
        }else{
            if(regNum > getPocetTymuZ() || regNum <= 0){
                throw new ExceptionChybnyRozmer("Neplatny rozmer " + regNum);
            }
        }
        boolean tymRemoved;
        if (kategorie == 'M') {
            Tym tym = findTym(regNum, kategorie);
            tymRemoved = muzi.remove(tym);
        } else {
            Tym tym = findTym(regNum, kategorie);
            tymRemoved = zeny.remove(tym);
        }
        if (!tymRemoved) {
            throw new ExceptionTymNenalezen("Tým nenalezen!");
        }
    }

    public Tym findTym(int regNum, char kategorie) {
        if (kategorie == 'M') {
            for (Tym tym : muzi) {
                if (tym.getPoradi() == regNum) {
                    return tym;
                }
            }
        } else {
            for (Tym tym : zeny) {
                if (tym.getPoradi() == regNum) {
                    return tym;
                }
            }
        }
        throw new ExceptionTymNenalezen("Tým nenalezen!");
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
    
    public String getCasy(int poradi, char kat){
        StringBuilder s = new StringBuilder();
        Tym tym = findTym(poradi, kat);
        s.append("LP: ").append(tym.getLP()).append(" PP: ").append(tym.getPP()).append(" Výsledný: ").append(tym.vyslednyCas());
        return s.toString();
    }

    public void sortByPoradi() {
        Collections.sort(muzi, (Tym o1, Tym o2) -> o1.getPoradi() - o2.getPoradi());
        Collections.sort(zeny, (Tym o1, Tym o2) -> o1.getPoradi() - o2.getPoradi());
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
        for (Tym tym : muzi) {
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
        for (Tym tym : zeny) {
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
        for (Tym tym : muzi) {
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
        for (Tym tym : zeny) {
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
        for(Tym tym : muzi){
            vysledne[i] = tym.lepsiCas();
            i++;
        }
        Arrays.sort(vysledne);
        int pom;
        for(Tym tym : muzi){
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
        for(Tym tym : zeny){
            vysledne[i] = tym.lepsiCas();
            i++;
        }
        Arrays.sort(vysledne);
        int pom;
        for(Tym tym : zeny){
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
        }catch (FileNotFoundException e) {
            throw new ExceptionFileNotFound("Zadaný soubor nebyl nalezen!");
        } catch (IOException e) {
            throw new ExceptionInputOutput("Chyba při vstupu!");
        }
    }
    
    public void stratovniListina(File startovka){
        try (BufferedReader reader = new BufferedReader(new FileReader(startovka))){
            String line,nazev, kategorie;
            String[] split;
            while((line = reader.readLine()) != null){
                split = line.split(";");
                
                nazev = split[0];
                kategorie = split[1];
                
                if("M".equals(kategorie)){
                    muzi.add(new Tym(nazev, 'M'));
                }else if("Z".equals(kategorie)){
                    zeny.add(new Tym(nazev, 'Z'));
                }
            }
        }catch (FileNotFoundException e) {
            throw new ExceptionFileNotFound("Zadaný soubor" + startovka.getName() + "nebyl nalezen!");
        }catch (IOException e) {
            throw new ExceptionInputOutput("Chyba při vstupu!");
        }
    }
    
    public void vysledkovaListina(File vysledkovka) throws IOException{
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(vysledkovka)))){
            vyslednePoradiM();
            vyslednePoradiZ();
            writer.println("Pořadí;Název;Kategorie;LP;PP;Výsledný čas;Výsledné pořadí");
            for(Tym tym : muzi){
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
            for(Tym tym : zeny){
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
        }catch (FileNotFoundException e) {
            throw new ExceptionFileNotFound("Zadaný soubor nebyl nalezen!");
        }
    }
    
    public void saveToBinary(File result) throws FileNotFoundException, IOException{
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(result, true))){
            out.writeInt(muzi.size());
            for(Tym tym : muzi){
                out.writeUTF(tym.getTym());
                out.writeChar(tym.getKategorie());
                out.writeDouble(tym.getLP());
                out.writeDouble(tym.getPP());
                out.writeDouble(tym.vyslednyCas());
            }
            out.writeInt(zeny.size());
            for(Tym tym : zeny){
                out.writeUTF(tym.getTym());
                out.writeChar(tym.getKategorie());
                out.writeDouble(tym.getLP());
                out.writeDouble(tym.getPP());
                out.writeDouble(tym.vyslednyCas());
            }
        }
    }
    
    public String readFromBinary(File binRead) throws IOException{
        StringBuilder s = new StringBuilder();
        try (DataInputStream in = new DataInputStream(new FileInputStream(binRead))){
            Boolean end = false;
            int numMuzi = 0;
            int numZeny = 0;
            String nazev;
            char kategorie;
            double Lp,Pp,vysledny;
            
            while(!end){
                try{
                    numMuzi = in.readInt();
                    for (int i = 0; i < numMuzi; i++) {
                        nazev = in.readUTF();
                        kategorie = in.readChar();
                        Lp = in.readDouble();
                        Pp = in.readDouble();
                        vysledny = in.readDouble();
                        s.append(nazev).append(" ").append(kategorie).append(" ").append(Lp).append(" ").append(Pp).append(" ").append(vysledny).append("\n");
                    }
                    numZeny = in.readInt();
                    for (int i = 0; i < numZeny; i++) {
                        nazev = in.readUTF();
                        kategorie = in.readChar();
                        Lp = in.readDouble();
                        Pp = in.readDouble();
                        vysledny = in.readDouble();
                        s.append(nazev).append(" ").append(kategorie).append(" ").append(Lp).append(" ").append(Pp).append(" ").append(vysledny).append("\n");
                    }
                }catch (EOFException e) {
                    end = true;
                }
            }
        }
        return s.toString();
    }
    
    public void saveToPDF(File result) throws FileNotFoundException, DocumentException{
        vyslednePoradiM();
        vyslednePoradiZ();
        Document doc = new Document() {};
        PdfWriter.getInstance((com.itextpdf.text.Document) doc, new FileOutputStream(result));
        doc.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk nazev = new Chunk();
        nazev.setFont(font);
        nazev.append(jmenoZavodu).append(" ").append(rokKonani).append(" ").append(rocnik).append(".ročník").append("\n");
        float[] columnWidths = {1f,1f,1f,1f,1f,1f,1f};
        PdfPTable table = new PdfPTable(7);
        table.setWidths(columnWidths);
        table.addCell("Pořadí");
        table.addCell("Název");
        table.addCell("Kategorie");
        table.addCell("Lp");
        table.addCell("Pp");
        table.addCell("Výsledný čas");
        table.addCell("Výsledné pořadí");
        for(Tym tym : muzi){
            table.addCell("" + tym.getPoradi());
            table.addCell(tym.getTym());
            table.addCell("" + tym.getKategorie());
            table.addCell("" + tym.getLP());
            table.addCell("" + tym.getPP());
            table.addCell("" + tym.vyslednyCas());
            table.addCell("" + tym.getVyslednePoradi());
        }
        for(Tym tym : zeny){
            table.addCell("" + tym.getPoradi());
            table.addCell(tym.getTym());
            table.addCell("" + tym.getKategorie());
            table.addCell("" + tym.getLP());
            table.addCell("" + tym.getPP());
            table.addCell("" + tym.vyslednyCas());
            table.addCell("" + tym.getVyslednePoradi());
        }
        
        Paragraph out = new Paragraph();
        out.add(nazev);
        out.add(table);
        doc.add(out);
        doc.close();
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, DocumentException {
        Zavod zavod = new Zavod("Kosmonosy", 2022, 25);
        zavod.registerTym("Obrubce", 'M');
        zavod.registerTym("DC", 'm');
        //zavod.registerTym("Kosmonosy", 'M');
        //zavod.registerTym("Kosmonosy", 'Z');
        //zavod.registerTym("Bukovno", 'Z');
        //zavod.deleteTym(2, 'M');
        //zavod.prohoditTymy(1, 2, 'M');
        //zavod.setBoth(3, 'M', 16.45, 16.46);
        zavod.setNeplatny(2, 'M');
        zavod.setBoth(1, 'M', 16.98, 17.05);
        //zavod.setBoth(1, 'Z', 16.11, 18.11);
        //zavod.sortByPoradi();
        //zavod.kontrolaPlatnosti();
        //zavod.seznamRozhodcich(new File("Rozhodci.csv"));
        //System.out.println(zavod.getHlavniRozhodci());
        //zavod.stratovniListina(new File("Start.csv"));
        //System.out.println(zavod.startovniListina());
        zavod.saveToPDF(new File("Result.pdf"));
        zavod.saveToBinary(new File("Binary"));
        System.out.println(zavod.readFromBinary(new File("Binary")));
        //System.out.println(zavod);
        //System.out.println(zavod.nejlepiSestriky());
        //System.out.println(zavod.nejlepsiSoustriky());
    }
}
