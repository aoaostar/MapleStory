/*
	This file is part of the cherry Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
					   Matthias Butz <matze@cherry.de>
					   Jan Christian Meyer <vimes@cherry.de>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
-- Odin JavaScript --------------------------------------------------------------------------------
	 Kerning Ticket Gate - Subway Ticketing Booth(103000100)
-- By ---------------------------------------------------------------------------------------------
	Information
-- Version Info -----------------------------------------------------------------------------------
	1.1 - Added function to NLC [Information]
	1.0 - First Version by Information
---------------------------------------------------------------------------------------------------
**/

var itemid = new Array(4031036,4031037,4031038,4031711);
var mapid = new Array(103000900,103000903,103000906,600010004);
var mapname = new Array("地铁B1", "地铁B2", "地铁B3","市新叶（正常）");
var menu;
var sw;

function start() {
	status = -1;
	sw = cm.getEventManager("Subway");
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1 || (mode == 0 && status ==1)) {
		cm.dispose();
	} else {
		if (mode == 0) {
			cm.sendNext("你必须有一些企业对照顾这里，好吗？");
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		if (status == 0) {
			if (cm.haveItem(itemid[0]) || cm.haveItem(itemid[1]) || cm.haveItem(itemid[2]) || cm.haveItem(itemid[3])) {
				status = 1;
			} else {
				cm.sendOk("这里的票机。不允许你在无票。");
				cm.dispose();
			}
		} if (status == 1) {
			menu = "这里的票机。你将在马上。这票要用？\r\n";
			for(i=0; i < itemid.length; i++) {
				if(cm.haveItem(itemid[i])) {
					menu += "#L"+i+"##b"+mapname[i]+"#k#l\r\n";
				}
			}
			cm.sendSimple(menu);
		} if (status == 2) {
			section = selection;
			if(section < 3) {
				cm.gainItem(itemid[selection],-1);
				cm.warp(mapid[selection]);
				cm.dispose();
			}
			else {
				if(sw == null) {
					cm.sendNext("事件的错误，请重新启动你的服务器解决方案");
					cm.dispose();
				} else if(sw.getProperty("entry").equals("true")) {
					cm.sendYesNo("看来这个骑充足的房间。请你把车票准备好所以我可以告诉你，旅行将是漫长的，但你会得到你的目的地就好了。你是怎么想的？你想在这坐?");
				} else if(sw.getProperty("entry").equals("false") && sw.getProperty("docked").equals("true")) {
					cm.sendNext("地铁是准备起飞。我很抱歉，但你得乘坐下一趟。的不论您是亚瑟坐在售票亭.");
					cm.dispose();
				} else {
					cm.sendNext("我们将在起飞1分钟后开始登机。请耐心等待几分钟。要知道，地铁将在正确的时间，我们停止接收票之前1分钟，所以请务必准时到这里。");
					cm.dispose();
				}
			}
		} if (status == 3) {
			cm.gainItem(itemid[section],-1);
			cm.warp(mapid[section]);
			cm.dispose();
		}
	}
}
