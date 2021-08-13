/*
	Ludibirum Maze PQ
*/

function act() {
    var rand = (Math.random() * 2) + 1;
    var q = 0;
    var q2 = 0;
    if (rand < 2) {
	q = 4;
	q2 = 3;
    } else {
	q = 3;
	q2 = 4;
    }
	rm.spawnMonster(9400250, q);
	rm.spawnMonster(9400251, q2);
}