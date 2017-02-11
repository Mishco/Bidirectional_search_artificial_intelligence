# Obojsmerne prehladavanie pre hru 8
Projekt z umelej inteligencie http://www2.fiit.stuba.sk/~kapustik/z2d.html

Tento program slúži na nájdenie riešenia 8-hlavolamu. Hlavolam je zložený z 8 očíslovaných 
políčok a jedného prázdneho miesta(v tomto prípade označený nulou). Políčka je možné presúvať 
hore, dole, vľavo alebo vpravo, ale len ak je tým smerom medzera. Na začiatku je vždy východisková 
a cieľová pozícia a je potrebné nájsť postupnosť krokov, ktoré vedú z jednej pozície do druhej. 
 
V tomto konkrétnom prípade využívame obojsmerné hľadanie. Keďže poznáme aj začiatočný 
aj cieľový stav nie je to problém. Zo začiatočného stavu budeme hľadať postupne vpred, smerom 
k cieľovému stavu. Z cieľového stavu budeme hľadať akoby dozadu, teda smerom k začiatočnému 
stavu. Ak sa podarí týmto dvom hľadaniam nájsť stav(uzol), ktorý je spoločný, tak sme súčasne našli 
cestu zo začiatočnej do cieľovej pozície. 

Samotné prehľadávanie či už zo strany od začiatočného stavu alebo od koncového stavu 
prebieha na pomocou prehľadávania do šírky, takže určite nájde riešenie a zároveň sa využíva 
prehľadávanie do hĺbky s obmedzením hĺbky. 

Nevyužívame heuristiku, ktorá by nám dokázala zlepšiť hľadanie riešenia, pretože stavy sa 
generujú zaradom postupne a vyhodnocujú sa až po získaní spojového stavu(stav alebo uzol, 
v ktorom sa prehľadávania stretli). Týmto „backtrackingom“ získame celú cestu od začiatočného 
stavu ku konečnému stavu. 
