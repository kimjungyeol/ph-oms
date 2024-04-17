;(function(g, trigger) {
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
	 * datepicker : html datepicker management.
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
					
					alert('Success save.');
                    
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
    }
    
    wg.f = g.function;
    
    console.log("=== wg.f (function) ===", wg.f); 
    
})(window.global, wg.t);
