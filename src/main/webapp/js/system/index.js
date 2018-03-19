$(function() {
	$("#TabPage2 li").click(function () {
        $.each($("#TabPage2 li"), function (index, val) {
            $(val).removeClass("selected");
            $(val).children("img").prop("src", "/images/common/"+(index+1)+".jpg");
        })
		$(this).addClass("selected");
        $(this).children("img").prop("src", "/images/common/"+($(this).index()+1)+"_hover.jpg");
		$("#nav_module img").prop("src", "/images/common/module_"+($(this).index()+1)+".png");
        //菜单发生变化
        loadMenu($(this).data("rootmenu"));
	});
    //菜单发生变化
    //loadMenu($(this).data("rootmenu"));
    // 加载日期
    loadDate();
    // ======================================
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function() {
        switchSysBar();
    });
    // ======================================
    loadMenu('business');
});

//zTree的系统设置
var setting = {
	data : {
		simpleData : {
			enable : true
		}
	},
	callback:{
		onClick: function (even, treeId, treeNode) {
			var action = treeNode.action;
			if(action) {
				$("#rightMain").prop("src", "/" + action + ".do");
				var loc = "当前位置：" + treeNode.getParentNode().name
					+ "&nbsp;&gt;&nbsp;" + treeNode.name;
				$("#here_area").html(loc);
			}
        }
	},
    async: {
        enable : true,
        url : "/systemMenu/loadMenusBySn.do",
        autoParam : ["sn=parentSn"]
    }
};

//zTree的菜单树节点
var nodes = {
	systemManage : [
		{id : 1,pId : 0, name : "系统模块", sn : "system", isParent : true}
	],
	business : [
		{id : 2,pId : 0, name : "业务模块", sn : "business", isParent : true}
	],
	charts : [
		{id : 3,pId : 0, name : "报表模块", sn : "chart", isParent : true}
	]
};
//加载菜单
function loadMenu(menuSn) {
    //初始化并显示一棵树
	$.fn.zTree.init($("#dleft_tab1"), setting, nodes[menuSn]);
}
//加载当前日期
function loadDate() {
	var time = new Date();
	var myYear = time.getFullYear();
	var myMonth = time.getMonth() + 1;
	var myDay = time.getDate();
	if (myMonth < 10) {
		myMonth = "0" + myMonth;
	}
	document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."
			+ myDay;
}

/**
 * 隐藏或者显示侧边栏
 * 
 */
function switchSysBar(flag) {
	var side = $('#side');
	var left_menu_cnt = $('#left_menu_cnt');
	if (flag == true) { // flag==true
		left_menu_cnt.show(500, 'linear');
		side.css({
			width : '280px'
		});
		$('#top_nav').css({
			width : '77%',
			left : '304px'
		});
		$('#main').css({
			left : '280px'
		});
	} else {
		if (left_menu_cnt.is(":visible")) {
			left_menu_cnt.hide(10, 'linear');
			side.css({
				width : '60px'
			});
			$('#top_nav').css({
				width : '100%',
				left : '60px',
				'padding-left' : '28px'
			});
			$('#main').css({
				left : '60px'
			});
			$("#show_hide_btn").find('img').attr('src',
					'/images/common/nav_show.png');
		} else {
			left_menu_cnt.show(500, 'linear');
			side.css({
				width : '280px'
			});
			$('#top_nav').css({
				width : '77%',
				left : '304px',
				'padding-left' : '0px'
			});
			$('#main').css({
				left : '280px'
			});
			$("#show_hide_btn").find('img').attr('src',
					'/images/common/nav_hide.png');
		}
	}
}
