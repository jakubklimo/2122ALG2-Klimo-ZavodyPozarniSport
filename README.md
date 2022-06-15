2122ALG2-Klimo-ZavodyPozarniSport

<h1>Závody v požárním sportu</h1>
<p>Vedení jedné z lig v požárním sportu tě požádalo o vytvoření programu, který usnadní pořadatelům jak zápis týmů a v jakých kategoriích budou soutěžit 
tak zápis výsledků z časomíry a následné vyhodnocení pořadí a určení nejrychlejšího proudaře. Zápis bude vypadat následovně:</p>
<ul>
  <li>Před začátkem vytvoříš v programu závod (jméno závodu, rok konání, ročník, jména rohodčích k jednotlivým postům(hlavní rozhodčí, startér,          rozhodčí na základně, dva rozhodčí na terčích a jeden nebo dva na měření hadic))</li>
  <li>Přijde tým k prezenci a ty do programu zapíšeš jméno týmu a kategorii</li>
  <li>Pořadí se tvoří samo (možnost v průběhu prezence týmy prohodit, vymyzat tým)</li>
  <li>Týmy se automaticky rozhodí dle zadaných kategorií (muži,ženy)</li>
  <li>Po vytvoření startovní listiny se kazdému týmu zapisují 2 časy (čas levého terče a čas pravého terče), přesáhne-li čas jakéhokoli terče dvě minuty je pokus             automaticky nelatný</li>
  <li>Vyhodnocení vysledků - týmy se seřadí od nejrychlejšího času po nejpomalejší (každá kategorie zvlašť), počítá se vždy ten pomalejší čas z těch dvou                     zapsaných</li>
  <li>Vyhodnocení nejrychlejšího proudaře - určí se ten nejrychlejší čas ze všech zapsaných časů (každá kategorie zvlášť) a vypíše se zda to byl levý nebo pravý              proud</li>
  <li>Vyhodnocení nejlepšího soustřiku (nejmenší rozdíl mezi oběma časy jednoho týmu)</li>
</ul>  

<h2>Vytvoř Aplikaci na ovládání programu, která bude obsahovat:</h2>
<ul>
  <li>Po zapnutí umožní vytvořit závod a zadat všechny potřebné parametry</li>
  <li>Po vytvoření závodu umožnit uživateli zadávat týmy</li>
  <li>Postupně podle startovního pořadí přidávat jednotlivé časy k týmu</li>
  <li>Vyhodnocení výsledků</li>
</ul>
<p>Startovní listinu bude možné nahrát ze souboru .csv</p>
<p>Po ukončení bude možnost exportovat výsledkovou listinu do souboru</p>
<h1>Řešení</h1>
<h2>Popis řešení v menu</h2>
<li>Zadaní jména soutěže, roku konání a ročníku soutěže</li>
<li>Zadání rozhodčích (volitelné), možnost nahrát ze souboru</li>
<li>Zadání týmů, možnost nahrát ze souboru</li>
<li>Možnost smazání týmu, nebo prohození týmů</li>
<li>Zadání časů ke každému týmu, platný/neplatný čas</li>
<li>Vypsat výsledky do konzole + nejlepší sestřiky a soustřiky</li>
<li>Možnost exportovat výsledky do souboru csv, pdf a binárního souboru</li>
<h2>Popis vstupních a výstupních souborů</h2>
<p>Vstupní soubory jsou typu .csv, kde každá informace je oddělena středníkem (;). Oba soubory jsou ve složce data.</p>
<p>Výstupní soubory jsou dohromady 3, jeden je typu .csv, druhý typu .pdf a třetí .bin. Všechny soubory se ukládají do složky data.</p>
<h2>Class diagram</h2>
![ClassDiagram](https://user-images.githubusercontent.com/100779403/170049803-5dbfbb24-25d3-4681-b3d6-e11bb4651d1a.png)
<h1>Externí knihovna</h1>
<p>Pro export dat do pdf jsem zvolil knihovnu iTextpdf, kde jsem do knihovny nahrál soubor .jar ve kterém byla knihovna pdf.</p>
<li>Document - vytvoření dokumentu</li>
<li>PdfWriter - zápis do dokumentu</li>
<li>FontFactory - vytvoření fontu</li>
<li>Chunk - jednotlivé části dokumentu</li>
<li>Paragraph - část textu - přidávám do něj jednotlivé chunky</li>
