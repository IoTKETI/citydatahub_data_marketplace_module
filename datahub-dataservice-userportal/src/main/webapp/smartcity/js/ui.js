var N2M = N2M || {};

N2M.ui = function() {



  // element
  var $allmenu                  = $('.all-menu'),
      $html                     = $('html'),
      $nav                      = $('.nav'),
      $navDepth1List            = $('.nav-depth1__list'),
      $navDepth1Link            = $('.nav-depth1__link'),
      $navDepth1Item            = $('.nav-depth1__item'),
      $navDepth2List            = $('.nav-depth2__list'),
      $navBG                    = $('.nav__bg'),
      $footerTopButton          = $('.footer-top-button'),
      $footerDropdownMenu       = $('.footer-dropdown-menu'),
      $footerDropdownMenuButton = $('.footer-dropdown-menu__button'),
      $categoryNav              = $('.category-nav'),
      $categoryNavItem          = $('.category-nav__item'),
      $window                   = $(window);

  // [Main]
  var $mainBestTabButton        = $('.main-best-tab__button'),
      $mainEtcTabButton         = $('.main-etc-tab__button');

  // [Main] swiper-slide's option
  function setSwiperSlide() {
    // main 카테고리's slider
    var swiper1 = new Swiper('.main-slider', {
      autoplay           : {
        delay : 2500,
        disableOnInteraction : false,
      },
      grabCursor         : false,
      loop               : true, 
      keyboard           : {
        enabled : true,
      },
      navigation         : {
        nextEl: '.main-slider__button--next',
        prevEl: '.main-slider__button--prev',
      },
      roundLengths	     : true,
      slidesPerGroup     : 1, 
      slidesPerView      : 1, 
      spaceBetween       : 10, 
      scrollbar          : {
        draggable : false,
      },
      breakpointsInverse : true, 
      breakpoints: { 
        768: { 
          slidesPerView : 2, 
        },
        1024: {
          slidesPerView : 3, 
          spaceBetween  : 10, 
        },
        1260: { 
          slidesPerView : 3, 
          spaceBetween  : 41, 
        },
      },
    });
  
    // main 알림마당's slider
    var swipers = [];
    $('.main-etc-tab__item').each(function(index, item){
      var $this  = $(this);
      var swiper = new Swiper(this, {
        direction          : 'vertical',
        grabCursor         : false,
        initialSlide       :	0,
        keyboard           : {
          enabled : true,
        },
        loop               : true,
        navigation         : {
          nextEl : $this.find('.main-etc-swiper__button--next')[0],
          prevEl : $this.find('.main-etc-swiper__button--prev')[0],
        },
        noSwiping          : true,
        noSwipingClass     : 'main-etc-tab__panel',
        scrollbar          : {
          draggable : false,
        },
        roundLengths	     : true,
        slidesPerView      : 3, 
        slidesPerGroup     : 3,
        spaceBetween       : 0,
        breakpointsInverse : true, 
        breakpoints: { 
          641: {
            direction: 'horizontal',
            noSwiping: false,
          },
          1260: { 
            direction: 'horizontal',
            slidesPerView: 4, 
            slidesPerGroup: 1,
          }, 
        },
      });
      swipers.push(swiper);
    });

    var swiper3 = new Swiper('.select-result__banner', {
      autoplay            : {
        delay : 2500,
        disableOnInteraction : false,
      },
      direction           : 'horizontal',
      grabCursor          : false,
      initialSlide        :	0,
      keyboard            : {
        enabled : true,
      },
      loop                : false,
      navigation          : {
        nextEl : '.select-result__banner-button--next',
        prevEl : '.select-result__banner-button--prev',
      },
      roundLengths	      : true,
      slidesPerView       : 3, 
      slidesPerGroup      : 1,
      /*slidesOffsetBefore	: -40,*/
      spaceBetween        : 0,
      /*width               : 784,*/
      breakpointsInverse  : true, 
      breakpoints         : { 
        // 641: {
        //   direction: 'horizontal',
        // },
        // 1260: { 
        //   direction: 'horizontal',
        //   slidesPerView: 4, 
        //   slidesPerGroup: 1,
        // }, 
      },
    });
    
    var swiper4 = new Swiper('.review-slider__container', {
      autoplay            : {
        delay : 2500,
        disableOnInteraction : false,
      },
      centeredSlides      : true,
      direction           : 'horizontal',
      grabCursor          : false,
      initialSlide        :	2,
      keyboard            : {
        enabled : true,
      },
      loop                : true,
      navigation          : {
        nextEl : '.review-slider__button--next',
        prevEl : '.review-slider__button--prev',
      },
      roundLengths	      : true,
      slidesPerView       : 'auto', 
      slidesPerGroup      : 1,
      spaceBetween        : 10,
      breakpointsInverse  : true, 
      breakpoints         : { 
        1024: {
          slidesPerView   : 'auto', 
          spaceBetween    : 10,
          centeredSlides  : true,
        },
        1260: {
          slidesPerView   : 3.58,
          spaceBetween    : 40,
          centeredSlides  : false,
        },
      },
    });
  }



	// [Main] tab's handler
  var onTabHandler = function () {
    $(this).parent().addClass('is-active').siblings().removeClass('is-active');
  };



	// [PC] nav's mouseenter handler 
  function onEnterNav() {
    $nav.addClass('is-active');
    $(this).addClass('is-active').siblings().removeClass('is-active');
  }



	// [PC] nav's mouseleave handler
  function onLeaveNav() {
    $nav.removeClass('is-active');
    $(this).removeClass('is-active');
  }



	// [PC] nav-depth2-list 중앙 정렬
  function setNavDepth2ListPositionCenter(){
    // 웹폰트 로딩 때문
    // 2019-10-14 : FF, IE에서 제대로 로딩이 완료되지 않아 중앙 정렬되지 않는 이슈로 주석처리
    // window.onload = function(){
      var $navDepth2List = $('.nav-depth2__list');
      var $navDepth1ItemWidth = $navDepth1List.outerWidth() / $navDepth1Item.length;
      
      $navDepth2List.each(function(index, item){
        var biggerWidth = 0;
    
        function getBiggerWidth(children){
          $(item).find(children).each(function(index, item) {
            var currentItemWidth = $(item).width();
            if(biggerWidth <= currentItemWidth){
              biggerWidth = currentItemWidth;
            }
          });
          $(item).children().css({
            marginLeft: ($navDepth1ItemWidth - biggerWidth) / 2
          });
        }
    
        if($(item).has('.nav-depth3__list').length > 0){
          getBiggerWidth('.nav-depth3__link');
        }else{
          getBiggerWidth('.nav-depth2__link');
        }
      });
    // }
  }


  // [RWD-640] sub-nav-depth2__list toggle
  function toggleSubNavDepth2List(){
    var $subNavDepth1ItemIsOnlyDepth1 = $('.sub-nav-depth1__item.is-only-depth1');
    var $subNavDepth1ItemIsActive     = $('.sub-nav-depth1__item.is-active');

    $subNavDepth1ItemIsOnlyDepth1.on('mouseenter', function(){
      $subNavDepth1ItemIsActive.children('.sub-nav-depth2__list').css({visibility:'hidden'});
    });
    $subNavDepth1ItemIsOnlyDepth1.on('mouseleave', function(){
      $subNavDepth1ItemIsActive.children('.sub-nav-depth2__list').css({visibility:'visible'});
    });
  }



  // [RWD-1260] allmenu toggle
    $allmenu.on('click', function(){
      if($html.hasClass('is-mobile-nav-show')) {
        $html.removeAttr('class');
        $allmenu.find('span').text('전체 메뉴 닫기');
      }else {
        $html.removeAttr('class');
        $html.addClass('is-mobile-nav-show is-scroll-blocking');
        $allmenu.find('span').text('전체 메뉴 열기');
      }
    });



  // [RWD-640] search toggle
  function toggleSearch(){
    var $headerSearchField  = $('.header-search__field'),
        $headerSearchSubmit = $('.header-search__submit'),
        $headerSearchClose  = $('.header-search__close');

    $headerSearchSubmit.on('click', function(){
      if($(this).parents($html).hasClass('is-mobile-search-show')) return;
      $html.removeAttr('class');
      $html.addClass('is-scroll-blocking is-mobile-search-show');
    });
    $headerSearchClose.on('click', function(){
      $html.removeAttr('class');
    });
  }



	// footer-dropdown-menu toggle
	function toggleFooterDropdownMenu(){
		$footerDropdownMenu.toggleClass('is-active');
	}



  // [RWD-all] footerTopButton 위치 지정
  function fixedScroll(){
    var $footerTopButton   = $(".footer-top-button"),
        speed              = 300,
        scrollTopNav       = 40,
        scrollBottomFooter = $(document).height() - $window.height() - ($('.footer').height() / 2),
        timer, 
        $this;

    if(!timer) {
      timer = setTimeout(function(){
        timer = null;
        if($window.scrollTop() > scrollTopNav) {
          $footerTopButton.addClass('is-show');
        } else {
          $footerTopButton.removeClass('is-show');
        }

        if($window.scrollTop() >= scrollBottomFooter){
          $footerTopButton.addClass('is-fixed');
        }else{
          $footerTopButton.removeClass('is-fixed');
        }
      }, 50);
    }
  }



  // footerTopButton 클릭하면 페이지 상단으로 이동
  function moveTop() {
    $('html, body').animate({ scrollTop: 0 }, 100);
  }



	// modal
  function toggleModal(){
    var $wrap            = $('.wrap');
    var $buttonModalShow = '.js-modal-show';
    var $this, $id;
  
    $wrap.on('click', $buttonModalShow, function(e){
      $this = $(this);
      $id = $this.attr('href');
      openModal($id, $this);
    });
  
    function openModal(selector, $opener) {
      var $modal = $(selector);
      var $btnClose = $modal.find('.js-modal-close');

      $html.addClass('is-scroll-blocking');
      $modal.addClass('js-modal-show').attr('tabindex', 0).focus();
      $btnClose.on('click.layerClose', function(){
        closeModal(selector, $opener);
      });
      $modal.on('keydown.esc', function(e){
          if (e.which === 27) {
              $btnClose.trigger('click');
          }
      });
    }
    
    function closeModal(selector, $opener) {
      var $modal = $(selector);
      var $btnClose = $modal.find('.js-modal-close');

      $html.removeClass('is-scroll-blocking');
      $btnClose.off('click.layerClose');
      $modal.removeClass('js-modal-show').removeAttr('tabindex').off('keydown.esc');
      $opener.focus();
    }
  }



	// sub category-nav toggle
  function toggleCategoryNavHandler(){
    if(this === $categoryNavItem[0]){
      $(this).siblings().removeClass('is-select');
      $(this).toggleClass('is-select');
    } else if(this !== $categoryNavItem[0]) {
      $(this).toggleClass('is-select');
    }
  }



	// sub tab's handler
  function toggleTab($tab, $tabTarget, $tabButton, tabPanel, tabActiveClass) {
    $tab           = $tab || $('.tab__list');
    $tabTarget     = $tabTarget || $('.tab__item.is-active');
    $tabButton     = $tabButton || $('.tab__button');
    tabPanel       = tabPanel || '.tab__panel';
    tabActiveClass = tabActiveClass || 'is-active';

    return (function(){
      // 클릭 이벤트로 활성화 된 탭 높이를 지정
      var setTabHeight = function(){
        $(this).parent().addClass(tabActiveClass).siblings().removeClass(tabActiveClass);
      };

      $tabButton.on('click', setTabHeight);
    }());
  }



	// allcheckbox's toggle
  function toggleAllCheckbox($allCheckbox) {
    $allCheckbox.on('click', function (e){
      var $childrenCheckbox = $(this).closest('table').find('tbody input[type="checkbox"]');

      // 기본형 테이블의 체크박스일 때
      if($childrenCheckbox.length > 0) {
        controlAllCheckbox();
      // 고정형 테이블의 체크박스일 때
      }else{
        $childrenCheckbox = $(e.target).closest('table').parent().next().find('tbody input[type="checkbox"]');
        controlAllCheckbox();
      }

      function controlAllCheckbox(){
        if($allCheckbox.prev().prop('checked')) { 
          $childrenCheckbox.prop('checked', false); 
        } else { 
          $childrenCheckbox.prop('checked', true); 
        }
      }
    });
  }



  // 관심상품, 이용신청하기 버튼 토글
  function toggleDataUnitButton(){
    var $subContainer = $('.sub__container');
    $subContainer.on('click', '.data-unit__button', function(){
      $(this).toggleClass('is-active');
    });
  }



	// datepicker's options
  $.datepicker.setDefaults({
    dateFormat         : 'yy-mm-dd',
    showOtherMonths    : true,
    showMonthAfterYear : true,
    changeYear         : true,
    changeMonth        : true,
    buttonText         : "선택",
    yearSuffix         : "년",
    monthNamesShort    : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    monthNames         : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
    dayNamesMin        : ['일','월','화','수','목','금','토'],
    dayNames           : ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
  });



  function getWindowWidth(){
    var $windowWidth = $window.outerWidth();
    if($windowWidth >= 1260){
      setNavDepth2ListPositionCenter();
      toggleSubNavDepth2List();
      $nav.on('mouseenter', '.nav-depth1__item', onEnterNav);
      $nav.on('mouseleave', '.nav-depth1__item', onLeaveNav);
    } else {
      $nav.off('mouseenter');
      $nav.off('mouseleave');
    } 
    if($windowWidth <= 1024){
      toggleSearch();
    } 
  }
  
  
  
  // keyword
  function keyword(createCallback, deleteCallback, keywordList){
    var $keywordInputWrap = $('.keyword__input-wrap');
    var $keywordInput = $('.keyword__input');
    var $keyword = $('.keyword');
    
	if(keywordList && keywordList.length > 0){
		for(var i=0; i<keywordList.length; i++){
			var keyword = keywordList[i]; 
			$keywordInputWrap.before('<li class="keyword__item">' + keyword + '<button class="keyword__button--delete material-icons" type="button"><span class="hidden">삭제</span></button></li>');
		}
	}
    
    function createKeyword(e){
      if((e.keyCode === 13 || e.keyCode === 32 || e.keyCode === 188) && e.target.value != 0){
    	var inputKeyword = e.target.value.trim().replace(/,/g, '');
    	if(inputKeyword){
    		$keywordInputWrap.before('<li class="keyword__item">' + inputKeyword + '<button class="keyword__button--delete material-icons" type="button"><span class="hidden">삭제</span></button></li>');
    		/**
    		 * hk-lee 추가
    		 */
    		if($.isFunction(createCallback)){
    			createCallback(inputKeyword);
    		}
    	}
        e.target.value = '';
      }
    }

    function deleteKeyword(e){
	  /**
       * hk-lee 추가
       */
      var $btnDel = $(this);
      var $keywordChild = $btnDel.parent("li");
      var $keywordList = $(".keyword__list .keyword__item");
      var currentIndex = $keywordList.index($keywordChild);
      if($.isFunction(deleteCallback)){
    	  deleteCallback(currentIndex);
      }
      
      $keywordChild.remove();
    }
    
    
    $keyword.on('keyup', '.keyword__input', createKeyword);
    $keyword.on('click', '.keyword__button--delete', deleteKeyword);
    
  }

  
  
  // accordionBoard
  function accordionBoard(){
    var $accordionBoard      = $('.accordion-board');
    var $this;

    function accordionBoardHandler(){
      $this = $(this);
      $this.toggleClass('is-active').siblings('.accordion-board__title').removeClass('is-active').next('.accordion-board__text').css({'display':'none'});
      if($this.hasClass('is-active')){
        $this.next('.accordion-board__text').css({'display':'table-row'});
      } else {
        $this.next('.accordion-board__text').css({'display':'none'});
      }
    }
  
    $accordionBoard.on('click', '.accordion-board__title', accordionBoardHandler);
  }
  

  
  // eventListner
  $footerTopButton.on('click', moveTop);
  $footerDropdownMenuButton.on('click', toggleFooterDropdownMenu);
  $mainBestTabButton.on('click', onTabHandler);
  $mainEtcTabButton.on('click', onTabHandler);
  $window.on('scroll', fixedScroll);
  $nav.on('click', '.nav-depth1__item', function(){
    if($window.outerWidth() > 640) return;
    $(this).toggleClass('is-active').siblings().removeClass('is-active');
  });



  $(document).ready(function(){
    getWindowWidth();
  });
  $(window).resize(function(){
    getWindowWidth();
    toggleTab();
  });



  return {
    toggleTab            : toggleTab,
    toggleAllCheckbox    : toggleAllCheckbox,
    toggleDataUnitButton : toggleDataUnitButton,
    toggleModal          : toggleModal,
    setSwiperSlide       : setSwiperSlide,
    keyword              : keyword,
    accordionBoard       : accordionBoard,
  };
};
