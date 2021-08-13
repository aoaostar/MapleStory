/*
	装备锻造 NPC脚本  by 土狗 2017/6/30 星期五 上午 12:03:48
	仅供学习参考，禁止用于任何商业行为
*/


var status;
var text;
var itemid,item;
var selstatus = -1;
var itemList = new Array();
var deleteSlot;
var itemstr,itemdex,itemluk,itemint,itemhp,itemmp,itemwatk,itemmatk,itemwdef,itemmdef,itemavoid,itemacc,itemjump,itemspeed;
// str,dex,luk,int,hp,mp,watk,matk,wdef,mdef,avoid,acc,jump,speed,time
var str_random=Math.floor(Math.random()*6);
var dex_random=Math.floor(Math.random()*6);
var luk_random=Math.floor(Math.random()*6);
var int_random=Math.floor(Math.random()*6);
var hp_random=Math.floor(Math.random()*6);
var mp_random=Math.floor(Math.random()*6);
var watk_random=Math.floor(Math.random()*6);
var matk_random=Math.floor(Math.random()*6);
var wdef_random=Math.floor(Math.random()*6);
var mdef_random=Math.floor(Math.random()*6);
var avoid_random=Math.floor(Math.random()*6);
var acc_random=Math.floor(Math.random()*6);
var jump_random=Math.floor(Math.random()*6);
var speed_random=Math.floor(Math.random()*6);

var chance_random=Math.floor(Math.random()*100);
var chance_value=100;
//var chance_level1=60,chance_level2=50,chance_level3=30,chance_level4=20,chance_level5=10;
//var item_level1=50,item_level2=150,item_level3=350,item_level4=600,item_level5=1000;
var price=1,price1=1,price2=1,price3=1,price4=1,price5=1;

 
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode <= 0) {
        cm.dispose();
        return;
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
        	
            text = "#r【中国心强化】属性提升说明：#d每次提升会随机增加属性0-6点， 提升需要#v4000464#中国心一个！\r\n#b【规则】每日十次机会，100%成功率！#k\r\n";
            text += "#L0##e#r 升级包裹内指定装备#l\r\n";
            text += "\r\n\r\n\r\n\r\n";
            cm.sendSimple(text);
        } else {
            if (selstatus == -1) {
                selstatus = selection;
            }
            switch (selstatus) {
                case 0:
				if (cm.getBossLog('PlayQuest99') >= 10) {
			cm.sendOk("你今天强化次数超过3次!");
			cm.dispose();
			} else {
                    deleteItemBySlot(selection);
                    break;
               // case 1:
                    cm.openNpc(cm.getNpc(), 501);
            }
        }
    }
}

