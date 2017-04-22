/* Swipe menu initial js */


jQuery(document).ready(function($) {

	jQuery('#responsive-menu-button').sidr({
		name: 'sidr-right',
		speed: 50,
		side: 'right',
		source: '#swipe-menu-responsive'	
	});
		
	/*	
		
	jQuery(window).touchwipe({
		wipeRight: function() {
		// Close
		$.sidr('close', 'sidr-right');
		},
		wipeLeft: function() {
		// Open
		$.sidr('open', 'sidr-right');
		},
		preventDefaultEvents: false
	}); 
	
	*/
 
}); 