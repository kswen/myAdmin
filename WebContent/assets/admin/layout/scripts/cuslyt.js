var CusLyt = function() {

    // Handle Theme Settings
    var handleTheme = function() {

        var panel = $('.theme-panel');

        if ($('body').hasClass('page-boxed') === false) {
            $('.layout-option', panel).val("fluid");
        }

        $('.sidebar-option', panel).val("default");
        $('.page-header-option', panel).val("fixed");
        $('.page-footer-option', panel).val("default");
        if ($('.sidebar-pos-option').attr("disabled") === false) {
            $('.sidebar-pos-option', panel).val(Metronic.isRTL() ? 'right' : 'left');
        }

        //handle theme layout
        var resetLayout = function() {
            $("body").
            removeClass("page-boxed").
            removeClass("page-footer-fixed").
            removeClass("page-sidebar-fixed").
            removeClass("page-header-fixed").
            removeClass("page-sidebar-reversed");

            $('.page-header > .page-header-inner').removeClass("container");

            if ($('.page-container').parent(".container").size() === 1) {
                $('.page-container').insertAfter('body > .clearfix');
            }

            if ($('.page-footer > .container').size() === 1) {
                $('.page-footer').html($('.page-footer > .container').html());
            } else if ($('.page-footer').parent(".container").size() === 1) {
                $('.page-footer').insertAfter('.page-container');
                $('.scroll-to-top').insertAfter('.page-footer');
            }

             $(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");

            $('body > .container').remove();
        };

        var lastSelectedLayout = '';

        var setLayout = function(isManualSet) {

            var layoutOption = $('.layout-option', panel).val();
            var sidebarOption = $('.sidebar-option', panel).val();
            var headerOption = $('.page-header-option', panel).val();
            var footerOption = $('.page-footer-option', panel).val();
            var sidebarPosOption = $('.sidebar-pos-option', panel).val();
            var sidebarStyleOption = $('.sidebar-style-option', panel).val();
            var sidebarMenuOption = $('.sidebar-menu-option', panel).val();
            var headerTopDropdownStyle = $('.page-header-top-dropdown-style-option', panel).val();

            if("change"!=isManualSet.type){
				if(store.get('theme_layoutOption')!==undefined){
					layoutOption = store.get('theme_layoutOption');
					$('.layout-option', panel).val(layoutOption);
				}
				if(store.get('theme_sidebarOption')!==undefined){
					sidebarOption = store.get('theme_sidebarOption');
					$('.sidebar-option', panel).val(sidebarOption);
				}
				if(store.get('theme_headerOption')!==undefined){
					headerOption = store.get('theme_headerOption');
					$('.header-option', panel).val(headerOption);
				}
				if(store.get('theme_footerOption')!==undefined){
					footerOption = store.get('theme_footerOption');
					$('.footer-option', panel).val(footerOption);
				}
				if(store.get('theme_sidebarPosOption')!==undefined){
					sidebarPosOption = store.get('theme_sidebarPosOption');
					$('.sidebar-pos-option', panel).val(sidebarPosOption);
				}				
				if(store.get('theme_sidebarStyleOption')!==undefined){
					sidebarStyleOption = store.get('theme_sidebarStyleOption');
					$('.sidebar-style-option', panel).val(sidebarStyleOption);
				}				
				if(store.get('theme_sidebarMenuOption')!==undefined){
					sidebarMenuOption = store.get('theme_sidebarMenuOption');
					$('.sidebar-menu-option', panel).val(sidebarMenuOption);
				}				
				if(store.get('theme_headerTopDropdownStyle')!==undefined){
					headerTopDropdownStyle = store.get('theme_headerTopDropdownStyle');
					$('.page-header-top-dropdown-style-option', panel).val(headerTopDropdownStyle);
				}				
			}
            
			//save layout states
			store.set('theme_layoutOption', layoutOption);
			store.set('theme_sidebarOption', sidebarOption);
			store.set('theme_headerOption', headerOption);
			store.set('theme_footerOption', footerOption);
			store.set('theme_sidebarPosOption', sidebarPosOption);
			store.set('theme_sidebarStyleOption', sidebarStyleOption);
			store.set('theme_sidebarMenuOption', sidebarMenuOption);
			store.set('theme_headerTopDropdownStyle', headerTopDropdownStyle);
            
            if (sidebarOption == "fixed" && headerOption == "default") {
                alert('页头（默认）+导航栏（固定）组合不支持，自动调整为固定布局.');
                $('.page-header-option', panel).val("fixed");
                $('.sidebar-option', panel).val("fixed");
                sidebarOption = 'fixed';
                headerOption = 'fixed';
            }

            resetLayout(); // reset layout to default state

            if (layoutOption === "boxed") {
                $("body").addClass("page-boxed");

                // set header
                $('.page-header > .page-header-inner').addClass("container");
                var cont = $('body > .clearfix').after('<div class="container"></div>');

                // set content
                $('.page-container').appendTo('body > .container');

                // set footer
                if (footerOption === 'fixed') {
                    $('.page-footer').html('<div class="container">' + $('.page-footer').html() + '</div>');
                } else {
                    $('.page-footer').appendTo('body > .container');
                }
            }

            if (lastSelectedLayout != layoutOption) {
                //layout changed, run responsive handler: 
                Metronic.runResizeHandlers();
            }
            lastSelectedLayout = layoutOption;

            //header
            if (headerOption === 'fixed') {
                $("body").addClass("page-header-fixed");
                $(".page-header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
            } else {
                $("body").removeClass("page-header-fixed");
                $(".page-header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
            }

            //sidebar
            if ($('body').hasClass('page-full-width') === false) {
                if (sidebarOption === 'fixed') {
                    $("body").addClass("page-sidebar-fixed");
                    $("page-sidebar-menu").addClass("page-sidebar-menu-fixed");
                    $("page-sidebar-menu").removeClass("page-sidebar-menu-default");
                    Layout.initFixedSidebarHoverEffect();
                } else {
                    $("body").removeClass("page-sidebar-fixed");
                    $("page-sidebar-menu").addClass("page-sidebar-menu-default");
                    $("page-sidebar-menu").removeClass("page-sidebar-menu-fixed");
                    $('.page-sidebar-menu').unbind('mouseenter').unbind('mouseleave');
                }
            }

            // top dropdown style
            if (headerTopDropdownStyle === 'dark') {
                $(".top-menu > .navbar-nav > li.dropdown").addClass("dropdown-dark");
            } else {
                $(".top-menu > .navbar-nav > li.dropdown").removeClass("dropdown-dark");
            }

            //footer 
            if (footerOption === 'fixed') {
                $("body").addClass("page-footer-fixed");
            } else {
                $("body").removeClass("page-footer-fixed");
            }

            //sidebar style
            if (sidebarStyleOption === 'light') {
                $(".page-sidebar-menu").addClass("page-sidebar-menu-light");
            } else {
                $(".page-sidebar-menu").removeClass("page-sidebar-menu-light");
            }

            //sidebar menu 
            if (sidebarMenuOption === 'hover') {
                if (sidebarOption == 'fixed') {
                    $('.sidebar-menu-option', panel).val("accordion");
                    alert("Hover Sidebar Menu is not compatible with Fixed Sidebar Mode. Select Default Sidebar Mode Instead.");
                } else {
                    $(".page-sidebar-menu").addClass("page-sidebar-menu-hover-submenu");
                }
            } else {
                $(".page-sidebar-menu").removeClass("page-sidebar-menu-hover-submenu");
            }

            //sidebar position
            if (Metronic.isRTL()) {
                if (sidebarPosOption === 'left') {
                    $("body").addClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'right'
                    });
                } else {
                    $("body").removeClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'left'
                    });
                }
            } else {
                if (sidebarPosOption === 'right') {
                    $("body").addClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'left'
                    });
                } else {
                    $("body").removeClass("page-sidebar-reversed");
                    $('#frontend-link').tooltip('destroy').tooltip({
                        placement: 'right'
                    });
                }
            }

            Layout.fixContentHeight(); // fix content height            
            Layout.initFixedSidebar(); // reinitialize fixed sidebar
        };

        // handle theme colors
        var setColor = function(color) {
            var color_ = (Metronic.isRTL() ? color + '-rtl' : color);
            $('#style_color').attr("href", Layout.getLayoutCssPath() + 'themes/' + color_ + ".css");
            if (color == 'light2') {
                $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo-invert.png');
            } else {
                $('.page-logo img').attr('src', Layout.getLayoutImgPath() + 'logo.png');
            }
            if ($.cookie) {               
                $.cookie('style_color', color);
            }
        };

        $('.toggler', panel).click(function() {
            $('.toggler').hide();
            $('.toggler-close').show();
            $('.theme-panel > .theme-options').show();
        });

        $('.toggler-close', panel).click(function() {
            $('.toggler').show();
            $('.toggler-close').hide();
            $('.theme-panel > .theme-options').hide();
        });

        $('.theme-colors > ul > li', panel).click(function() {
            var color = $(this).attr("data-style");
            setColor(color);
            $('ul > li', panel).removeClass("current");
            $(this).addClass("current");
        });

        // set default theme options:

        if ($("body").hasClass("page-boxed")) {
            $('.layout-option', panel).val("boxed");
        }

        if ($("body").hasClass("page-sidebar-fixed")) {
            $('.sidebar-option', panel).val("fixed");
        }

        if ($("body").hasClass("page-header-fixed")) {
            $('.page-header-option', panel).val("fixed");
        }

        if ($("body").hasClass("page-footer-fixed")) {
            $('.page-footer-option', panel).val("fixed");
        }

        if ($("body").hasClass("page-sidebar-reversed")) {
            $('.sidebar-pos-option', panel).val("right");
        }

        if ($(".page-sidebar-menu").hasClass("page-sidebar-menu-light")) {
            $('.sidebar-style-option', panel).val("light");
        }

        if ($(".page-sidebar-menu").hasClass("page-sidebar-menu-hover-submenu")) {
            $('.sidebar-menu-option', panel).val("hover");
        }

        $('.layout-option, .page-header-option, .page-header-top-dropdown-style-option, .sidebar-option, .page-footer-option, .sidebar-pos-option, .sidebar-style-option, .sidebar-menu-option', panel).change(setLayout);
        if ($.cookie && $.cookie('style_color')) {
            setColor($.cookie('style_color'));
        }else{
        	  setColor('default');
        }
        setLayout(true);//restore last time layout setting
    };

    // handle theme style
    var setThemeStyle = function(style) {
        var file = (style === 'rounded' ? 'components-rounded' : 'components');
        file = (Metronic.isRTL() ? file + '-rtl' : file);

        $('#style_components').attr("href", Metronic.getGlobalCssPath() + file + ".css");

        if ($.cookie) {
            $.cookie('layout-style-option', style);
        }
    };
    
    // Handle full screen mode toggle
    var handleFullScreenMode = function() {
        // mozfullscreenerror event handler
       
        // toggle full screen
        function toggleFullScreen() {
          if (!document.fullscreenElement &&    // alternative standard method
              !document.mozFullScreenElement && !document.webkitFullscreenElement) {  // current working methods
            if (document.documentElement.requestFullscreen) {
              document.documentElement.requestFullscreen();
            } else if (document.documentElement.mozRequestFullScreen) {
              document.documentElement.mozRequestFullScreen();
            } else if (document.documentElement.webkitRequestFullscreen) {
              document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
            }
          } else {
            if (document.cancelFullScreen) {
              document.cancelFullScreen();
            } else if (document.mozCancelFullScreen) {
              document.mozCancelFullScreen();
            } else if (document.webkitCancelFullScreen) {
              document.webkitCancelFullScreen();
            }
          }
        }

        $('#trigger_fullscreen').click(function() {
            toggleFullScreen();
        });
    };

    var setMenuLayout = function() {
    	if($(".contentDiv")){
    		$(".contentDiv").hide();
    	}
    	
    	if($(".maintainDiv")){
    		$(".maintainDiv").hide();
    	}
    	
    	if($(".dataDiv")){
    		$(".dataDiv").hide();
    	}
    	
    	if($(".configDiv")){
    		$(".configDiv").hide();
    	}
    	
    	if($(".userDiv")){
    		$(".userDiv").hide();
    	}
    	
    	if($(".expandDiv")){
    		$(".expandDiv").hide();
    	}
    	
    	$("#workBenchMenu").click( function () {  
    		$("#workBenchMenu").parent("li").addClass("active");
    		$(".workBenchDiv").show();
    		if($(".contentDiv")){
    			$(".contentDiv").hide();
    			$("#contentMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".maintainDiv")){
    			$(".maintainDiv").hide();
    			$("#maintainMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".dataDiv")){
    			$(".dataDiv").hide();
    			$("#dataMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".configDiv")){
    			$(".configDiv").hide();
    			$("#configMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".userDiv")){
    			$(".userDiv").hide();
    			$("#userMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".expandDiv")){
    			$(".expandDiv").hide();
    			$("#expandMenu").parent("li").removeClass("active");
    		}
    	});
    	$("#contentMenu").click( function () {  
    		$("#contentMenu").parent("li").addClass("active");
    		$(".contentDiv").show();
    		if($(".workBenchDiv")){
    			$(".workBenchDiv").hide();
    			$("#workBenchMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".maintainDiv")){
    			$(".maintainDiv").hide();
    			$("#maintainMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".dataDiv")){
    			$(".dataDiv").hide();
    			$("#dataMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".configDiv")){
    			$(".configDiv").hide();
    			$("#configMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".userDiv")){
    			$(".userDiv").hide();
    			$("#userMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".expandDiv")){
    			$(".expandDiv").hide();
    			$("#expandMenu").parent("li").removeClass("active");
    		}
    	});
    	
    	$("#maintainMenu").click( function () {  
    		$("#maintainMenu").parent("li").addClass("active");
    		$(".maintainDiv").show();
    		if($(".workBenchDiv")){
    			$(".workBenchDiv").hide();
    			$("#workBenchMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".contentDiv")){
    			$(".contentDiv").hide();
    			$("#contentMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".dataDiv")){
    			$(".dataDiv").hide();
    			$("#dataMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".configDiv")){
    			$(".configDiv").hide();
    			$("#configMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".userDiv")){
    			$(".userDiv").hide();
    			$("#userMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".expandDiv")){
    			$(".expandDiv").hide();
    			$("#expandMenu").parent("li").removeClass("active");
    		}
    	});
    	
    	$("#dataMenu").click( function () {  
    		$("#dataMenu").parent("li").addClass("active");
    		$(".dataDiv").show();
    		if($(".workBenchDiv")){
    			$(".workBenchDiv").hide();
    			$("#workBenchMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".contentDiv")){
    			$(".contentDiv").hide();
    			$("#contentMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".maintainDiv")){
    			$(".maintainDiv").hide();
    			$("#maintainMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".configDiv")){
    			$(".configDiv").hide();
    			$("#configMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".userDiv")){
    			$(".userDiv").hide();
    			$("#userMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".expandDiv")){
    			$(".expandDiv").hide();
    			$("#expandMenu").parent("li").removeClass("active");
    		}
    	});
    	
    	$("#configMenu").click( function () {  
    		$("#configMenu").parent("li").addClass("active");
    		$(".configDiv").show();
    		if($(".workBenchDiv")){
    			$(".workBenchDiv").hide();
    			$("#workBenchMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".contentDiv")){
    			$(".contentDiv").hide();
    			$("#contentMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".maintainDiv")){
    			$(".maintainDiv").hide();
    			$("#maintainMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".dataDiv")){
    			$(".dataDiv").hide();
    			$("#dataMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".userDiv")){
    			$(".userDiv").hide();
    			$("#userMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".expandDiv")){
    			$(".expandDiv").hide();
    			$("#expandMenu").parent("li").removeClass("active");
    		}
    	});
    	
    	$("#userMenu").click( function () {  
    		$("#userMenu").parent("li").addClass("active");
    		$(".userDiv").show();
    		if($(".workBenchDiv")){
    			$(".workBenchDiv").hide();
    			$("#workBenchMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".contentDiv")){
    			$(".contentDiv").hide();
    			$("#contentMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".maintainDiv")){
    			$(".maintainDiv").hide();
    			$("#maintainMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".dataDiv")){
    			$(".dataDiv").hide();
    			$("#dataMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".configDiv")){
    			$(".configDiv").hide();
    			$("#configMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".expandDiv")){
    			$(".expandDiv").hide();
    			$("#expandMenu").parent("li").removeClass("active");
    		}
    	});
    	
    	$("#expandMenu").click( function () {  
    		$("#expandMenu").parent("li").addClass("active");
    		$(".expandDiv").show();
    		if($(".workBenchDiv")){
    			$(".workBenchDiv").hide();
    			$("#workBenchMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".contentDiv")){
    			$(".contentDiv").hide();
    			$("#contentMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".maintainDiv")){
    			$(".maintainDiv").hide();
    			$("#maintainMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".dataDiv")){
    			$(".dataDiv").hide();
    			$("#dataMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".configDiv")){
    			$(".configDiv").hide();
    			$("#configMenu").parent("li").removeClass("active");
    		}
    		
    		if($(".userDiv")){
    			$(".userDiv").hide();
    			$("#userMenu").parent("li").removeClass("active");
    		}
    	});
    	
    };
    
    return {

        //main function to initiate the theme
        init: function() {
            // handles style customer tool
            handleTheme(); 
            handleFullScreenMode();
            setMenuLayout();
            
            // handle layout style change
            $('.theme-panel .layout-style-option').change(function() {
                 setThemeStyle($(this).val());
            });

            // set layout style from cookie
            if ($.cookie && $.cookie('layout-style-option') === 'rounded') {
                setThemeStyle($.cookie('layout-style-option'));
                $('.theme-panel .layout-style-option').val($.cookie('layout-style-option'));
            }            
        }
    };

}();