function deleteItemBySlot(selection) {
    if (status == 1) {
        itemList = cm.getInventory(1).list().iterator();
        text = "#e- 请选择要升级的道具 -#n\r\n\r\n#b";
        var indexof = 1;
        while (itemList.hasNext()) {
            var item = itemList.next();
            text += "#L" + item.getPosition() + "##v" + item.getItemId() + "#";
            if (indexof > 1 && indexof % 5 == 0) {
                text += "\r\n";
            }
            indexof++;
        }
        cm.sendSimple(text);
    } else if (status == 2) {
		var ii = Packages.server.MapleItemInformationProvider.getInstance();
        item = cm.getInventory(1).getItem(selection);
        itemid=item.getItemId();
        
        itemstr=item.getStr();
        itemdex=item.getDex();
        itemluk=item.getLuk();
        itemint=item.getInt();
        itemhp=item.getHp();
        itemmp=item.getMp();
        itemwatk=item.getWatk();
        itemmatk=item.getMatk();
        itemwdef=item.getWdef();
        itemmdef=item.getMdef();
        itemavoid=item.getAvoid();
        itemacc=item.getAcc();
        itemjump=item.getJump();
        itemspeed=item.getSpeed();
        
        deleteSlot = selection;
			if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("只支持普通装备强化.");
                    cm.dispose();
  
                }
    	/*	if((itemstr>item_level5)||(itemdex>item_level5)||(itemluk>item_level5)||(itemint>item_level5))
    		{
    			chance_value=chance_level5;
    			price=price5;
    		}
    		else if(((itemstr>item_level4)&&(itemstr<=item_level5))||((itemdex>item_level4)&&(itemdex<=item_level5))||((itemluk>item_level4)&&(itemluk<=item_level5))||((itemint>item_level4)&&(itemint<=item_level5)))
    		{
    			chance_value=chance_level4;
    			price=price4;
    		}
    		else if(((itemstr>item_level3)&&(itemstr<=item_level4))||((itemdex>item_level3)&&(itemdex<=item_level4))||((itemluk>item_level3)&&(itemluk<=item_level4))||((itemint>item_level3)&&(itemint<=item_level4)))
    		{
    			chance_value=chance_level3;
    			price=price3;
    		}
    		else if(((itemstr>item_level2)&&(itemstr<=item_level3))||((itemdex>item_level2)&&(itemdex<=item_level3))||((itemluk>item_level2)&&(itemluk<=item_level3))||((itemint>item_level2)&&(itemint<=item_level3)))
    		{
    			chance_value=chance_level2;
    			price=price2;
    		}
    		else if(((itemstr>item_level1)&&(itemstr<=item_level2))||((itemdex>item_level1)&&(itemdex<=item_level2))||((itemluk>item_level1)&&(itemluk<=item_level2))||((itemint>item_level1)&&(itemint<=item_level2)))
    		{
    			chance_value=chance_level1;
    			price=price1;i
    		}*/
        text = "#e确定要升级#v" + item.getItemId() + "# "+ "？成功概率为：#r"+chance_value+"%\r\n\r\n";
		text += "#b装备目前属性：\r\n" ;
        text += "力量：" + itemstr + "\r\n" ;
        text += "敏捷：" + itemdex + "\r\n" ;
        text += "运气：" + itemluk + "\r\n" ;
        text += "智力：" + itemint + "\r\n" ;
		text += "HP：" + itemhp + "\r\n" ;
		text += "MP：" + itemmp + "\r\n" ;
        text += "攻击：" + itemwatk + "\r\n" ;
        text += "魔攻：" + itemmatk + "\r\n" ;
		text += "防御力：" +  itemwdef + "\r\n" ;
		text += "魔法防御力：" +  itemmdef + "\r\n" ;
		text += "命中率：" + itemacc + "\r\n" ;
		text += "回避率：" + itemavoid + "\r\n" ;
		
        cm.sendNextPrev(text);
    } else if (status == 3) {
    		if(itemspeed>=295)
    		{
    			cm.sendOk("该装备无法升级");
        	status = 0;
					cm.dispose();
				}
    		else{
    			if(cm.getPlayer().getItemQuantity(4000464,false) < price)
    			//if(cm.getPlayer().getItemQuantity(04001126, false) < price)
    			{
								cm.sendOk("材料不足，升级需要"+price+"个#r#v4000464#，你当前有"+cm.getPlayer().getItemQuantity(4000464, false));
				        status = 0;
								cm.dispose();
    			}
    			else
    				{
    					if(chance_random<=chance_value)
		    			{
				        cm.removeSlot(1, deleteSlot, 1);//第一个参数1是装备栏，第二关参数是背包中的位置，第三个参数是数量
				        cm.gainItem(4000464,-price);
				    		if(itemspeed>40)
				    			speed_random=0;						
				    		if(itemjump>23)
				    			jump_random=0;																		// str,dex,luk,int,hp,mp,watk,matk,wdef,mdef,avoid,acc,jump,speed,time
								cm.gainItem(itemid,itemstr+str_random,itemdex+dex_random,itemluk+luk_random,itemint+int_random,itemhp+hp_random,itemmp+mp_random,itemwatk+watk_random,itemmatk+matk_random,itemwdef+wdef_random,itemmdef+mdef_random,itemavoid+avoid_random,itemacc+acc_random,0,0);//item.getItemId(),  ,speed=295
				        
				        text = "#e成功为装备增加属性：#b\r\n";
				        text += "力量：" + itemstr + "+" + str_random + "\r\n" ;
				        text += "敏捷：" + itemdex + "+" + dex_random + "\r\n" ;
				        text += "运气：" + itemluk + "+" + luk_random + "\r\n" ;
				        text += "智力：" + itemint + "+" + int_random + "\r\n" ;
						text += "HP：" + itemhp + "+" + hp_random + "\r\n" ;
						text += "MP：" + itemmp + "+" + mp_random + "\r\n" ;
				        text += "攻击：" + itemwatk+ "+" + watk_random + "\r\n" ;
				        text += "魔攻：" + itemmatk+ "+" + matk_random + "\r\n" ;
						text += "命中率：" + itemacc + "+" + acc_random + "\r\n" ;
						text += "回避率：" + itemavoid + "+" + avoid_random + "\r\n" ;
				        cm.sendOk(text);
						cm.setBossLog('PlayQuest99');
								cm.喇叭(3, "『中国心强化』 ：恭喜【"+cm.getName()+"】使用中国心成功强化了装备，继续加油将它打造到极致吧！");
								//cm.全服漂浮喇叭("恭喜玩家["+cm.getName()+"]时装强化六阶成功！可喜可贺！", 5121001);
								//1白色，2红色，3粉色喇叭
				        status = 0;
								cm.dispose();
							}
							else
							{
								cm.sendOk("升级失败，请再接再厉！");
								cm.setBossLog('PlayQuest99');
				        cm.gainItem(4000464,-price);
				        status = 0;
								cm.dispose();
							}
					}
			}
    }
}
}
