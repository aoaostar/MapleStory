function enter(pi) { // tutor00
    if (pi.getInfoQuest(21002).equals("normal=o;arr0=o;arr1=o;mo1=o;mo2=o;mo3=o;mo4=o")) {
        pi.playerMessage(5, "请连续按Ctrl键，尝试连续攻击.");
        pi.updateInfoQuest(21002, "normal=o;arr0=o;arr1=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o");
        pi.showWZEffect("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialGuide2", 1);
    }
}