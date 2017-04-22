var FormValidation = function () {

	//handle form common post
	var postAjaxtRequest = function(form){
		var url = $(form).attr("action"); 
		var postData = $(form).serialize();
		Metronic.startPageLoading();
		$.ajax({
			type: "POST",
			url: url,
			data: postData,
			success: function(data)
			{
				Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {  
                Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(XMLHttpRequest.responseText);
            }
		});
	}
	
	//handle form need refresh channel tree
	var postAjaxtRequestAndRefreshTree = function(form){
		var url = $(form).attr("action"); 
		var postData = $(form).serialize();
		Metronic.startPageLoading();
		$.ajax({
			type: "POST",
			url: url,
			data: postData,
			success: function(data)
			{
				Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(data);
				UITree.ajaxLeftChannelTree();
				UITree.ajaxLeftChannelTree4Content();
				$("#tree_channel_list").jstree("refresh");
				$("#left_channel_tree_4_content_list").jstree("refresh");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
                Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(XMLHttpRequest.responseText);
            }
		});
	}
	
	//handle form need refresh template tree
	var postAjaxtRequestAndRefreshTplTree = function(form){
		var url = $(form).attr("action"); 
		var postData = $(form).serialize();
		Metronic.startPageLoading();
		$.ajax({
			type: "POST",
			url: url,
			data: postData,
			success: function(data)
			{
				Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(data);
				UITree.ajaxLeftTemplateTree();
				$("#left_template_tree").jstree("refresh");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
                Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(XMLHttpRequest.responseText);
            }
		});
	}
	
	//handle form need refresh resource tree
	var postAjaxtRequestAndRefreshResTree = function(form){
        var url = $(form).attr("action"); 
        var postData = $(form).serialize();
        Metronic.startPageLoading();
        $.ajax({
               type: "POST",
               url: url,
               data: postData,
               success: function(data)
               {
            	   Metronic.stopPageLoading();
            	   var pageContentBody = $('.page-content .page-content-body');
            	   pageContentBody.html(data);
                   UITree.ajaxLeftResourceTree();
               	   $("#left_resource_tree").jstree("refresh");
               },
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
                Metronic.stopPageLoading();
				var pageContentBody = $('.page-content .page-content-body');
				pageContentBody.html(XMLHttpRequest.responseText);
            }
             });
	}
	
	var handleValidationContent = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'ihelp-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				title: {
					required: true
				}
			},
			messages: {
				title: "请输入标题"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "warning"
				});
			},
			highlight: function (element) { 
				$(element).closest('.pn-fcontent input').addClass('input_has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.pn-fcontent input').removeClass('input_has-error'); 
			},
			success: function (label) {
				label.closest('.pn-fcontent input').removeClass('input_has-error'); 
			},
			submitHandler: function (form) {
				for (instance in CKEDITOR.instances) {
					CKEDITOR.instances[instance].updateElement();
				}
				postAjaxtRequestAndRefreshTree(form);
				return false; 
			}
		});
	}
	
	var handleValidationChannel = function() {
		var form1 = $('#jvForm');
		var r_url = "channel/v_check_path.do";
		if($("#cid_check").val()!=""){
			r_url = r_url+"?cid="+$("#cid_check").val()+"";
		}
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'ihelp-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					minlength: 1,
					required: true
				},
				path: {
					minlength: 1,
					required: true,
					remote: r_url
				}
			},
			messages: {
				name: {
					required: "请输入栏目名称"
				},
				path: {
					required: "请输入栏目路径",
					remote: $.validator.format("此路径已被使用, 请使用别的路径")
				}
			},
            invalidHandler: function (event, validator) { //display error alert on form submit              
            	Metronic.alert({
                    place: "append", 
                    type: "danger",  
                    message: '下面的信息填写有误，请仔细检查',  
                    close: true, 
                    reset: true, 
                    focus: true, 
                    closeInSeconds: 5, 
                    icon: "warning"
                });
            },
			highlight: function (element) { 
				$(element).closest('.pn-fcontent input').addClass('input_has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.pn-fcontent input').removeClass('input_has-error'); 
			},
			success: function (label) {
				label.closest('.pn-fcontent input').removeClass('input_has-error'); 
			},
			submitHandler: function (form) {
				 for (instance in CKEDITOR.instances) {
		                CKEDITOR.instances[instance].updateElement();
		            }
				postAjaxtRequestAndRefreshTree(form);
				return false; 
			}
		});
	}
	
	var handleContentQuery = function() {
		var form1 = $('#content_list_query_form');
		form1.validate({
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleResourceRename = function() {
		var form1 = $('#jvForm');
		form1.validate({
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequestAndRefreshResTree(form);
				return false;
			}
		});
	}
	
	var handleTemplateRename = function() {
		var form1 = $('#jvForm');
		form1.validate({
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequestAndRefreshTplTree(form);
				return false;
			}
		});
	}
	
	var handleValidationCommon = function() {
		var form1 = $('#jvForm');
		form1.validate({
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false;
			}
		});
	}
	
	var handleValidationTask = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					required: true
				},
				dayOfMonth: {
					range:[1,31]
				},
				dayOfWeek: {
					range:[1,7]
				},
				hour: {
					range:[0,23]
				},
				minute: {
					range:[0,59]
				},
				intervalHour: {
					digits:true,
					range:[0,23]
				},
				intervalMinute: {
					digits:true,
					range:[0,59]
				}
			},
			messages: {
				name: {required: "请输任务名称"},
				dayOfMonth:  {range : "请输入一个介于 {0} 和 {1} 之间的值"},
				dayOfWeek:  {range :"请输入一个介于 {0} 和 {1} 之间的值"},
				hour:  {range :"请输入一个介于 {0} 和 {1} 之间的值"},
				minute:  {range : "请输入一个介于 {0} 和 {1} 之间的值"},
				intervalHour:  {range : "请输入一个介于 {0} 和 {1} 之间的值",  digits : "只能输入整数",},
				intervalMinute:  {range : "请输入一个介于 {0} 和 {1} 之间的值",  digits : "只能输入整数",}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	var handleValidationGroup = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					required: true
				},
				priority: {
					required: true
				}
			},
			messages: {
				name: {
					required: "请输入组名"
				},
				priority: "两次密码输入不匹配"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationTag = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					required: true
				}
			},
			messages: {
				name: {
					required: "请输入tag名称"
				}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationMember = function() {
		var form1 = $('#form_member');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				username: {
					required: true,
					remote: "member/v_check_username.do"
				},
				email: {
					email: true
				}
			},
			messages: {
				username: {
					required: "请输入用户名",
					remote: $.validator.format("用户名已被使用")
				},
				repeatePwd: "两次密码输入不匹配",
				email:"请输入有效的电子邮件地址"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationAdminLocal = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				username: {
					required: true,
					remote: "admin_local/v_check_username.do"
				},
				email: {
					email: true
				}
			},
			messages: {
				username: {
					required: "请输入用户名",
					remote: $.validator.format("用户名已被使用")
				},
				repeatePwd: "两次密码输入不匹配",
				email:"请输入有效的电子邮件地址"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationAdminGlobal = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				username: {
					required: true,
					remote: "admin_global/v_check_username.do"
				},
				email: {
					email: true
				}
			},
			messages: {
				username: {
					required: "请输入用户名",
					remote: $.validator.format("用户名已被使用")
				},
				repeatePwd: "两次密码输入不匹配",
				email:"请输入有效的电子邮件地址"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationPersonalInfo = function() {
		var form1 = $('#form_personal_info_edit');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				origPwd: {
					minlength: 1,
					required: true,
					remote: "personal/v_checkPwd.do"
				},
				email: {
					email: true
				}
			},
			messages: {
				origPwd: {
					required: "请输入原密码",
					remote: $.validator.format("原密码不正确")
				},
				repeatePwd: "两次密码输入不匹配",
				email:"请输入有效的电子邮件地址"
			},
            invalidHandler: function (event, validator) { //display error alert on form submit              
            	Metronic.alert({
                    place: "append", 
                    type: "danger",  
                    message: '下面的信息填写有误，请仔细检查',  
                    close: true, 
                    reset: true, 
                    focus: true, 
                    closeInSeconds: 5, 
                    icon: "warning"
                });
            },
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationRole = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					required: true
				}
			},
			messages: {
				name: "请输入角色名称"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationModel = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				id: {
					required: true,
					digits: true
				},
				path: {
					required: true
				},
				tplChannelPrefix: {
					required: true
				},
				name: {
					required: true
				}
			},
			messages: {
				id: {
					required: "请输入模型ID",
					digits: "模型ID必须为整数"
				},
				path: "请输入模型路径",
				name: "请输入模型名称",
				tplChannelPrefix:"请输入栏目模板前缀"
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	 var handleValidationModelItem = function() {
		  var form1 = $('#jvForm');
		  form1.validate({
		   errorElement: 'span', //default input error message container
		   errorClass: 'help-block', // default input error message class
		   focusInvalid: false, // do not focus the last invalid input
		   ignore: "",
		   rules: {
		    field: {
		     required: true
		    },
		    dataType: {
		    	required: true
		    },
		    priority: {
		    	required: true,
		    	digits: true
		    },
		    helpPosition: {
		    	digits: true
		    },
		    size: {
		    	digits: true
		    },
		    label: {
		     required: true
		    }
		   },
		   messages: {
		    field:  "请输入字段",
		    label:  "请输入字段名称",
		    dataType:  "请选择字段类型",
		    priority:  {required:"请输入排序",digits:"只能是数字"},
		    helpPosition:  "只能是数字",
		    size:  "只能是数字"
		   },
		   invalidHandler: function (event, validator) { //display error alert on form submit
		    Metronic.alert({
		     place: "append",
		     type: "danger",
		     message: '下面的信息填写有误，请仔细检查',
		     close: true,
		     reset: true,
		     focus: true,
		     closeInSeconds: 5,
		     icon: "warning"
		    });
		   },
		   highlight: function (element) {
		    $(element).closest('.form-group').addClass('has-error');
		   },
		   unhighlight: function (element) {
		    $(element).closest('.form-group').removeClass('has-error');
		   },
		   success: function (label) {
		    label.closest('.form-group').removeClass('has-error');
		   },
		   submitHandler: function (form) {
		    postAjaxtRequest(form);
		    return false;
		   }
		  });
		 }
	 
	 var handleValidationREGEdit = function() {
		 var form1 = $('#jvForm');
		 form1.validate({
			 errorElement: 'span', //default input error message container
			 errorClass: 'help-block', // default input error message class
			 focusInvalid: false, // do not focus the last invalid input
			 ignore: "",
			 rules: {
				 field: {
					 required: true
				 },
				 dataType: {
					 required: true
				 },
				 priority: {
					 required: true,
					 digits: true
				 },
				 helpPosition: {
					 digits: true
				 },
				 size: {
					 digits: true
				 },
				 label: {
					 required: true
				 }
			 },
			 messages: {
				 field:  "请输入字段",
				 label:  "请输入字段名称",
				 dataType:  "请选择字段类型",
				 priority:  {required:"请输入排序",digits:"只能是数字"},
				 helpPosition:  "只能是数字",
				 size:  "只能是数字"
			 },
			 invalidHandler: function (event, validator) { //display error alert on form submit
				 Metronic.alert({
					 place: "append",
					 type: "danger",
					 message: '下面的信息填写有误，请仔细检查',
					 close: true,
					 reset: true,
					 focus: true,
					 closeInSeconds: 5,
					 icon: "warning"
				 });
			 },
			 highlight: function (element) {
				 $(element).closest('.form-group').addClass('has-error');
			 },
			 unhighlight: function (element) {
				 $(element).closest('.form-group').removeClass('has-error');
			 },
			 success: function (label) {
				 label.closest('.form-group').removeClass('has-error');
			 },
			 submitHandler: function (form) {
				 postAjaxtRequest(form);
				 return false;
			 }
		 });
	 }
	
		var handleValidationCompanyEdit = function() {
			var form1 = $('#form_site_company');
			form1.validate({
				errorElement: 'span', //default input error message container
				errorClass: 'help-block', // default input error message class
				focusInvalid: false, // do not focus the last invalid input
				ignore: "",
				rules: {
					name: {
						required: true
					},
					scale: {
						required: true
					}
				},
				messages: {
					name: {
						required: "请输入公司名字"
					},
					scale: {
						required: "请输入公司规模"
					}
				},
				invalidHandler: function (event, validator) { //display error alert on form submit              
					Metronic.alert({
						place: "append", 
						type: "danger",  
						message: '下面的信息填写有误，请仔细检查',  
						close: true, 
						reset: true, 
						focus: true, 
						closeInSeconds: 5, 
						icon: "flash"
					});
				},
				highlight: function (element) { 
					$(element).closest('.form-group').addClass('has-error'); 
				},
				unhighlight: function (element) { 
					$(element).closest('.form-group').removeClass('has-error'); 
				},
				success: function (label) {
					label.closest('.form-group').removeClass('has-error'); 
				},
				submitHandler: function (form) {
					for (instance in CKEDITOR.instances) {
		                CKEDITOR.instances[instance].updateElement();
		            }
					postAjaxtRequest(form);
					return false; 
				}
			});
		}
		
		var handleValidationLoginEdit = function() {
			var form1 = $('#form_site_login');
			form1.validate({
				errorElement: 'span', //default input error message container
				errorClass: 'help-block', // default input error message class
				focusInvalid: false, // do not focus the last invalid input
				ignore: "",
				rules: {
					errorTimes: {
						required: true
					},
					errorInterval: {
						required: true
					}
				},
				messages: {
					errorTimes: {
						required: "请输入登录错误次数"
					},
					errorInterval: {
						required: "请输入登录错误时间"
					}
				},
				invalidHandler: function (event, validator) { //display error alert on form submit              
					Metronic.alert({
						place: "append", 
						type: "danger",  
						message: '下面的信息填写有误，请仔细检查',  
						close: true, 
						reset: true, 
						focus: true, 
						closeInSeconds: 5, 
						icon: "flash"
					});
				},
				highlight: function (element) { 
					$(element).closest('.form-group').addClass('has-error'); 
				},
				unhighlight: function (element) { 
					$(element).closest('.form-group').removeClass('has-error'); 
				},
				success: function (label) {
					label.closest('.form-group').removeClass('has-error'); 
				},
				submitHandler: function (form) {
					postAjaxtRequest(form);
					return false; 
				}
			});
		}
		
		var handleValidationFWEdit = function() {
			var form1 = $('#form_o_firewall_edit');
			form1.validate({
				errorElement: 'span', //default input error message container
				errorClass: 'help-block', // default input error message class
				focusInvalid: false, // do not focus the last invalid input
				ignore: "",
				submitHandler: function (form) {
					postAjaxtRequest(form);
					return false; 
				}
			});
		}
		
		var handleValidationFWLG = function() {
			var form1 = $('#form_system_firewall');
			form1.validate({
				errorElement: 'span', //default input error message container
				errorClass: 'help-block', // default input error message class
				focusInvalid: false, // do not focus the last invalid input
				ignore: "",
				submitHandler: function (form) {
					postAjaxtRequest(form);
					return false; 
				}
			});
		}
	
	var handleValidationType = function() {
		  var form1 = $('#form_type');
		  form1.validate({
		   errorElement: 'span', //default input error message container
		   errorClass: 'help-block', // default input error message class
		   focusInvalid: false, // do not focus the last invalid input
		   ignore: "",
		   rules: {
		    id: {
		     required: true,
		     digits: true
		    },
		    imgWidth: {
		     required: true,
		     digits: true
		    },
		    imgHeight: {
		     required: true,
		     digits: true
		    },
		    name: {
		     required: true
		    }
		   },
		   messages: {
		    id: {
		     required: "请输入id",
		     digits:"只能输入整数"
		    },
		    imgWidth: "请输入图片高度",
		    imgHeight: "请输入图片宽度",
		    name: "请输入名称"
		   },
		            invalidHandler: function (event, validator) { //display error alert on form submit
		             Metronic.alert({
		                    place: "append",
		                    type: "danger",
		                    message: '下面的信息填写有误，请仔细检查',
		                    close: true,
		                    reset: true,
		                    focus: true,
		                    closeInSeconds: 5,
		                    icon: "warning"
		                });
		            },
		   highlight: function (element) {
		    $(element).closest('.form-group').addClass('has-error');
		   },
		   unhighlight: function (element) {
		    $(element).closest('.form-group').removeClass('has-error');
		   },
		   success: function (label) {
		    label.closest('.form-group').removeClass('has-error');
		   },
		   submitHandler: function (form) {
		    postAjaxtRequest(form);
		    return false;
		   }
		  });
		 }
	
	var handleValidationSystemEdit = function() {
		  var form1 = $('#form_system_edit');
		  form1.validate({
		   errorElement: 'span', //default input error message container
		   errorClass: 'help-block', // default input error message class
		   focusInvalid: false, // do not focus the last invalid input
		   ignore: "",
		   rules: {
		    dbFileUri: {
		     required: true
		    },
		    port: {
		     digits: true
		    }
		   },
		   messages: {
		    dbFileUri: {
		     required: "请输入数据库路径"
		    },
		    port: {
		     digits: "只能输入整数"
		    }
		   },
		   invalidHandler: function (event, validator) { //display error alert on form submit
		    Metronic.alert({
		     place: "append",
		     type: "danger",
		     message: '下面的信息填写有误，请仔细检查',
		     close: true,
		     reset: true,
		     focus: true,
		     closeInSeconds: 5,
		     icon: "warning"
		    });
		   },
		   highlight: function (element) {
		    $(element).closest('.form-group').addClass('has-error');
		   },
		   unhighlight: function (element) {
		    $(element).closest('.form-group').removeClass('has-error');
		   },
		   success: function (label) {
		    label.closest('.form-group').removeClass('has-error');
		   },
		   submitHandler: function (form) {
		    postAjaxtRequest(form);
		    return false;
		   }
		  });
		 }
	
	var handleValidationSiteCfgEdit = function() {
		var form1 = $('#form_site_config');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				path: {
					required: true
				},
				shortName: {
					required: true
				},
				domain: {
					remote: "site/v_checkDomain.do?siteId="+$("#siteId").val()
				},
				name: {
					required: true
				}
			},
			messages: {
				path: {
					required: "请输入路径"
				},
				shortName: {
					required: "请输入站点简称"
				},
				domain: {
					remote: $.validator.format("此域名已被使用, 请使用别的域名")
				},
				name: {
					required: "请输入站点名称"
				}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit
				Metronic.alert({
					place: "append",
					type: "danger",
					message: '下面的信息填写有误，请仔细检查',
					close: true,
					reset: true,
					focus: true,
					closeInSeconds: 5,
					icon: "flash"
				});
			},
			highlight: function (element) {
				$(element).closest('.form-group').addClass('has-error');
			},
			unhighlight: function (element) {
				$(element).closest('.form-group').removeClass('has-error');
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error');
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false;
			}
		});
	}
	
	var handleValidationSite = function() {
		var form1 = $('#form_config_site');
		var r_url = "site/v_checkDomain.do";
		if(undefined!=$("#siteId").val()&&$("#siteId").val()!=""){
			r_url = r_url+"?siteId="+$("#siteId").val();
		}
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					required: true
				},
				domain: {
					remote: r_url
				},
				id: {
					required: true
				}
			},
			messages: {
				name: {
					required: "请输入站点名字"
				},
				domain: {
					remote: $.validator.format("此域名已被使用, 请使用别的域名")
				},
				id: {
					required: "请输入IP"
				}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationFTP = function() {
		var form1 = $('#form_config_ftp');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				name: {
					required: true
				},
				id: {
					required: true
				}
			},
			messages: {
				name: {
					required: "请输入FTP名字"
				},
				id: {
					required: "请输入IP"
				}
			},
			invalidHandler: function (event, validator) { //display error alert on form submit              
				Metronic.alert({
					place: "append", 
					type: "danger",  
					message: '下面的信息填写有误，请仔细检查',  
					close: true, 
					reset: true, 
					focus: true, 
					closeInSeconds: 5, 
					icon: "flash"
				});
			},
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationSCRIEdit = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationSCRGEdit = function() {
		var form1 = $('#jvForm');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationMarkEdit = function() {
		var form1 = $('#form_o_mark_update');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationATRREdit = function() {
		var form1 = $('#form_system_edit');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationAPIEdit = function() {
		var form1 = $('#form_system_edit');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationMemEdit = function() {
		var form1 = $('#form_o_member_update');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	 
	var handleValidationStatisticMember = function() {
		var form1 = $('#form_statistic_member');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationStatisticContent = function() {
		var form1 = $('#form_statistic_content');
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
	var handleValidationDictionary = function() {
		var form1 = $('#form_dictionary');
		
		form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			rules: {
				type: {
					required: true
				},
				name: {
					required: true
				}
			},
			messages: {
				type: {
					required: "请输入类型"
				},
				name:"请输入名称"
			},
            invalidHandler: function (event, validator) { //display error alert on form submit              
            	Metronic.alert({
                    place: "append", 
                    type: "danger",  
                    message: '下面的信息填写有误，请仔细检查',  
                    close: true, 
                    reset: true, 
                    focus: true, 
                    closeInSeconds: 5, 
                    icon: "warning"
                });
            },
			highlight: function (element) { 
				$(element).closest('.form-group').addClass('has-error'); 
			},
			unhighlight: function (element) { 
				$(element).closest('.form-group').removeClass('has-error'); 
			},
			success: function (label) {
				label.closest('.form-group').removeClass('has-error'); 
			},
			submitHandler: function (form) {
				postAjaxtRequest(form);
				return false; 
			}
		});
	}
	
    var handleValidationStatisticComment = function() {
            var form1 = $('#form_statistic_comment');
            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                submitHandler: function (form) {
                    postAjaxtRequest(form);
                    return false; 
                }
            });
    }
    
    var handleValidationStatisticGuestbook = function() {
    	var form1 = $('#form_statistic_guestbook');
    	form1.validate({
    		errorElement: 'span', //default input error message container
    		errorClass: 'help-block', // default input error message class
    		focusInvalid: false, // do not focus the last invalid input
    		ignore: "",
    		submitHandler: function (form) {
    			postAjaxtRequest(form);
    			return false; 
    		}
    	});
    }
	
	var handleValidationFlowInit = function() {
	var form1 = $('#form_statistic_flow_init');
	form1.validate({
			errorElement: 'span', //default input error message container
			errorClass: 'help-block', // default input error message class
			focusInvalid: false, // do not focus the last invalid input
			ignore: "",
			submitHandler: function (form) {
				if(confirm('您确定执行初始化吗？')){
					postAjaxtRequest(form);
					return false; 
				}else{
					return false; 
				}
			}
		});
    }

    return {
        //main function to initiate the module
    	handleValidationPersonalInfo: function () {
    		handleValidationPersonalInfo();
    	},
    	handleValidationStatisticComment: function () {
    		handleValidationStatisticComment();
    	},
    	handleValidationStatisticMember: function () {
    		handleValidationStatisticMember();
    	},
    	handleValidationStatisticContent: function () {
    		handleValidationStatisticContent();
        },
        handleValidationStatisticGuestbook: function () {
        	handleValidationStatisticGuestbook();
        },
		handleValidationFlowInit: function () {
        	handleValidationFlowInit();
        },
        handleValidationFlowInit: function () {
        	handleValidationFlowInit();
        },
        handleValidationChannel: function () {
        	handleValidationChannel();
        },
        handleValidationContent: function () {
        	handleValidationContent();
        },
        handleValidationModelItem: function () {
            handleValidationModelItem();
        },
        handleContentQuery: function () {
        	handleContentQuery();
        },
        handleValidationSystemEdit: function () {
        	handleValidationSystemEdit();
        },
        handleValidationType: function () {
        	handleValidationType();
        },
        handleValidationSiteCfgEdit: function () {
        	handleValidationSiteCfgEdit();
        },
        handleValidationCompanyEdit: function () {
        	handleValidationCompanyEdit();
        },
        handleValidationLoginEdit: function () {
        	handleValidationLoginEdit();
        },
        handleValidationFWLG: function () {
        	handleValidationFWLG();
        },
        handleValidationFWEdit: function () {
        	handleValidationFWEdit();
        },
        handleValidationMarkEdit: function () {
        	handleValidationMarkEdit();
        },
        handleValidationMemEdit: function () {
        	handleValidationMemEdit();
        },
        handleValidationFTP: function () {
        	handleValidationFTP();
        },
        handleValidationSite: function () {
        	handleValidationSite();
        },
        handleValidationDictionary: function () {
        	handleValidationDictionary();
        },
        handleValidationAdminLocal: function () {
        	handleValidationAdminLocal();
        },
        handleValidationAdminGlobal: function () {
        	handleValidationAdminGlobal();
        },
        handleValidationRole: function () {
        	handleValidationRole();
        },
        handleValidationMember: function () {
        	handleValidationMember();
        },
        handleValidationGroup: function () {
        	handleValidationGroup();
        },
        handleValidationTask: function () {
        	handleValidationTask();
        },
        handleValidationTag: function () {
        	handleValidationTag();
        },
        handleValidationCommon: function () {
        	handleValidationCommon();
        },
        handleResourceRename: function () {
        	handleResourceRename();
        },
        handleTemplateRename: function () {
        	handleTemplateRename();
        },
        handleValidationAPIEdit: function () {
        	handleValidationAPIEdit();
        },
        handleValidationATRREdit: function () {
        	handleValidationATRREdit();
        },
        handleValidationREGEdit: function () {
        	handleValidationREGEdit();
        },
        handleValidationSCRIEdit: function () {
        	handleValidationSCRIEdit();
        },
        handleValidationSCRGEdit: function () {
        	handleValidationSCRGEdit();
        },
        handleValidationModel: function () {
        	handleValidationModel();
    	}
    };

}();