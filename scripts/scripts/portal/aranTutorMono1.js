function enter(pi) {
    if (pi.getInfoQuest(21002).equals("mo1=o")) {
	pi.Aran_Start();
	pi.updateInfoQuest(21002, "mo1=o;mo2=o");
	pi.showWZEffect("Effect/OnUserEff.img/guideEffect/aranTutorial/legendBalloon2",1);
    }
}