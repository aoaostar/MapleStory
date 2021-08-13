/*
	Ludibirum Maze PQ
*/

function act() {
    var rand = (Math.random() * 2) + 1;
    var q = 0;
    var q2 = 0;
    if (rand < 2) {
	q = 3;
	q2 = 4;
    } else {
	q = 4;
	q2 = 3;
    }
	rm.spawnMonster(9400248, q);
	rm.spawnMonster(9400249, q2);
}