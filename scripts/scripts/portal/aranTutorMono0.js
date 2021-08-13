function enter(pi) {
    if (pi.getInfoQuest(21002).equals("")) {
	pi.updateInfoQuest(21002, "mo1=o");
	pi.showWZEffect("Effect/OnUserEff.img/guideEffect/aranTutorial/legendBalloon1",1);
    }
}