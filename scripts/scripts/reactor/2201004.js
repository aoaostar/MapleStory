/*
Papulatus Reactor: Performs the Papulatus commands
*/

function act(){
try {
    rm.mapMessage(5, "The dimensional hole has been filled by the <Piece of Cracked Dimension>.");
    rm.changeMusic("Bgm09/TimeAttack");
    rm.spawnMonster(8500000, -410, -400);
    rm.getMap(220080000).setReactorState();
} catch(e) {
    rm.mapMessage(5, "Error: " + e);
}
}