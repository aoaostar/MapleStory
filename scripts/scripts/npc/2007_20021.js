importPackage(Packages.database);
importPackage(Packages.client.inventory);
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR); //获得年份
var month = ca.get(java.util.Calendar.MONTH) + 1; //获得月份
var day = ca.get(java.util.Calendar.DATE);//获取日
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);

var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var item = 4000016;
var sidainum = 0;
var num = 0;
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
	if(cm.getBossLog("tijiao21") >= 5){
    		cm.sendOk("每天只能提交5次哦！");
    		cm.dispose();
    		return;}
    if (cm.getMapId() == 180000001) {
        cm.sendOk("很遗憾，您因为违反用户守则被禁止游戏活动，如有异议请联系管理员.");
        cm.dispose();
    } 
    else if (status == 0) {
		
        var id = cm.getPlayer().getId();
        sidainum = getsidai(id);
        if(sidainum == -1){
            num = 0;
        }else{
            num = sidainum;
        }
        var selStr = "您当前提交蜗牛壳的数量 ："+ num +"\r\n";
        selStr += "#r您是否继续添加？？";
        cm.sendYesNo(selStr);
    }else if(status == 1){
		if(!cm.haveItem(item)){
			cm.sendOk("你身上没有蜗牛壳哦！");
			cm.dispose();
			return;
		}
        var iter = cm.getChar().getInventory(MapleInventoryType.ETC).listById(item).iterator();
        cm.sendGetNumber("请输入数量 \r\n", 1, 1,iter.next().getQuantity());
    }else if(status == 2){
        if (selection <= 0) {
            cm.sendOk("输入的上缴数字错误。");
            cm.dispose();
            return;
        }
        if(cm.haveItem(item, selection)){
			var sidai = selection;
            cm.gainItem(item, -selection);
            if(sidainum == -1){
                setsidai(sidai);
            }else{
                num = sidai + sidainum;
                changesidai(num);
            }
			cm.worldMessage(6,""+cm.getName()+"提交的红色蜗牛壳已经达到了"+num+"个。");
            cm.sendOk("提交成功。");
			cm.setBossLog("tijiao21");
            cm.dispose();
        }
    }
}


function getsidai(id){
    var con = DatabaseConnection.getConnection();
    ps = con.prepareStatement("SELECT sidai FROM sidai_slots1 WHERE characterid = ?");
    ps.setInt(1, id);
    var rs = ps.executeQuery();
    if (rs.next()) {
        count = rs.getInt(1);
    } else {
        count = -1;
    }
    rs.close();
    ps.close();
    return count;
}

function changesidai(sidai){
    var id = cm.getPlayer().getId();
	var con = DatabaseConnection.getConnection();
    ps = con.prepareStatement("update sidai_slots1 set sidai = ? where characterid = ?");
    ps.setInt(1, sidai);
    ps.setInt(2, id);
    ps.executeUpdate();
    ps.close();

}

function setsidai(sidai) {
	var id = cm.getPlayer().getId();
    var con1 = DatabaseConnection.getConnection();
    var ps = con1.prepareStatement("insert into sidai_slots1 (characterid, sidai) values (?,?)");
    ps.setInt(1, id);
    ps.setInt(2, sidai);
    ps.executeUpdate();
    ps.close();
}