var N2MFileUploader = {};
N2MFileUploader.install = function(Vue, options){ 
	Vue.component('n2m-file-uploader', {
	data: function(){
		return {
			files: [],
			allowExtensions: ["gif", "jpg", "png", "jpeg"]
		}
	},
	props: {
		savedFiles: null,
		single: null,
		onlyImg: null
	},
	methods: {
		clear: function(){
			const input = this.$refs.fileupload;
			input.type = 'text';
			input.type = 'file';
		},
		validateFile: function(files){
			var totalFileLength = this.files.length + this.savedFiles.length + files.length;
			
			if(this.single){
				if(totalFileLength > 1){
					alert("파일 첨부는 최대 1개까지 가능합니다.");
					return false;
				}
				
				if (this.onlyImg) {
					var file = files[0];
					var fileName = file.name;
					var ext = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();
					if(this.allowExtensions.indexOf(ext) === -1){
						alert("이미지 파일 첨부만 가능합니다.");
						return false;
					}
					return true;
				}
				
				this.files.push(files[0]);
			}else{
				if(totalFileLength > 5){
					alert("파일 첨부는 최대 5개까지 가능합니다.");
					return false;
				}
				for(var i=0; i<files.length; i++){
					var file = files[i];
					if(file.size > Math.pow(1024, 2) * 10){
						alert("파일 첨부는 1건 당 최대 10MB 이하만 가능합니다.");
						return false;
					}
					if (this.onlyImg) {
						var fileName = file.name;
						var ext = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();
						if(this.allowExtensions.indexOf(ext) === -1){
							alert("이미지 파일 첨부만 가능합니다.");
							return false;
						}
						return true;
					}
					this.files.push(files[i]);
				}
			}
			
			return true;
		},
		selectFile: function(e){
			var $this = this;
			var files = e.target.files;
			if(!$this.validateFile(files)) return;
			if($this.onlyImg){
				var _URL = window.URL || window.webkitURL;

				var imgError = false;
				var imgArr = [];
				for(var i=0; i<files.length; i++){
					var file = files[i];
					imgArr.push(new Image());
					imgArr[i].src = _URL.createObjectURL(file);
					imgArr[i].onload = function(){
						if(!imgError){
							if(this.width <= 150 && this.height <= 150){
								$this.files.push(file);
								$this.$emit('select-file', $this.files, $this.files.length > 0);
								$this.clear();
							}else{
								alert("가로 150px, 세로 150px 이하만 가능합니다.");
								imgError = true;
								$this.clear();
							}
						}
					}
				}
			}else{
				$this.$emit('select-file', files, $this.files.length > 0);
				$this.clear();
			}
		},
		deleteFile: function(index, id){
			if(id){
				this.$emit('delete-file', index, id);
			}else{
				this.files.splice(index, 1);
				this.$emit('delete-file', index);
			}
			this.clear();
		}
	},
	template: '\
	<div class="attach-file">\
		<label class="attach-file__button--add" for="upload">찾아보기 <input id="upload" class="input__file" type="file" :multiple="!single" @change="selectFile" ref="fileupload"></label>\
		<ul class="attach-file__list">\
			<li class="attach-file__item--none" v-if="files.length === 0 && savedFiles.length === 0">첨부파일이 없습니다.</li>\
			<li class="attach-file__item material-icons" v-for="(file, index) in files">\
				{{file.name}}<span class="attach-file__item--size">{{file.size | size}}</span>\
				<button type="button" class="attach-file__button--delete material-icons" title="파일 삭제" @click="deleteFile(index)">\
					<span class="hidden">파일 삭제</span>\
				</button>\
			</li>\
			<li class="attach-file__item material-icons" v-for="(file, index) in savedFiles" v-if="file.id">\
				{{file.name}}<span class="attach-file__item--size">{{file.size | size}}</span>\
				<button type="button" class="attach-file__button--delete material-icons" title="파일 삭제" @click="deleteFile(index, file.id)">\
					<span class="hidden">파일 삭제</span>\
				</button>\
			</li>\
		</ul>\
	</div>\
	'
	});
}
