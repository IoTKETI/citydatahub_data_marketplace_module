var N2MFileUploader = {};
N2MFileUploader.install = function(Vue, options){ 
	Vue.component('n2m-file-uploader', {
	data: function(){
		return {
			files: []
		}
	},
	props: {
		savedFiles: null,
		single: null
	},
	methods: {
		clear: function(){
			const input = this.$refs.fileupload;
			input.type = 'text';
			input.type = 'file';
		},
		selectFile: function(e){
			var files = e.target.files;
			if(!this.single && (this.files.length + this.savedFiles.length + files.length > 5 )){
				alert("파일 첨부는 최대 5개까지 가능합니다.");
				return;
			}else if(this.single){
				if(this.files.length + this.savedFiles.length + files.length > 1){
					alert("파일 첨부는 최대 1개까지 가능합니다.");
					return;
				}else{
					var fileName = files[0].name;
					var ext = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();
					if (!(ext == "gif" || ext == "jpg" || ext == "png" || ext == "jpeg")) {
						alert("이미지 파일 첨부만 가능합니다.");
						return;
					}else{
						this.files.push(files[0]);
					}
				}
			}else{
				var limit = false;
				for(var i=0; i<files.length; i++){
					var file = files[i];
					if(file.size > Math.pow(1024, 2) * 10){
						limit = true;
					}else{
						this.files.push(files[i]);
					}
				}
				if(limit){
					alert("파일 첨부는 1건 당 최대 10MB 이하만 가능합니다.");
					return;
				}
			}
			
			this.$emit('select-file', files);
			this.clear();
		},
		deleteFile: function(index, oid){
			if(oid){
				this.$emit('delete-file', index, oid);
			}else{
				this.files.splice(index, 1);
				this.$emit('select-file', this.files);
			}
			this.clear();
		}
	},
	template: '\
	<div>\
		<label class="button__file" for="upload">찾아보기 <input id="upload" class="input__file" type="file" :multiple="!single" @change="selectFile" ref="fileupload"></label>\
		<ul class="file__list">\
			<li class="file__item file__item--none" v-if="files.length === 0 && savedFiles.length === 0">첨부파일이 없습니다.</li>\
			<li class="file__item" v-for="(file, index) in files">\
				{{file.name}} ({{file.size | size}})\
				<button type="button" class="button__file-delete material-icons" title="파일 삭제" @click="deleteFile(index)">\
					<span class="hidden">파일 삭제</span>\
				</button>\
			</li>\
			<li class="file__item" v-for="(file, index) in savedFiles">\
				{{file.name}} ({{file.size | size}})\
				<button type="button" class="button__file-delete material-icons" title="파일 삭제" @click="deleteFile(index, file.oid)">\
					<span class="hidden">파일 삭제</span>\
				</button>\
			</li>\
		</ul>\
	</div>\
	'
	});
}
