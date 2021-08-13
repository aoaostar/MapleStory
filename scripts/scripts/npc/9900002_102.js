importPackage(net.sf.odinms.client);

var status = 0;
var fee;
var chance = Math.floor(Math.random()*1);
	
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("不设置密码无法开启银行。");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
		var h1 = cm.getBossRank("银行账户1",2);
	var h2 = cm.getBossRank("银行账户2",2);
	var h3 = cm.getBossRank("银行账户3",2);
	var h4 = cm.getBossRank("银行账户4",2);
	var h5 = cm.getBossRank("银行账户5",2);
	var h6 = cm.getBossRank("银行账户6",2);
	var h7 = cm.getBossRank("银行账户7",2);
	var h8 = cm.getBossRank("银行账户8",2);
	var h9 = cm.getBossRank("银行账户9",2);
       if (status == 0) {
            cm.sendGetText("\r\n - #r输入你要设置的密码,要牢记哦（最多只能六位数）#k - ");
        } else if (status == 1) {
            fee = cm.getText();
            cm.sendYesNo("- 你确定要将密码设置成； #r" + fee + "#k 吗？");
             if (cm.getText() > 999999) {
                cm.sendOk("错误 ；密码不能大于6位数字。");
                cm.dispose();
        } else {
                 if (chance <= 1) { 
				        cm.sendOk("密码设置成功");
				        cm.setBossRankCount("银行密码");
				        cm.setBossRankCount("银行密码","-"+cm.getBossRank("银行密码",2)+"");
				        cm.setBossRankCount("银行密码",fee);
						
	                    cm.dispose(); 
	                } else if (chance >= 2) {
                    cm.sendOk("错误~");
                    cm.dispose();
                }
            }
        }
    }
}
