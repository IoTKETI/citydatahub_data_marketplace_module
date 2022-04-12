var N2MPagination = {};
N2MPagination.install = function(Vue, options){ 
  Vue.component('n2m-pagination', {
    props: {
      pager   : null
    },
    methods: {
      page: function (totalPage) {
    	var pages=[];
    	var startBlock = Math.floor((this.pager.curPage - 1) / this.pager.blockSize) * this.pager.blockSize;
    	var endBlock = startBlock + this.pager.blockSize > totalPage ? totalPage : startBlock + this.pager.blockSize; 
    	for(var i = startBlock; i < endBlock; i++){
    		pages.push(i);
    	}
    	
    	if(pages.length === 0) pages.push(0);
    	
        return pages;
      },
      pageMove: function(page){
        this.$emit("pagemove", page);
      }
    },
    computed: {
      totalPage: function(){
        return this.pager.totalListSize % this.pager.pageListSize == 0 ? Math.floor(this.pager.totalListSize / this.pager.pageListSize) : Math.floor(this.pager.totalListSize / this.pager.pageListSize) + 1;
      }
    },
    template: '<div>\
        <nav class="pagination">\
          <ul class="pagination__list">\
            <li class="pagination__item" v-if="pager.curPage > 1">\
              <a class="pagination__link pagination__link--first" href="#first" @click="pageMove(1)">\
                <span class="hidden">처음으로</span>\
              </a>\
            </li>\
            <li class="pagination__item" v-if="pager.curPage > 1">\
              <a class="pagination__link pagination__link--prev" href="#prev" @click="pageMove(pager.curPage-1)">\
                <span class="hidden">이전</span>\
              </a>\
            </li>\
            <li class="pagination__item" v-for="p in page(totalPage)">\
              <a class="pagination__link" :class=\'{"pagination__link--active" : pager.curPage == p+1}\' v-if="pager.curPage == p+1" href="#">{{p+1}}</a>\
              <a class="pagination__link" v-else href="#" @click="pageMove(p+1)">{{p+1}}</a>\
            </li>\
	    	<li class="pagination__item" v-if="pager.curPage < totalPage">\
		      <a class="pagination__link pagination__link--next" href="#next" @click="pageMove(pager.curPage+1)">\
		        <span class="hidden">다음</span>\
		      </a>\
		    </li>\
	    	<li class="pagination__item" v-if="pager.curPage < totalPage">\
		      <a class="pagination__link pagination__link--last" href="#last" @click="pageMove(totalPage)">\
		        <span class="hidden">마지막으로</span>\
		      </a>\
	    	</li>\
          </ul>\
        </nav>\
    	<p class="text__total">총 {{pager.totalListSize ? pager.totalListSize : 0}}건</p>\
      </div>\
      '
  });
}
