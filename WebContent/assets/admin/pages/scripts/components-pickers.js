var ComponentsPickers = function () {
	
	var handleDatePickers = function () {
		if (jQuery().datepicker) {
			$('.date-picker').datepicker({
				rtl: Metronic.isRTL(),
				autoclose: true,
				language:'zh-CN',
				format: "yyyy-mm-dd", 
			});
			$('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
		}
	}

    var handleYearPickers = function () {
        if (jQuery().datepicker) {
            $('.year-picker').datepicker({
                rtl: Metronic.isRTL(),
                autoclose: true,
                language:'zh-CN',
                format: " yyyy", // Notice the Extra space at the beginning
                viewMode: "years", 
                minViewMode: "years"
            });
            $('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
        }
    }

    var handleColorPicker = function () {
        if (!jQuery().colorpicker) {
            return;
        }
        $('.colorpicker-default').colorpicker({
            format: 'hex'
        });
        $('.colorpicker-rgba').colorpicker();
    }
   

    return {
        //main function to initiate the module
    	handleDatePickers: function () {
    		handleDatePickers();
    	},
    	handleYearPickers: function () {
    		handleYearPickers();
        },
        handleColorPicker: function () {
	    	handleColorPicker();
	    }
    };

}();