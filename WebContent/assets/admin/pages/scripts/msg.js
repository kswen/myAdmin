function showErrorMsg(){
	Metronic.alert({
        place: "append", 
        type: "danger",  
        message: '请选择要操作的信息',  
        close: true, 
        reset: true, 
        focus: true, 
        closeInSeconds: 5, 
        icon: "warning"
    }); 
 }
//批量删除到垃圾箱
function toTrash(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要删除这些信息吗？")) {
				return;
			}
			var url ="message/v_trash.do"; 
			var postData = $("#tableForm").serialize();
			 $.ajax({
					    type: "POST",
						url: url,
						data: postData,
						success: function(data)
						{
							for(var i=0;i<ids.length;i++){
								$("#id_"+ids[i]).parent().parent().parent().parent().parent().remove();
							}
							Metronic.alert({
					            place: "append", 
					            type: "info",  
					            message: '您选择的站内信息已被移动到垃圾箱',  
					            close: true, 
					            reset: true, 
					            focus: true, 
					            closeInSeconds: 5, 
					            icon: "check"
					        });
						}
					});
		 }else{
			 showErrorMsg();
		 }
}
//单条信息到垃圾箱
function trash(id){
	 if(!confirm("您确定要删除该条信息吗？")) {
			return;
		}
	 $.post("message/v_trash.do?ids="+id, {
			"ids" : id
		}, function(data) {
			if(data.result){
				$("#msg_inbox").click(); 
			}else{
				alert("请先登录");
			}
		}, "json");
}
function forward(){
	$.ajax({
		type: "POST",
		url: "message/v_forward.do",
		data: $("#tableForm").serialize(),
		success: function(data)
		{
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(data);
		}
	});
}
function reply(){
	$.ajax({
		type: "POST",
		url: "message/v_reply.do",
		data: $("#tableForm").serialize(),
		success: function(data)
		{
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(data);
		}
	});
}
function empty(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要彻底删除这些信息吗？")) {
				return;
			}
	 
		 	var url ="message/v_empty.do"; 
			var postData = $("#tableForm").serialize();
			 $.ajax({
					    type: "POST",
						url: url,
						data: postData,
						success: function(data)
						{
							for(var i=0;i<ids.length;i++){
								$("#id_"+ids[i]).parent().parent().parent().parent().parent().remove();
							}
							Metronic.alert({
					            place: "append", 
					            type: "info",  
					            message: '您选择的站内信息已被彻底删除',  
					            close: true, 
					            reset: true, 
					            focus: true, 
					            closeInSeconds: 5, 
					            icon: "check"
					        });
						}
					});
		 }else{
			 showErrorMsg();
		 }
}
function emptySingle(id){
	 if(!confirm("您确定要彻底删除该信息吗？")) {
			return;
		}
	 $.ajax({
			type: "POST",
			url: "message/v_empty.do?ids="+id,
			data: $("#tableForm").serialize(),
			success: function(data)
			{
				$("#msg_trash_inbox").click(); 
			}
		});
}
function revert(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).attr("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要还原这些信息吗？")) {
				return;
			}
		var url ="message/v_revert.do"; 
		var postData = $("#tableForm").serialize();
		 $.ajax({
				    type: "POST",
					url: url,
					data: postData,
					success: function(data)
					{
						for(var i=0;i<ids.length;i++){
							$("#id_"+ids[i]).parent().parent().parent().parent().parent().remove();
						}
						Metronic.alert({
				            place: "append", 
				            type: "info",  
				            message: '您选择的站内信息已还原',  
				            close: true, 
				            reset: true, 
				            focus: true, 
				            closeInSeconds: 5, 
				            icon: "check"
				        });
					}
				});
	 }else{
		 showErrorMsg();
	 }
}
function find_user(){
	var username=$("#username").val();
	if(username!=""){
		$.post("message/v_findUser.do", {
			"username" : username
		}, function(data) {
			if(data.result){
				if(data.exist){
					Metronic.alert({
			            place: "append", 
			            type: "danger",  
			            message: '没有此用户: '+'<strong>'+$("#username").val()+'</strong>',  
			            close: true, 
			            reset: true, 
			            focus: true, 
			            closeInSeconds: 5, 
			            icon: "warning"
			        });
					$("#username").val("");
					return;
				}
			}else{
					alert("请先登录");
			}
		}, "json");
	}
}
function submitFormMsg(form){
	Metronic.startPageLoading();
	var url = $(form).attr("action"); 
	var postData = $(form).serialize();
	$.ajax({
		type: "POST",
		url: url,
		data: postData,
		success: function(data)
		{
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(data);
			Metronic.stopPageLoading();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(XMLHttpRequest.responseText);
			Metronic.stopPageLoading();
        }
	});
	
}
function submitForm(url, form){
	Metronic.startPageLoading();
    var postData = $(form).serialize();
    $.ajax({
           type: "POST",
           url: url,
           data: postData,
           success: function(data)
           {
               var pageContentBody = $('.page-content .page-content-body');
               pageContentBody.html(data);
               reloaddiyMenu(url);
               Metronic.stopPageLoading();
           },
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(XMLHttpRequest.responseText);
			Metronic.stopPageLoading();
        }
         });
}
function deleteSingle(url){
    if(confirm('确定删除？')){
        sendR(url);
        return false; 
    }else{
        return false; 
    }
}
function sendGet(url){
	Metronic.startPageLoading();
	$.ajax({
		type: "GET",
		url: url,
		success: function(data)
		{
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(data);
			reloaddiyMenu(url);
			Metronic.stopPageLoading();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(XMLHttpRequest.responseText);
			Metronic.stopPageLoading();
		}
	});
}
function sendR(url){
	Metronic.startPageLoading();
    $.ajax({
        type: "POST",
        url: url,
        success: function(data)
        {
            var pageContentBody = $('.page-content .page-content-body');
            pageContentBody.html(data);
            reloaddiyMenu(url);
            Metronic.stopPageLoading();
        },
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			var pageContentBody = $('.page-content .page-content-body');
			pageContentBody.html(XMLHttpRequest.responseText);
			Metronic.stopPageLoading();
        }
      });
}
function reloaddiyMenu(url){
	 if(url.indexOf('menu/')>=0){
     	$(".menu_refresh").remove();
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
}