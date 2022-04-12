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
          <ul class="pagination__list">\
            <li class="pagination__item" v-if="pager.curPage > 1">\
              <a class="pagination__nav pagination__nav--first" href="#first" @click.prevent="pageMove(1)">\
                <span class="hidden">처음으로</span>\
              </a>\
            </li>\
            <li class="pagination__item" v-if="pager.curPage > 1">\
              <a class="pagination__nav pagination__nav--prev" href="#prev" @click.prevent="pageMove(pager.curPage*1-1)">\
                <span class="hidden">이전</span>\
              </a>\
            </li>\
            <li class="pagination__item" v-for="p in page(totalPage)">\
              <a class="pagination__link" :class=\'{"pagination__link is-active" : pager.curPage == p+1}\' v-if="pager.curPage == p+1" href="#" @click.prevent>{{p+1}}</a>\
              <a class="pagination__link" v-else href="#" @click.prevent="pageMove(p+1)">{{p+1}}</a>\
            </li>\
	    	<li class="pagination__item" v-if="pager.curPage < totalPage">\
		      <a class="pagination__nav pagination__nav--next" href="#next" @click.prevent="pageMove(pager.curPage*1+1)">\
		        <span class="hidden">다음</span>\
		      </a>\
		    </li>\
	    	<li class="pagination__item" v-if="pager.curPage < totalPage">\
		      <a class="pagination__nav pagination__nav--last" href="#last" @click.prevent="pageMove(totalPage)">\
		        <span class="hidden">마지막으로</span>\
		      </a>\
	    	</li>\
          </ul>\
      </div>\
      '
  });
}
