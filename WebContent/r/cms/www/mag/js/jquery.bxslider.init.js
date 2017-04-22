jQuery(document).ready(function($) {

	$('.bxslider').bxSlider({
	  minSlides: 1,
	  maxSlides: 4,
	  pager: false,
	  adaptiveHeight: true,
  	  slideWidth: 172,
  	  slideMargin: 27,
  	  nextText: '',
  	  prevText: ''
	});
	$('.gallery').bxSlider({
	  minSlides: 1,
	  maxSlides: 6,
	  pager: false,
	  adaptiveHeight: true,
  	  slideWidth: 172,
  	  slideMargin: 27,
  	  nextText: '',
  	  prevText: ''
	});

});