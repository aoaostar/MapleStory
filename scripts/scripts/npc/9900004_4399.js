var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var 返回市场 = "#fEffect/SkillName1.img/1001003/返回市场#";
var 返回市场2 = "#fEffect/SkillName1.img/1001003/返回市场2#";
var 返回市场3 = "#fEffect/SkillName1.img/1001003/返回市场3#";
var 返回市场4 = "#fEffect/SkillName1.img/1001003/返回市场4#";
var 返回市场5 = "#fEffect/SkillName1.img/1001003/返回市场5#";
var 返回市场6 = "#fEffect/SkillName1.img/1001003/返回市场6#";
var 返回市场7 = "#fEffect/SkillName1.img/1001003/返回市场7#";
var 返回市场8 = "#fEffect/SkillName1.img/1001003/返回市场8#";
var 返回市场9 = "#fEffect/SkillName1.img/1001003/返回市场9#";
var 枫叶至尊10 = "#fEffect/SkillName1.img/1001003/枫叶至尊10#";






function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
	    if (cm.getMapId() == 20000 || cm.getMapId() == 10000) {
            cm.sendOk(" 自 由 冒 险 岛 境 外 无 法 使 用 此 功 能 。");
            cm.dispose();
        }


  
    else if (status == 0) {
        var 
         selStr = "--------------------------------------------\r\n";
		selStr += " 你当前主界面风格为；\r\n\r\n";
		
	 if (cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142099) && cm.getBossRank("枫叶风格",2) == 1) { 
    selStr += "     "+ 枫叶至尊10 +" \r\n\r\n";
	selStr += "  #r#L100#关闭至尊主页面#l#k \r\n\r\n";	
	} else if (cm.getBossRank("主界面风格",2) == 1) { 
    selStr += "     "+ 返回市场 +" \r\n\r\n";
    } else if (cm.getBossRank("主界面风格",2) ==2) { 
    selStr += "     "+ 返回市场2 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==3) { 
    selStr += "     "+ 返回市场3 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==4) { 
    selStr += "     "+ 返回市场4 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==5) { 
    selStr += "     "+ 返回市场5 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==6) { 
    selStr += "     "+ 返回市场6 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==7) { 
    selStr += "     "+ 返回市场7 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==8) { 
    selStr += "     "+ 返回市场8 +" \r\n\r\n";
	} else if (cm.getBossRank("主界面风格",2) ==9) { 
    selStr += "     "+ 返回市场9 +" \r\n\r\n";
	
    } else { 
	selStr += "     #r默认风格#k  \r\n\r\n";}
	
	     selStr += "--------------------------------------------\r\n";
		 
		 
		 if (cm.getBossRank("主界面风格",2) >= 0) {
		 
		 selStr += "#L0##b切换默认风格[简]#l#k    \r\n\r\n";
		 
		 selStr += "#L101##r开启至尊风格#l#k    \r\n\r\n";
		 
         selStr += "#L1#切换风格  "+返回市场+"#l\r\n\r\n";
		 
		 selStr += "#L2#切换风格  "+返回市场2+"#l\r\n\r\n";
		 
		 selStr += "#L3#切换风格  "+返回市场3+"#l\r\n\r\n";
		 
		 selStr += "#L4#切换风格  "+返回市场4+"#l\r\n\r\n";
		  
		 selStr += "#L5#切换风格  "+返回市场5+"#l\r\n\r\n";
		 
		 selStr += "#L6#切换风格  "+返回市场6+"#l\r\n\r\n";
		 
		 selStr += "#L7#切换风格  "+返回市场7+"#l\r\n\r\n";
		 
		 selStr += "#L8#切换风格  "+返回市场8+"#l\r\n\r\n";
		 
		 selStr += "#L9#切换风格  "+返回市场9+"#l\r\n\r\n";
		 

       } else { selStr += "   #L99#解锁风格切换#l  \r\n\r\n";}
		
	
	
	
	

		cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {	 
		case 0:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2) +"");
		   cm.sendOk("成功切换为默认风格。");
		   cm.dispose();   
			break;
		case 1:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","1");
		   cm.sendOk("成功切换为 "+返回市场+"");
		   cm.dispose();   
			break;
		case 2:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","2");
		   cm.sendOk("成功切换为 "+返回市场2+"");
		   cm.dispose();   
			break;
		case 3:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","3");
		   cm.sendOk("成功切换为 "+返回市场3+"");
		   cm.dispose();   
			break;	
		case 4:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","4");
		   cm.sendOk("成功切换为 "+返回市场4+"");
		   cm.dispose();   
			break;	
		case 5:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","5");
		   cm.sendOk("成功切换为 "+返回市场5+"");
		   cm.dispose();   
			break;
		case 6:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","6");
		   cm.sendOk("成功切换为 "+返回市场6+"");
		   cm.dispose();   
			break;		   
		case 7:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","7");
		   cm.sendOk("成功切换为 "+返回市场7+"");
		   cm.dispose();   
			break;
		case 8:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","8");
		   cm.sendOk("成功切换为 "+返回市场8+"");
		   cm.dispose();   
			break;			
		case 9:
           cm.setBossRankCount("主界面风格","-"+cm.getBossRank("主界面风格",2)+"");
		   cm.setBossRankCount("主界面风格","9");
		   cm.sendOk("成功切换为 "+返回市场9+"");
		   cm.dispose();   
			break;			
			
			
		case 99:
       		
		   cm.setBossRankCount("主界面风格","0");
		   cm.dispose();
		   cm.openNpc(9900004,4399);	
		   
		   break;	
			
		case 100:
           cm.setBossRankCount("枫叶风格","-"+cm.getBossRank("枫叶风格",2)+"");
		   cm.sendOk("关闭成功。");
		   cm.dispose();   
			break;
		case 101:
		
		 if (cm.getPlayer().getInventory(cm.getInvType(-1)).findById(1142099)) { 
		   cm.setBossRankCount("枫叶风格","0");
           cm.setBossRankCount("枫叶风格","-"+cm.getBossRank("枫叶风格",2)+"");
		   cm.setBossRankCount("枫叶风格","1");
		   cm.sendOk("开启成功。");
		   cm.dispose();   
		} else {    
		   cm.sendOk("请佩戴好枫叶至尊勋章再开启。");
		   cm.dispose();}
			break;			
			
			
			
			
	
			
			
			 
			 

			 
			 
			 
			 
			 
		}
    }
}