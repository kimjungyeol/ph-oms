;(function(g, trigger) {
    console.log('context.function load!!');
    
    function validation() {
        if (g.contants.uriMap.search == '') {
            return false;
        }
        return true;
    }
    
    /**
	 * api  : defulat transaction management.
	 * move : page move management.
	 * form : html form data management.
	 * file : html attach file management.
	 */
    g.function = {
        
        /**
         * defulat transaction management.
         *   - search, save, saveForm
         */
        api : {
            search : function(opt = {}, callbackFnc = null) {
                console.log('call -> ' + opt.uri);
                
                if (!validation()) {
                    return;
                }
                
                $.ajax({
                    url: opt.uri,
                    method: "post",
                    dataType: "json",
                    data: JSON.stringify(opt.params),
                    success: function(result) {
                        
                        console.log('search result!!', result);
                        
                        if (callbackFnc != null && typeof callbackFnc == 'function') {
                            callbackFnc(result);
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        alert('search Eerror occurred.');
                        
                        console.log(jqXHR, textStatus, errorThrown);
                    }
                });
                
            },
            save : function(opt = {}, callbackFnc = null) {
                console.log('call -> ' + opt.uri);
                
                let saveParams = {};
                
                //append form data.
                let formData = g.function.form.getData();
                Object.keys(formData).forEach(function(key) {
					Object.assign(saveParams, formData);
				});
				
				//append opt.params data.
				if (opt.params != undefined && Object.keys(opt.params).length > 0) {
					Object.assign(saveParams, opt.params);
				}
				
				console.log('saveParams===',saveParams);
                
                $.ajax({
                    url: opt.uri,
                    method: "post",
                    dataType: "json",
                    data: JSON.stringify(saveParams),
                    success: function(result) {
                        
                        console.log('save result!!', result);
                        
                        if (callbackFnc != null && typeof callbackFnc == 'function') {
                            callbackFnc(result);
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        alert('search Eerror occurred.');
                        
                        console.log(jqXHR, textStatus, errorThrown);
                    }
                });
            },
            saveForm : function(opt = {}, callbackFnc = null) {
				const self = this;
				
                console.log('call -> ' + opt.uri);
                
                //get file data.
                let fileDataForm = g.function.file.getFileData();
                if (fileDataForm == null) {
					return;
				}
                
                //append html form data.
                let formData = g.function.form.getData();
                Object.keys(formData).forEach(function(key) {
					fileDataForm.append(key, JSON.stringify(formData[key]));
				});
				
				//append opt.params data.
				if (opt.params != undefined && Object.keys(opt.params).length > 0) {
	                Object.keys(opt.params).forEach(function(key) {
						fileDataForm.append(key, JSON.stringify(opt.params[key]));
					});
				}
                
                $.ajax({
					url: opt.uri,
					processData : false,
					contentType : false,
					data : fileDataForm,
					type : 'POST',
					success : function(response){
						console.log('uploaded!!', response);
					},
					error: function(jqXHR, textStatus, errorThrown) {
                        console.log(jqXHR, textStatus, errorThrown);
                    }
				})
				.done(function (response, textStatus, xhr) {
                    console.log('done response', response);
                    //console.log('done xhr', xhr);
                    
                    if (response.status == 500) {
						alert(response.reason);
						return;
					}
                    
                    /*if(data.result == "1"){
                        alert("success!");
                    } else {
                        alert("에러발생["+data.result+"]");
                        console.log(data.result_msg);
                        callback(data);
                    }*/
                    
                    if (fileDataForm != null) {
	                    g.function.file.reload();
					}
                    
                    if (callbackFnc != null && typeof callbackFnc == 'function') {
                        callbackFnc(response.data);
                    }
                })
                .fail(function(data, textStatus, errorThrown){
                    console.log("fail in get addr");
                });
            }
        }, // api {}
        
        /**
         * browser sesstionStorage mangement. 
         */ 
        storage : {
            movePrefix : 'moveParams::',
            removeParams : function(uri) {
				let key = this.movePrefix + uri;
                sessionStorage.removeItem(key);
			},
            setMoveParams : function(uri, params = {}) {
                let key = this.movePrefix + uri;
                
                sessionStorage.removeItem(key);
                sessionStorage.setItem(key, JSON.stringify(params));
            },
            getMoveParams : function() {
                let uri = location.pathname;
                let key = this.movePrefix + uri;
                
                let moveParams = sessionStorage.getItem(key);
                moveParams = moveParams == null ? {} : JSON.parse(moveParams);
                
                return moveParams;
            }  
        }, // storage {}
        
        /**
		 * page move management.
		 */
        move : {
	        page : function(uri, params = null) {
	            console.log('call -> ' + uri);
	            
	            if (!uri.startsWith('/')) {
	                uri = '/' + uri;
	            }
	            
	            g.function.storage.removeParams(uri);
	            
	            window.location.href = uri;
	        },
	        detail : function(uri, params = null) {
	            console.log('call -> ' + uri);
	            
	            if (!uri.startsWith('/')) {
	                uri = '/' + uri;
	            }
	            
	            if (params != null) {
	                //delete not use param.
	                Object.keys(params).forEach(function(key) {
	                    if (key.includes('_') || key == 'rowSpanMap' || key == 'uniqueKey') { 
	                        delete params[key];                    
	                    }
	                });
	                
	                g.function.storage.setMoveParams(uri, params);
	            }
	            
	            window.location.href = uri;
	        },
	        form : function(uri, params = null) {
	            console.log('call -> ' + uri);
	            
	            let id = "_moveForm_";
	            let mform = document.getElementById(id);
	            if (mform != null) {
	                mform.remove();
	            }
	            
	            var form = document.createElement("form");
	            form.setAttribute("charset", "UTF-8");
	            form.setAttribute("method", "GET");
	            form.setAttribute("id", id);
	            form.setAttribute("action", uri); 
	            
	            if (params != null) {
	                sessionStorage.setItem("moveParams", params);
	            }
	    
	            document.body.appendChild(form);
	            form.submit();
	        },
		}, // move
		
		/**
		 * html form data management.
		 */
		form : {
	        /**
	         * Setting data on the form.
	         *  - Based on 'data' parameter key value.
	         */
	        setData : function(data = {}) {
	            if (Object.keys(data).length == 0) {
	                return;
	            }
	            
	            function getEle(key) {
	                let obj = document.querySelectorAll(`[id="${key}"]`);
	
	                if (obj.length > 1) {
	                    console.log('== There is more than one id ==');
	                    console.log(`[id="${key}"] length =>> ${obj.length}`);
	                }
	                
	                if (obj.length == 0) {
	                    obj = document.querySelectorAll(`[name="${key}"]`);
	                } 
	                
	                return obj[0];
	            }
	            
	            Object.keys(data).forEach(function(key) {
	                let ele = getEle(key);
	                if (ele == undefined) {
	                    return; //continue.
	                }
	                
	                let tagName = ele.tagName.toLowerCase();
	                if (tagName === 'input') {
	                    if (ele.type.toLowerCase() == 'radio') {
	                         const raiodObjs = document.querySelectorAll(`[name="${key}"]`);
	                         raiodObjs.forEach((ele) => {
	                             let value = ele.value;
	                             if (value == data[key]) {
	                                 ele.checked = true;
	                             }
	                         });
	                    } else {
	                        ele.value = data[key];
	                    }
	                } else {
	                    if (ele.value != undefined) {
	                        ele.value = data[key];
	                    } else {
	                        ele.innerHTML = data[key];
	                    }
	                }
	            });
	        },
	        /**
	         * Get parameters of dom in 'data-form' attribute.
	         */
	        getData : function() {
	            let formParams = {};
	            
	            function getChildNodeData(fele) {
	                if (fele.nodeName.includes('text')) {
	                    return;
	                }
	                
	                const childNodes = fele.childNodes;
	                if (childNodes != undefined) {
	                    childNodes.forEach((ele) => {
	                        getChildNodeData(ele);
	                    });
	                }
	                
	                let tagName = fele.tagName.toLowerCase();
	                if (tagName == 'option') {
	                    return;
	                }
	                 
	                 if (tagName === 'input') {
	                     if (fele.type.toLowerCase() == 'radio') {
	                         if (formParams[fele.name] != undefined) {
	                             return;
	                         }
	                         const raiodObjs = document.querySelectorAll(`[name="${fele.name}"]`);
	                         raiodObjs.forEach((ele) => {
	                             if (ele.checked) {
	                                 formParams[ele.name] = ele.value;
	                             }
	                         });
	                     } else {
	                         formParams[fele.id] = fele.value;
	                     }
	                 } else {
	                     if (fele.value != undefined) {
	                         formParams[fele.id] = fele.value;
	                     }
	                 }
	            }
	            
	            // target form object.
	            let triggerFormEle = document.querySelectorAll('[data-trigger="form"]');
	            triggerFormEle.forEach((form) => {
	                form.childNodes.forEach((fele) => {
	                    getChildNodeData(fele);
	                });
	            });
	            
	            return formParams;
	        },
		}, // form
        
        /**
		 * html attach file management.
		 * create target : <div data-file="xxxx"></div>
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
			 * HTML with data-file attribute create an file area
 			 *   - data-file="user"
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
				
				let dataFileEleArr = document.querySelectorAll('[data-file]');
				dataFileEleArr.forEach((dataFileEle) => {
					dataFileEle.innerHTML = self.dom.area;
					let fileId = dataFileEle.getAttribute('data-file');
					
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
				let dataFileEle = document.querySelectorAll('[data-file]');
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
						formData.append(`${key}_atchFile`, self.loadParam[key]);
					});
				}
				
				return formData;
			},
			/**
			 * searched file info.
			 */
			loadParam : {},
			/**
			 * search file list.
			 */
			load : function(params = {}) {
				const self = this;
				
				if (params == null || params == undefined) {
					return;
				}
				
				self.init();
				self.loadParam = params;
				
				Object.keys(params).forEach(function(key) {
					self.view(key, params[key]);
				});
			},
			/**
			 * view file list after save.
			 */
			reload: function() {
				const self = this;
				self.load(self.loadParam);
			},
			/**
			 * view data file list.
			 */
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
		} // file {}
    }
    
    wg.f = g.function;
    
    console.log("============= wg.f =============", wg.f); 
    
})(window.global, wg.t);
