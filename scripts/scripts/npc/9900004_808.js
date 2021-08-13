/*
	内容：个人排行榜
*/

var status = -1;

var tz5 = "#fUI/UIWindow2.img/QuestAlarm/BtQ/normal/0#";
var iconEvent = "#fUI/UIToolTip.img/Item/Equip/Star/Star#";
var sl0items=null;
var bankitems=null;
var sel_0=0;
var sel_1=0;
var sel_2=0;
var sel_3=0;
var savemax=90;//仓库上限
function start() {
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 0) {
		cm.dispose();
		return;
	}var MC = cm.getServerName();
	status++;
	if (status == 0) {
		bankitems=cm.getBankItems();
		var  text = "#e#r"+MC+"#k 随身仓库 - \r\n"; 
		  text += "#d#n最大存储；"+savemax+" #r\r\n"; 
		 text += "#d#n已用存储；" +( bankitems==null?0:bankitems.size())+" \r\n";
		
		 text += "#e#b      ┏                           ┓ #r\r\n";
		
		
		text += "\t\t       #L21#存入道具#l\r\n";
		text += "\t\t       #L22#取出道具#l\r\n";
        text += "\t\t       #L99##b返回界面#l\r\n\r\n";
		text += "\r\n#e#b      ┗                           ┛\r\n";
		
		cm.sendSimple(text);
	} else if (status == 1) {
		sel_0=selection;
		if(selection==99){
             cm.dispose();
             cm.openNpc(9900004,0);	
			return;
			}
			
		
		if(selection==21){
			if(bankitems!=null){
				if(bankitems.size()>=savemax){
					cm.sendOkS("仓库已达上限，不可存储。", 3);
					cm.dispose();
					return;
				}
			}
			var text = "\t\t\t\t#e#d★ 选择背包种类 ★#k\r\n\r\n";
			text+="\t\t\t\t#b#L2#消耗栏#l#k\r\n";
			text+="\t\t\t\t#b#L3#设置栏#l#k\r\n";
			text+="\t\t\t\t#b#L4#其他栏#l#k\r\n";
			cm.sendSimple(text);
		}else if(selection==22){
			if(bankitems==null||bankitems.size()<1){
				cm.sendOkS("仓库里没有道具", 3);
				cm.dispose();
				return;
			}
			var text="#b请选择你要取的道具#k\r\n\r\n";
			for(var i=0;i<bankitems.size();i++){
				text+="#r#L"+i+"##v"+bankitems.get(i).getItemid()+"# 数量 #r"+bankitems.get(i).getCount()+"#l#k\r\n";
			}
			cm.sendSimple(text);
		}
	}else if(status == 2){
		sel_1=selection;
		if(sel_0==21){
			sl0items=cm.getItemsByType(sel_1);
			if(sl0items==null||sl0items.size()<1){
				cm.sendOkS("选择的背包里没有道具", 3);
				cm.dispose();
				return;
			}
			var text="#b请选择你要存的道具#k\r\n\r\n";
			for(var i=0;i<sl0items.size();i++){
				text+="#r#L"+i+"##v"+sl0items.get(i).getItemId()+"# 数量 #r"+sl0items.get(i).getQuantity()+"#l#k\r\n";
			}
			cm.sendSimple(text);
		}else if(sel_0==22){
			var takeselitem=bankitems.get(sel_1);
			if(takeselitem==null||takeselitem.getCount()<1){
				cm.sendOkS("选择的道具不存在", 3);
				cm.dispose();
				return;
			}
			cm.sendGetNumber("请输入要取的数量",takeselitem.getCount(),1,takeselitem.getCount());	
		}
	}else if(status == 3){
		sel_2=selection;
		if(sel_0==21){
			var selItem=sl0items.get(sel_2);
			if(selItem==null||selItem.getQuantity()<1){
				cm.sendOkS("选择的道具不存在", 3);
				cm.dispose();
				return;
			}
			cm.sendGetNumber("请输入要存的数量",selItem.getQuantity(),1,selItem.getQuantity());		
			
		}else if(sel_0==22){
			var takeselitem=bankitems.get(sel_1);
			if(takeselitem==null||takeselitem.getCount()<1){
				cm.sendOkS("选择的道具不存在", 3);
				cm.dispose();
				return;
			}
			if(sel_2<1||sel_2>takeselitem.getCount()){
				cm.sendOkS("要取的道具数量有误", 3);
				cm.dispose();
				return;
			}
			var takeret=cm.takeBankItem(takeselitem,sel_2);
			if(takeret==-1){
				cm.sendOkS("取的对象为空", 3);
			}else if(takeret==-2){
				cm.sendOkS("数量不正确", 3);
			}else if(takeret==-3){
				cm.sendOkS("背包空间不足", 3);
			}else {
				if(takeret>0){
					cm.gainMeso(-5000);
					cm.sendOkS("取道具完毕。\r\n收取手术费用5000金币。", 3);
				}else{
					cm.sendOkS("取失败，错误代码("+takeret+")", 3);
				}
			}
			cm.dispose();
		}
	}else if(status == 4){
		sel_3=selection;
		if(sel_0==21){
			var selItem=sl0items.get(sel_2);
			if(selItem==null||selItem.getQuantity()<1){
				cm.sendOkS("选择的道具不存在", 3);
				cm.dispose();
				return;
			}
			if(sel_3<1||sel_3>selItem.getQuantity()){
				cm.sendOkS("存的道具数量有误", 3);
				cm.dispose();
				return;
			}
			var saveret=cm.saveBankItem(selItem,sel_3);
			if(saveret==-4){
				cm.sendOkS("存的道具不存在", 3);
			}else if(saveret==-5){
				cm.sendOkS("点装或限时道具不能存", 3);
			}else if(saveret==-6){
				cm.sendOkS("存的数量大于存在数量", 3);
			}else if(saveret==-8){
				cm.sendOkS("锁定道具或一栏多个装备", 3);
			}else {
				if(saveret>0){
					cm.gainMeso(-5000);
					cm.sendOkS("存储完毕。\r\n收取手术费用5000金币。", 3);
				}else{
					cm.sendOkS("存储失败，错误代码("+saveret+")", 3);
				}
			}
			cm.dispose();
		}else if(sel_0==22){
			cm.dispose();
		}
		
	}
}
