Cms={};
Cms.selectWeiXinType=function selectWeiXinType(){
	var t=$("#sendType").val();
	if(t==3){
		$("#selectImg").show();
		$("#imageHelpSpan").show();
		var img=$("#selectImg").val();
		if(img==0){
			$("#uploadImgPath4").parent().parent().parent().show();
		}
	}else{
		$("#selectImg").hide();
		$("#imageHelpSpan").hide();
		$("#uploadImgPath4").parent().parent().parent().hide();
	}
}
Cms.selectWeiXinImg=function selectWeiXinImg(){
	if($("#selectImg").val()==0){
		var t=$("#sendType").val();
		if(t==3){
			$("#uploadImgPath4").parent().parent().parent().show();
		}else{
			$("#uploadImgPath4").parent().parent().parent().hide();
		}
	}else{
		$("#uploadImgPath4").parent().parent().parent().hide();
	}
}
$(document).ready(function(){
	getLoginInfo();
	getCustomizeMenu();
	countUnreadInboxMsg();
	getDBType();
	getPlugMenu();
	var lastUnreadMsgCount=0;
	function countUnreadInboxMsg() { 
	     	$.ajax({
    		url: "message/v_countUnreadMsg.do",
    		success: function(data){
    			if(data.result){
					 $('#header_inbox_bar').text(data.count);
					 $('#header_inbox_bar_2').text(data.count);
					 $('#header_inbox_bar_msg').text(data.count);
					 $('#header_login_info_bar').text(data.count);
					 if(lastUnreadMsgCount<data.count){
						 showInboxMsgTooltip();
					 }
					 lastUnreadMsgCount = data.count;
				}else if(data.indexOf('baaa67449f5749a534bb7b2cc39c20bc')>0){
                	alert("登录超时，请重新登录");
                	location.href="logout.do";
                } 
    		}
    	}); 
	    
	setTimeout(countUnreadInboxMsg, 1000*60*5); 
	}
	
	function getPlugMenu(){
		$.ajax({
			url: "frame/expand_left.do",
			dataType: "json",
			success: function(data){
				var plug_menu = "";
				$.each(data,function(i,item){ 
					plug_menu = item.plug_menu;
				}); 
				$('#plug_menu').replaceWith(""+plug_menu+"");
				//alert(plug_menu);
			}
		});  
	}
	
	function getDBType(){
		$.ajax({
			url: "frame/maintain_left.do",
			dataType: "json",
			success: function(data){
				var db_backup = $('#db_backup').attr("href");
				var db_revert = $('#db_revert').attr("href");
				var db_dir = $('#db_dir').attr("href");
				var dbType = "";
    			$.each(data,function(i,item){ 
    				 dbType = item.dbType;
    	        }); 
    			$('#db_backup').attr("href",""+dbType+"/"+db_backup+"");
    			$('#db_revert').attr("href",""+dbType+"/"+db_revert+"");
    			$('#db_dir').attr("href",""+dbType+"/"+db_dir+"");
    			$('#dbTypeVal').val(dbType);
			}
		});  
	}
	
	function getCustomizeMenu(){
		$.ajax({
			url: "left.do",
			dataType: "json",
			success: function(data){
				$.each(data,function(i,item){ 
				$('#diyMenuList').append(item.mymenu);
   	        }); 
			}
		});  
	}
	
	function getLoginInfo(){
    	$.ajax({
    		url: "top.do",
    		dataType: "json", 
    		success: function(data){
    			var username = $('.dropdown-user .username');
    			var site_size_tooltip = $('#site_size_tooltip');
    			var site_size = $('#site_size');
    			var site_list = $('#all_site_list');
    			var siteURL = $('#current_siteURL');
    			$.each(data,function(i,item){ 
    	             username.html(item.username);
    	    		 siteURL.attr("href",item.siteURL);
    	    		 site_list.html(item.siteList);
    	    		 site_size_tooltip.html(item.siteSize);
    	    		 site_size.html(item.siteSize);
    	    		 document.title=item.siteTitle; 
    	        }); 
    		}
    	});  
	}
	
	function showInboxMsgTooltip(){
		$.gritter.add({
            title: '新信息提醒',
            text: '<p>你有新的站内信息，请注意查收哦 </p><br/>',
            sticky: false,
            time: 5000,
			speed:500,
			position: 'bottom-right'
        });
	}
}); 