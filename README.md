# Bidirectional searching for solve 8 game

Artificial intelligence project http://www2.fiit.stuba.sk/~kapustik/z2d.html

## Problem and solution

This program is used to find an 8-puzzle solution. The puzzle consists of 8 numbered spaces and one empty space (zero in this case). Boxes can be moved up, down, left or right, but only if there is a space in the free space. It is always the starting point
and target position and you need to find a sequence of steps that lead from one position to another.

In this particular case, we use the bidirectional search. Because we do not know the initial setting or the target state is not a problem. From the initial state, we will gradually try to progress to the target state. At the same time, we will look back from the target state towards the initial state. If these two searches find a state (node), then we have found a way from start to finish.

The crawl itself, either side by side or end, is done using the crawl, so it will certainly find a solution while using depth crawl.

We do not use heuristics to improve the search for a solution, because states are generated incrementally and are not evaluated until the link state is obtained (the state or node in which the crawl is encountered). With this "backtracking" we get all the way from the initial state to the final state.

## Slovak version

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
