function enter(pi) {
	if (pi.getEvanIntroState("dt00=o;dt01=o;mo00=o;mo01=o;mo10=o;mo02=o;mo11=o")) {
		return false;
	}
	pi.updateEvanIntroState("dt00=o;dt01=o;mo00=o;mo01=o;mo10=o;mo02=o;mo11=o");
	pi.showWZEffect("Effect/OnUserEff.img/guideEffect/evanTutorial/evanBalloon11",1);
	return true;
}