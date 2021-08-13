var status = 0;
var _useId = 2460005;
// var properties = [
//     'Str', 'Dex', 'Int', 'Luk',
//     'Hp', 'Mp', 'Watk', 'Matk',
//     'Wdef', 'Mdef', 'Acc', 'Avoid',
//     'Hands', 'Speed', 'Jump', 'Owner'
// ];
//力量,敏捷,智力,运气
//HP,MP,物攻,魔攻
//物御,魔防,命中,回避
//手技,移速,跳跃力
//Potential1-3,是潜能,079客户端没有这个,剔除
//属于谁(这里用来做等级的标识)
//装备鉴定出来的品级 , 以及不同品级会改变的装备属性列表 , 以及属性叠加值
var appraisal = {
    "D": {
        props: ['Str', 'Dex', 'Int', 'Luk'],
        value: [5, 5, 5, 5]
    },
    "C": {
        props: ['Str', 'Dex', 'Int', 'Luk'],
        value: [10, 10, 10, 10]
    },
    "B": {
        props: ['Str', 'Dex', 'Int', 'Luk'],
        value: [20, 20, 20, 20]
    },
    "A": {
        props: ['Str', 'Dex', 'Int', 'Luk'],
        value: [30, 30, 30, 30]
    },
    "S": {
        props: ['Str', 'Dex', 'Int', 'Luk', 'Watk', 'Matk'],
        value: [50, 50, 50, 50, 50, 50]
    },
    "SS": {
        props: ['Str', 'Dex', 'Int', 'Luk', 'Watk', 'Matk', 'Hands'],
        value: [70, 70, 70, 70, 70, 70, 70]
    },
    "SSS": {
        props: ['Str', 'Dex', 'Int', 'Luk', 'Watk', 'Matk', 'Hands'],
        value: [90, 90, 90, 90, 90, 90, 90]
    }
} //;['D', 'C', 'B', 'A', 'S', 'SS', 'SSS'];
//
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        if (status == 0) {
            var ii = Packages.server.MapleItemInformationProvider.getInstance();
            var item = cm.getInventory(1).getItem(1);
            var itemId = item.getItemId();
            if (item == null) {
                cm.sendOk("当前背包第一格没有装备 ！");
                cm.dispose();
            }
           var text = "    #e 您好 ! 我是#r传说中的装备鉴定之神#k : \r\n";
            text += "是否确认使用一枚#v2460005#鉴定当前道具 ？ \r\n";
			text += "#b潜能等级分为：D-C-B-A-S-SS-SSS七档 等级随机升降 不会降低装备初始属性\r\n";
            text += "当前道具 ： \r\n";
            text += "#v" + itemId + "#\r\n";
            cm.sendYesNo(text);
        } else if (status == 1) {
            var ii = Packages.server.MapleItemInformationProvider.getInstance();
            if (cm.haveItem(_useId, 1)) {
                item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                try {
                    if (ii.isCash(item.getItemId())) {
                        cm.sendOk("对不起,点装不支持鉴定 ！");
                        cm.dispose();
                        return;
                    }
                } catch (e) {
                    cm.dispose();
                    return;
                }
                //获取当前物品的现有等级
                var apLv = item.getOwner();
                var lv = "",
                    newLv = "";
                var props, value;
                var isNormal = false;
                if (apLv == "") {
                    //没有鉴定过的装备 
                    isNormal = true;
                } else {
                    lv = apLv.substring(0, apLv.length() - 1);
                }
                newLv = getAppraisalLevel();
                if (lv != newLv) {
                    if (isNormal) {
                        // 属性追加
                        props = appraisal[newLv].props;
                        value = appraisal[newLv].value;
                        for (var i = 0; i < props.length; i++) {
                            var oldProp = item['get' + props[i]]();
                            var newPorp = oldProp + parseInt(value[i]);
                            item['set' + props[i]](newPorp);
                        }
                    } else {
                        //replace new property
                        //5 , 10
                        //var opts = getNewProperty(appraisal[lv], appraisal[newLv]);
                        //清空旧的属性
                        var oldPorps = appraisal[lv].props;
                        var oldVals = appraisal[lv].value;
                        for (var i = 0; i < oldPorps.length; i++) {
                            var currVal = item['get' + oldPorps[i]]();
                            var oldVal = oldVals[i];
                            item['set' + oldPorps[i]](currVal - parseInt(oldVal));
                        }
                        //设置新的
                        props = appraisal[newLv].props;
                        value = appraisal[newLv].value;
                        for (var i = 0; i < props.length; i++) {
                            var oldProp = item['get' + props[i]]();
                            var newPorp = oldProp + parseInt(value[i]);
                            item['set' + props[i]](newPorp);
                        }
                    }
                }
                item.setOwner(newLv + "级");
                Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, true);
                Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
                cm.gainItem(_useId, -1);
                cm.sendOk("恭喜,鉴定成功 , 快看看你的包袱吧 ！");
                cm.worldMessage("[装备鉴定]]：恭喜[" + cm.getChar().getName() + "]成功使用了装备鉴定功能,获得[" + newLv + "]级道具 ！");
                cm.dispose();
            } else {
                cm.sendOk("对不起,你没有足够的#v" + _useId + "# ！");
                cm.dispose();
                return;
            }
        } else {
            cm.dispose();
        }
    }
}

function getAppraisalLevel() {
    //
    //9 8 7 6 3 2 1
    var num = Math.floor(Math.random() * 12000);
    var flag = "D";
    if (num > 1000 && num <= 1800) {
        //C
        flag = "C";
    } else if (num > 1800 && num <= 2500) {
        //B
        flag = "B";
    } else if (num > 2500 && num <= 3000) {
        //A
        flag = "A";
    } else if (num > 3000 && num <= 3150) {
        //S
        flag = "S";
    } else if (num > 3150 && num <= 3250) {
        //SS
        flag = "SS";
    } else if (num > 3250 && num <= 3300) {
        //SSS
        flag = "SSS";
    } else {
        flag = "D";
    }
    return flag;
}