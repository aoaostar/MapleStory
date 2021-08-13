function enter(pi) {
    if (pi.isQuestActive(1031)) {
        pi.showWZEffect("UI/tutorial.img/25", 1);
        return true;
    }
    return false;
}