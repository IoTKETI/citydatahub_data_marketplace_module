//의존성
//jquery
//serializeObject.js
//validate.js
//순서
//1. serialize
//2. constraints 수정
//3. validate
//4. action
var Valid = (function($, validate){
	var params={};
	var constraints={};
	var valid={};
	var vm = {};
	return {
		init: function(vm, vueObj){
			this.params = vueObj;
			this.constraints={};
			this.vm = vm;
			for(var key in this.params){
				this.constraints[key]={};
				var $elem = $(this.vm.$refs[key]);
				if($elem.prop('required')){
					this.constraints[key].presence={};
				}
				
			}
			//this.constraints['valid_manual']={presence:{message:"^메시지를 정의해주세요."}};
			//console.log('Object[constraints] === ', this.constraints);
			return this;
		},
		validate: function(c){
			if(!c){ throw '제약조건을 입력해주세요'; }
			this.valid = validate(this.params, c);
			
			if(this.valid){	//유효성 판단
				var elem = "";
				var msg = "";
				var result = {}; 
				for(var key in this.valid){
					elem = key;
					result = this.valid[key][0];
					if($.isFunction(result.callback)){
						result.callback();
					}else{
						msg = this.valid[key][0];
						var $elem = $(this.vm.$refs[elem]);
						var label = $elem.attr('title');
						alert('['+ label + '] 은(는) ' + msg);			//유효성 메시지
						$elem.focus();
					}
					break;
				}
			}
			return this.valid;
		},
		getConstraints: function(){
			return this.constraints;
		},
		getParams: function(){
			return this.params;
		},

	}
})(jQuery, validate);

validate.validators.custom = function(value, options, key, attributes) {
    if(!value || value == "<p><br></p>"){
    	return {
    	    callback: function(){
		        alert('['+ options.label + '] 은(는) ' + options.message);				    	    	
		        oEditors.getById[options.targetId].exec("FOCUS"); //포커싱
    	    }
        };
    } else {
			return undefined;
    }
};