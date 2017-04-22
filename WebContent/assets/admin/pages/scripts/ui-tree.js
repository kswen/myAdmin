var UITree = function() {
	
    var ajaxLeftChannelTree = function() {
        $("#tree_channel_list").jstree({
            "core" : {
                "themes" : {
                    "responsive": true
                }, 
                "check_callback" : true,
                'data' : {
                    'url' : function (node) {
                      return 'channel/v_tree.do';
                    },
                    'data' : function (node) {
                      return { 'root' : node.id };
                    }
                }
            },
            "state" : { "key" : "left_tree_channel_list" },
            "plugins" : [ "state" ]
        });
    };
    
    var ajaxLeftChannelTree4Content = function() {
    	$("#left_channel_tree_4_content_list").jstree({
    		"core" : {
    			"themes" : {
    				"responsive": true
    			}, 
    			"check_callback" : true,
    			'data' : {
    				'url' : function (node) {
    					return 'content/v_tree.do';
    				},
    				'data' : function (node) {
    					return { 'root' : node.id };
    				}
    			}
    		},
    		"state" : { "key" : "left_channel_tree_4_content_list" },
    		"plugins" : [ "state" ]
    	});
    };
    
    var ajaxLeftTemplateTree = function() {
    	$("#left_template_tree").jstree({
    		"core" : {
    			"themes" : {
    				"responsive": true
    			}, 
    			"check_callback" : true,
    			'data' : {
    				'url' : function (node) {
    					return 'template/v_tree.do';
    				},
    				'data' : function (node) {
    					return { 'root' : node.id };
    				}
    			}
    		},
    		"state" : { "key" : "left_template_tree_list" },
    		"plugins" : [ "state" ]
    	});
    };
    
    var ajaxLeftResourceTree = function() {
    	$("#left_resource_tree").jstree({
    		"core" : {
    			"themes" : {
    				"responsive": true
    			}, 
    			"check_callback" : true,
    			'data' : {
    				'url' : function (node) {
    					return 'resource/v_tree.do';
    				},
    				'data' : function (node) {
    					return { 'root' : node.id };
    				}
    			}
    		},
    		"state" : { "key" : "left_resource_tree_list" },
    		"plugins" : [ "state" ]
    	});
    };

	return {
		// main function to initiate the module
		init : function() {
			ajaxLeftChannelTree();
			ajaxLeftChannelTree4Content();
			ajaxLeftTemplateTree();
			ajaxLeftResourceTree();
		},
		ajaxLeftTemplateTree : function() {
			return ajaxLeftTemplateTree();
		},
		ajaxLeftResourceTree : function() {
			return ajaxLeftResourceTree();
		},
		ajaxLeftChannelTree : function() {
			return ajaxLeftChannelTree();
		},
		ajaxLeftChannelTree4Content : function() {
			return ajaxLeftChannelTree4Content();
		}
	};

}();