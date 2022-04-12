Vue.filter('size', function(value){
	var result = "";
	var UNITS = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
	var STEP = 1024;
	
	function insertComma(value){
		var parts = value.toString().split(".");
	    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	    return parts.join(".");
	}
	function format(value, power) {
	    return (value / Math.pow(STEP, power)).toFixed(2) + UNITS[power];
	}
	function read(value) {
        value = parseFloat(value, 10);
        for (var i = 0; i < UNITS.length; i++) {
            if (value < Math.pow(STEP, i)) {
                if (UNITS[i - 1]) {
                    return format(value, i - 1);
                }
                return value + UNITS[i];
            }
        }
        return format(value, i - 1);
	}
	result = read(value);	
	return insertComma(result); 
});
Vue.filter('flag', function(value){
  return value ? "예" : "아니오";
});
Vue.filter('date', function(value){
  return moment(value).format('YYYY-MM-DD HH:mm:ss');
});
Vue.filter('mainDateYM', function(value){
	  return moment(value).format('YYYY.MM');
});
Vue.filter('mainDateD', function(value){
	  return moment(value).format('DD');
});
Vue.filter('enter', function(value){
  return value ? value.replace(/(?:\r\n|\r|\n)/g, '<br />') : "";
});
Vue.filter('instance', function(value){
	if(!value) return "";
	var valueArr = value.split(":");
	return valueArr[valueArr.length - 1];
});
Vue.filter('comma', function (value){
	var parts = value.toString().split(".");
    parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    return parts.join(".");
});
/**
 * <pre>만족도 평가 점수 반올림</pre>
 * @Author      : hk-lee
 * @Date        : 2019. 11. 7.
 * @param value
 * @returns
 */
Vue.filter('satisfaction', function(value){
	if(value < 0.5){
		value = 0;
	}else if(value < 1){
		value = 0.5;
	}else if(value < 1.5){
		value = 1;
	}else if(value < 2){
		value = 1.5;		
	}else if(value < 2.5){
		value = 2;
	}else if(value < 3){
		value = 2.5;
	}else if(value < 3.5){
		value = 3;
	}else if(value < 4){
		value = 3.5;
	}else if(value < 4.5){
		value = 4;
	}else if(value < 5){
		value = 4.5;
	}else{
		value = 5;
	}
	return value;
});