function enter(pi) { // tutor00
    if (pi.getInfoQuest(21002).equals("normal=o;arr0=o;arr1=o;arr2=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o")) {
	pi.playerMessage(5, "连续攻击后，通过方向键和攻击键可以实现组合攻击。");
	pi.updateInfoQuest(21002, "cmd=o;normal=o;arr0=o;arr1=o;arr2=o;mo1=o;chain=o;mo2=o;mo3=o;mo4=o");
	pi.showWZEffect("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialGuide3",1);
    }
}