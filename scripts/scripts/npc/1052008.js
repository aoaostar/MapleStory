/**
 -- Odin JavaScript --------------------------------------------------------------------------------
 Treasure Chest - Line 3 Construction Site: B1 <Subway Depot> (103000902)
 -- By ---------------------------------------------------------------------------------------------
 Unknown
 -- Version Info -----------------------------------------------------------------------------------
 1.1 - Statement fix [Information]
 1.0 - First Version by Unknown
 ---------------------------------------------------------------------------------------------------
 **/

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 2 && mode == 0) {
        cm.sendOk("Alright, see you next time.");
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        if (cm.getQuestStatus(2055) == 1) {//是否接了休咪的金币任务
            cm.gainItem(4031039, 1); //4031039 - 休咪的金币 - 休咪弄丢的金币.
            cm.warp(103000100, 0);//103000100 - 废弃都市 地铁 - 地铁售票处
        } else {
            var rand = 1 + Math.floor(Math.random() * 6);
            if (rand == 1) {
                cm.gainItem(4010003, 2); //4010003 - 朱矿石母矿 - 重而坚硬的朱矿石的母矿.
            } else if (rand == 2) {
                cm.gainItem(4010000, 2); //4010000 - 青铜母矿 - 轻且脆的青铜的母矿.
            } else if (rand == 3) {
                cm.gainItem(4010002, 2); //4010002 - 锂矿石母矿 - 轻而坚硬的锂矿石的母矿.
            } else if (rand == 4) {
                cm.gainItem(4010005, 2); //4010005 - 紫矿石母矿 - 非常稀贵的紫矿石的母矿.
            } else if (rand == 5) {
                cm.gainItem(4010004, 2); //4010004 - 银的母矿 - 有光彩的银的母矿.
            } else if (rand == 6) {
                cm.gainItem(4010001, 2); //4010001 - 钢铁母矿 - 坚硬的钢铁的母矿.
            }
            cm.warp(103000100, 0);//103000100 - 废弃都市 地铁 - 地铁售票处
        }
        cm.dispose();
    }
}

