function enter(pi) {
    if (pi.getEvanIntroState("mo30=o")) {
        return false;
    }
    pi.updateEvanIntroState("mo30=o");
    pi.showWZEffect("Effect/OnUserEff.img/guideEffect/evanTutorial/evanBalloon30", 1);
    return true;
}