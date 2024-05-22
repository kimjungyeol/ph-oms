;(function(g, trigger) {
    /**
	 * file : html attach file management.
	 * datepicker : html datepicker management.
	 */
    g.component = {
        /**
		 * html attach file management.
		 * create target : <div data-component-file="xxxx"></div>
		 */	
        file : {
			uri : {
				search: '/file/attach/search',
				download: '/file/attach/down'
			},
			dom : {
				area: `<div class="mb-3">
		                  <input type="file" class="form-control" id="" name="" multiple="multiple">
		               </div>
		               <div id="viewfile"></div>
		               <div id="fileTxt"></div>
		              `,
		        select: `<span style="color: $color">
							$name ($size)
					     </span>
					    `,
				view: `<div>
				      	  <span style="cursor: pointer;color: $color" id="down" data-no="$no">
				      	  	$name ($size)
				      	  </span>
				      	  <span style="cursor: pointer;" id="del" data-no="$no">
				      	  	[X]
				      	  </span>
				      </div>
				      `,
            },
            deleteData: {
				name: 'deleteFileList',
				list: []
			},
	        validate : {
				fSExt: ['Bytes', 'KB', 'MB', 'GB', 'TB'],
				maxLength: 5,
				maxFileSize: (1024 * 1024) * 50,   //Mbytes
				fileSize: (1024 * 1024) * 10,      //Mbytes
				getFileSizeStr: function(bytes) {
					if (bytes == 0) { return '0 Bytes'; }
					
					const i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)), 10);
					if (i === 0) {
						return `${bytes} ${this.fSExt[i]})`;
					}
					
					return `${(bytes / (1024 ** i)).toFixed(1)} ${this.fSExt[i]}`;
				},
				getAllowFileSizeStr: function(bytes) {
					if (bytes == 0) { return '0 Bytes'; }
					
				    let i = 0;
				    while (bytes > 900) {
				        bytes /= 1024;
				        i++;
				    }
				    
				    return (Math.round(bytes * 100) / 100) + ' ' + this.fSExt[i];
				},
				checkFileSize: function(chkFileSize, type = '') {
				    let check = false;
				    let sizeinbytes = chkFileSize;
				    let fSize = sizeinbytes;
				    
				    let fileSize = this.fileSize;
				    if (type == 'ALL') {
						fileSize = this.maxFileSize;
					}
				    
				    if (fSize > fileSize) {
				        alert("Please attach files no larger than " + this.getAllowFileSizeStr(fileSize));
				        //TODO swal.
				        check = false;
				    } else {
				        check = true;
				    }
				    
				    return check;
				}
			},
			init: function() {
				const self = this;
				
				Object.keys(self.loadParam).forEach(function(key) {
					let fileEle = document.querySelectorAll(`[id="${key}_atchFile"]`);
					let viewFileEle = document.querySelectorAll(`[id="${key}_viewfile"]`);
					let fileTxtEle = document.querySelectorAll(`[id="${key}_fileTxt"]`);
					fileEle.forEach((ele) => {
						ele.value = '';
					});
					viewFileEle.forEach((ele) => {
						ele.innerHTML = '';
					});
					fileTxtEle.forEach((ele) => {
						ele.innerHTML = '';
					});
				});
				
				self.loadParam = {};
				self.deleteData.list = [];
			},
			/**
			 * HTML with data-component-file attribute create an file area
 			 *   - data-component-file="user"
			 */
			render: function(id = 'fileId') {
				const self = this;
				
				//file area render.
                function setFileHtml(fileEle, fileTxtEle) {
					if (fileEle.files.length == 0) { return; }
					
					let files = fileEle.files;
					let fileTxt = '';
					for (let i=0; i<files.length; i++) {
						if (i > 0) {
							fileTxt += '<br>';
						}
						let color = '#000'; 
						let isChkSize = self.validate.checkFileSize(files[i].size);
						if (!isChkSize) {
							color = '#dc3545';
						}
						
						let fileArea = self.dom.select;
						fileArea = fileArea.replace('$color', color);
						fileArea = fileArea.replace('$name', files[i].name);
						fileArea = fileArea.replace('$size', self.validate.getFileSizeStr(files[i].size));
						
						fileTxt += fileArea;
					}
					fileTxtEle.innerHTML = fileTxt;
				}
				
				let dataFileEleArr = document.querySelectorAll('[data-component-file]');
				dataFileEleArr.forEach((dataFileEle) => {
					dataFileEle.innerHTML = self.dom.area;
					let fileId = dataFileEle.getAttribute('data-component-file');
					
					let fileEle = dataFileEle.querySelector(`[type="file"]`);
					fileEle.id = fileId + '_atchFile';
					fileEle.name = fileId + '_atchFile';
					
					let viewFileEle = dataFileEle.querySelector(`[id="viewfile"]`);
					viewFileEle.id = fileId + '_viewfile';
					
					let fileTxtEle = dataFileEle.querySelector(`[id="fileTxt"]`);
					fileTxtEle.id = fileId + '_fileTxt';
					
	                //view string - fileName(fileSize)
	                fileEle.onchange = function() {
						setFileHtml(fileEle, fileTxtEle);
					}
				});
			},
			/**
			 * return new file object info, delete file info.
			 */
			getFileData: function() {
				const self = this;
				let formData = new FormData();
				
				//append new file info.
				let dataFileEle = document.querySelectorAll('[data-component-file]');
				dataFileEle.forEach((ele) => {
	                let fileEle = ele.querySelector(`[type="file"]`);
	                let fileId = fileEle.id;
	                
					let files = fileEle.files;
					//console.log(`getFileData '${fileId}' === `, files);
					
					if (files.length == 0) {
						return formData;
					}
					
					//1.Check each file size.
					let isChkSize = true;
					let allFileSize = 0;
					for (let i=0; i<files.length; i++) {
						if (!isChkSize) {
							continue;
						}
						formData.append(fileId+'[]', files[i]);
						
						allFileSize += files[i].size;
						isChkSize = this.validate.checkFileSize(files[i].size);
					}
					
					//2.Check all file size.
					if (isChkSize) {
						isChkSize = this.validate.checkFileSize(allFileSize, 'ALL');
					}
					if (!isChkSize) {
						formData = null;
					}
                });
                
                //append delete file info.
                if (self.deleteData.list.length > 0) {
	                formData.append(self.deleteData.name, JSON.stringify(self.deleteData.list));
				}
				
				//append loaded attached fileId.
				if (Object.keys(self.loadParam).length > 0) {
					Object.keys(self.loadParam).forEach((key) => {
						let fileId = self.loadParam[key] == undefined || self.loadParam[key] == null ? "" : self.loadParam[key];
						formData.append(`${key}_atchFile`, fileId);
					});
				}
				
				return formData;
			},
			//searched file info.
			loadParam : {},
			//search file list.
			load : function(params = {}) {
				const self = this;
				
				if (params == null || params == undefined) {
					return;
				}
				
				self.init();
				self.loadParam = params;
				
				Object.keys(params).forEach(function(key) {
					let fileId = params[key] == undefined || params[key] == null ? '' : params[key];
					self.view(key, fileId);
				});
			},
			//view file list after save.
			reload: function() {
				const self = this;
				self.load(self.loadParam);
			},
			//view data file list.
			view : function(fileIdNm = '', fileId = '') {
				const self = this;
				
                if (fileIdNm == '' || fileId == '') {
                    return;
                }
                
                //file area render.
                function setViewFileHtml(fileList = []) {
					if (fileList == null || (fileList != null && fileList.length == 0)) { return; }
					
					let fileTxt = '';
					let viewFileEle = document.querySelector(`[id="${fileIdNm}_viewfile"]`);
					for (let i=0; i<fileList.length; i++) {
						let color = '#000'; 
						let viewFile = self.dom.view;
						viewFile = viewFile.replace('$color', color);
						viewFile = viewFile.replace('$name', fileList[i].fileOrignNm);
						viewFile = viewFile.replace('$size', self.validate.getFileSizeStr(fileList[i].fileSize));
						viewFile = viewFile.replaceAll('$no', i);
						
						fileTxt += viewFile;
					}
					viewFileEle.innerHTML = fileTxt;
					
					//download file event.
					let downEle = viewFileEle.querySelectorAll('[id="down"]');
					downEle.forEach(function(ele) {
						ele.onclick = function() {
							let idx = ele.getAttribute("data-no");
							let f = btoa(fileList[idx].fileId);
							let s = btoa(fileList[idx].fileSn);
							
							location.href = `${self.uri.download}?f=${f}&s=${s}`;
						}
					});
					
					//delete element event.
					let delEle = viewFileEle.querySelectorAll('[id="del"]');
					delEle.forEach(function(ele) {
						ele.onclick = function() {
							let idx = ele.getAttribute("data-no");
							self.deleteData.list.push(fileList[idx]);
							
							ele.parentNode.remove();
						}
					});
				}
                
                $.ajax({
                    url: self.uri.search,
                    method: "post",
                    dataType: "json",
                    data: JSON.stringify({fileId : fileId}),
                    success: function(result) {
                        setViewFileHtml(result.dataList);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                    }
                });
            },
		}, // file {}
		
		/**
		 * datepicker component mangement.
		 *   - https://nhn.github.io/tui.date-picker/latest/tutorial-example08-daterangepicker 
		 *   - create target
		 * 		<div data-component-datepicker="basic" id="[basicId]"></div>
		 * 		<div data-component-datepicker="range" id="[rageId]"></div>
		 */
		datepicker: {
			tui: {
				context: {
					basic: {},
					range: {}
				},
				//screen 아래쪽에 있는경우 위치 조정.
				renderLocationChange: function(type) {
					const tui = this;
					const clientRect = tui.context[type][0].getBoundingClientRect(); // DomRect 구하기 (각종 좌표값이 들어있는 객체)
					const relativeTop = clientRect.top; // Viewport의 시작지점을 기준으로한 상대좌표 Y 값.
					const topVariableH = 290;
					
					const targetClass = {
						basic: '[class="tui-datepicker tui-hidden"]',
						range: '[class="tui-datepicker tui-hidden tui-rangepicker"]'
					}
					
					let ele = document.querySelectorAll(targetClass[type]);
					
					ele.forEach(function(el) {
						el.style['zIndex'] = '9999';
						
						if (window.innerHeight < (relativeTop + topVariableH)) {
							//let marginTop = window.screen.availHeight - relativeTop.toFixed() + 34;
							let marginTop = 305;
							el.parentNode.style['marginTop'] = '-' + marginTop + 'px';
						}
					});
				},
				renderHtml: function(target) {
					const tui = this;
					tui.context[target].forEach(function(ele) {
						let id = ele.id;
						let width = ele.style.width;
						
						let h = tui[target].dom();
						ele.innerHTML = h.replaceAll(tui[target].replaceId, `${id}`).replaceAll(tui[target].replaceWidth, width);
						ele.id = '';
						ele.style.removeProperty('width');
						
						tui[target].load(id);
					});
				},
				//all tui datepicker render.
				render: function() {
					const tui = this;
					tui.context.basic = document.querySelectorAll('[data-component-datepicker="basic"]');
					if (tui.context.basic.length > 0) {
						tui.basic.render();
					}
					tui.context.range = document.querySelectorAll('[data-component-datepicker="range"]');
					if (tui.context.range.length > 0) {
						tui.range.render();
					}
				},
				
				//basic tui datepicker
				basic: {
					replaceId: '$pickerid',
					replaceWidth: '$width',
					dom: function() {
						return `<div class="tui-datepicker-input tui-datetime-input tui-has-focus" style="width:${this.replaceWidth}">
					              <input type="text" id="${this.replaceId}">
					              <span class="tui-ico-date"></span>
					            </div>
					            <div id="${this.replaceId}_wrapper" style="margin-top: -1px;"></div>`;
					},
			        load: function(id) {
						let dateFormat = 'yyyy-MM-dd';
						if (global.lang == 'EN') {
							dateFormat = 'MM-dd-yyyy';
						}
						let datepicker = new tui.DatePicker(`#${id}_wrapper`, {
			                //date: new Date(),  //default date.
			                input: {
			                    element: `#${id}`,
			                    format: dateFormat
			                },
			                timePicker: false,
					        language: 'en',  //calendar text language.
			            });
					},
		            render: function() {
						g.component.datepicker.tui.renderHtml('basic');
						g.component.datepicker.tui.renderLocationChange('basic');
					}
				}, // basic
				
				//rage tui datepicker
				range: {
					replaceId: '$pickerid',
					dom: function() {
						return `<div class="tui-datepicker-input tui-datetime-input tui-has-focus" style="width:${this.replaceWidth}">
						            <input type="text" id="start_${this.replaceId}">
						            <span class="tui-ico-date"></span>
						            <div id="start_${this.replaceId}_container" style="margin-left: -1px;"></div>
						        </div>
						        <span>~</span>
						        <div class="tui-datepicker-input tui-datetime-input tui-has-focus" style="width:${this.replaceWidth}">
						            <input id="end_${this.replaceId}" type="text">
						            <span class="tui-ico-date"></span>
						            <div id="end_${this.replaceId}_container" style="margin-left: -1px;"></div>
						        </div>`;
					},
					load: function(id) {
						let dateFormat = 'yyyy-MM-dd';
						if (global.lang == 'EN') {
							dateFormat = 'MM-dd-yyyy';
						}
						
						//var today = new Date();
					    var picker = tui.DatePicker.createRangePicker({
					        startpicker: {
					            //date: today,
					            input: `#start_${id}`,
					            container: `#start_${id}_container`
					        },
					        endpicker: {
					            //date: today,
					            input: `#end_${id}`,
					            container: `#end_${id}_container`
					        },
					        format: dateFormat,
					        timePicker: false,
					        language: 'en',
					        /*
					        selectableRanges: [
		                        [
		                            new Date(2019, 3, 1),
		                            new Date(2019, 5, 1)
		                        ],
		                        [
		                            new Date(2019, 3, 1),
		                            new Date(2019, 10, 5)
		                        ]
		                    ]
		                    */
					    });
					
					    picker.on('change:start', () => {
					        //console.log('start date change.');
					    });
					    picker.on('change:end', () => {
					        //console.log('end date change.');
					    });
					},
					render: function() {
						g.component.datepicker.tui.renderHtml('range');
						g.component.datepicker.tui.renderLocationChange('range');
					}
				}// range
			}// tui
		}// datepicker {}
    }
    
    wg.cpnt = g.component;
    
    console.log("=== wg.cpnt (component) ===", wg.cpnt); 
    
})(window.global, wg.t);
