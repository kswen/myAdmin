/**
 *
 * Dependencies: jQuery
 *
 */
 
(function($) {
	$(function() {
		$("#scroller").simplyScroll();
	});
})(jQuery);

jQuery(document).ready(function($) {

		/*
		* Fix dropdown menu bootstrap error 
		* ------------------------------------------------- */

		$('.nav').find('li:has(ul)').addClass('dropdown');
		$('.dropdown > a').addClass('dropdown-toggle disabled');
		$('li.dropdown').children('ul.sub-menu').addClass('dropdown-menu');
		
		/*
		* Fix dropdown menu bootstrap error ends
		* --------------------------------------------------------- */	

    $('.dropdown .sub-menu').addClass('dropdown-menu');	
});

jQuery(document).ready(function($) {
    $('.dropdown > a').append('<b class="caret"></b>').dropdown();
    $('.dropdown .sub-menu').addClass('dropdown-menu');
});


jQuery(document).ready(function($) {
  
	$("#tabnav").idTabs(); 	
 
});

/* Swipe menu initial js */
jQuery(document).ready(function($) {

	jQuery('#responsive-menu-button').sidr({
		name: 'sidr-right',
		speed: 50,
		side: 'right',
		source: '#swipe-menu-responsive'	
	});
}); 