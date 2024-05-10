;(function(g, trigger) {
    function validation(opt) {
        if (opt.uri == '') {
            return false;
        }
        return true;
    }
    
    /**
	 * api  : defulat transaction management.
	 * move : page move management.
	 * form : html form data management.
	 * msg  : alert, confirm common message mangement.
	 * popup : layer, window popup management.
	 */
    g.function = {
        /**
         * defulat transaction management.
         *   - search, save, saveForm
		 *   - change to json stringify if use 'post' data.
         */
        api : {
            search : function(opt = {}, callbackFnc = null) {
                console.log('call -> ' + opt.uri);
                
                if (!validation(opt)) {
                    return;
                }
                
                if (opt.params == undefined || typeof opt.params != 'object') {
					opt.params = {};
				}
                
                $.ajax({
                    url: opt.uri,
                    method: "post",
                    dataType: "json",
                    contentType: 'application/x-www-form-urlencoded',
                    data: JSON.stringify(opt.params),
                    success: function(result) {
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                    }
                })
                .done(function (response, textStatus, xhr) {
                    console.log('done response', response);
                    
                    if (response.status == 500) {
						swal(response.reason, "You clicked the button!", "warning");
						return;
					}
                    
                    if (callbackFnc != null && typeof callbackFnc == 'function') {
                        callbackFnc(response);
                    }
                })
                .fail(function(data, textStatus, errorThrown) {
                    swal("server Error occurred!", "You clicked the button!", "warning");
                });
                
            },
            save : function(opt = {}, callbackFnc = null) {
				const msg = g.function.msg;
				
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
				
                $.ajax({
                    url: opt.uri,
                    method: "post",
                    dataType: "json",
                    contentType: 'application/x-www-form-urlencoded',
                    data: JSON.stringify(saveParams),
                    success: function(result) {
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                    }
                })
                .done(function (response, textStatus, xhr) {
                    console.log('done response', response);
                    
                    if (response.status == 500) {
	                    swal(response.reason, "server Error occurred!", "error");
						return;
					}
                    msg.alert.successSave();
                    
                    if (callbackFnc != null && typeof callbackFnc == 'function') {
                        callbackFnc(response);
                    }
                })
                .fail(function(data, textStatus, errorThrown) {
                    msg.alert.error();
                });
            },
            saveForm : function(opt = {}, callbackFnc = null) {
				const self = this;
				const msg = g.function.msg;
				
                console.log('call -> ' + opt.uri);
                
                //get file data.
                let fileDataForm = g.component.file.getFileData();
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
						swal(response.reason, 'server Error occurred!', 'error');
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
	                    g.component.file.reload();
					}
					
					msg.alert.successSave();
                    
                    if (callbackFnc != null && typeof callbackFnc == 'function') {
                        callbackFnc(response);
                    }
                })
                .fail(function(data, textStatus, errorThrown) {
                    msg.alert.error();
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
		}, // move {}
		
		/**
		 * html form data management.
		 */
		form : {
			//Setting data on the form.
			//- Based on 'data' parameter key value.
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
	        
	        //Get parameters of dom in 'data-form' attribute.
	        getData : function() {
	            let formParams = {};
	            
	            function getChildNodeData(fele) {
					const notAllowTag = ['#text', '#comment'];
					if (notAllowTag.includes(fele.nodeName)) {
						return;
					}
	                
	                const childNodes = fele.childNodes;
	                if (childNodes != undefined) {
	                    childNodes.forEach((ele) => {
	                        getChildNodeData(ele);
	                    });
	                }
	                
	                if (fele.tagName == undefined) {
						console.log('fele undefined ', fele);
					}
	                let tagName = fele.tagName.toLowerCase();
	                const allowTag = ['input', 'select', 'select', 'textarea'];
	                if (!allowTag.includes(tagName)) {
	                    return;
	                }
	                 
	                if (tagName === 'input') {
	                    if (fele.type.toLowerCase() == 'radio') {
	                        if (formParams[fele.name] != undefined) {
	                            return;
	                        }
	                        const raiodObjs = document.querySelectorAll(`[name="${fele.name}"]`);
	                        formParams[raiodObjs[0].name] = '';
	                        
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
	        
	        //validation check.
	        validation : function(vObj = {}) {
				const self = this;
				
				let rb = true;
				if (Object.keys(vObj).length == 0) {
					return rb;
				}
				
	            const fData = self.getData();
	            let errCnt = 0;
	            let errKeyArr = [];
	            
	            //append html error style.
	            function viewDomStyle(key, regExpKey, color = 'red') {
					let ele = document.getElementById(key);
					ele = ele == null ? document.getElementsByName(key)[0] : ele;
					
					if (ele == undefined) { return; }
					
					const pele = ele.parentNode;
					let title = color == 'red' ? `Please Check '${regExpKey}'` : '';
					let tagName = ele.tagName.toLowerCase();
					let type = ele.type.toLowerCase();
					if (tagName == 'input' && type == 'radio') {
						//radio.
						if (pele != undefined && color === 'red') {
							pele.style['border'] = `solid 1px ${color}`;
						} else {
							pele.style['border'] = `0px`;
						}
						pele.title = title;
					} else {
						
						ele.style['border-color'] = color;
						ele.title = title;
					}
					
					//if datepicker.
					if (pele != undefined) {
						const p_class = pele.getAttribute('class');
						if (p_class != null && p_class.includes('datepicker')) {
							pele.style['border-color'] = color;
							pele.title = title
						}
					}
				}
				
	            //check regExp.
	            function chkRegx(key) {
					vObj[key].forEach(function(regExpKey) {
						if (regExpKey == 'required') { return; }
						if (g.regExp[regExpKey] == undefined) {
							console.log('============ not exists validation regExp ==>>', regExpKey);
						}
						
						if(!g.regExp[regExpKey].test(fData[key])) {
							//console.log(`validation error!! - ${key}`, regExpKey);
							
							viewDomStyle(key, regExpKey);
							
							errCnt++;
							errKeyArr.push(key);
						}
					});
				}
	            
	            let emptyIdArr = [];
	            
	            //1.Change dom style back to original.
				Object.keys(vObj).forEach(function(key) {
					vObj[key].forEach(function(regExpKey) {
						viewDomStyle(key, regExpKey, '#dee2e6');
					});
				});
	            
	            //2.Check regular expressions excluding 'required'.
	            Object.keys(vObj).forEach(function(key) {
					if (fData[key] == undefined) {
						return;
					}
					
					if (fData[key].trim() != '') {
						chkRegx(key);
					} else {
						emptyIdArr.push(key);
					}
				});
				
				//3.'required' regular expressions check.
				let regExpKey = 'required';
				emptyIdArr.forEach(function(key) {
					if (vObj[key].includes(regExpKey)) {
						viewDomStyle(key, regExpKey);
						
						errCnt++;
						errKeyArr.push(key);
					}
				});
				
				console.log('validation error === ', errKeyArr);
				
				if (errCnt > 0) {
					rb = false;
				}
	            
	            return rb;
			},
		}, // form {}
		
		/**
		 * common message management.
		 * swal - https://sweetalert.js.org/guides/
		 */
		msg : {
			//alert message.
			alert: {
				//고정된 default 메시지가 필요한경우 추가하여 사용.
				message: {
					successSave: {
						title: 'Success save!',
						subtitle: 'Save is complete.'
					},
					success: {
						title: 'Success!',
						subtitle: 'Processing has been completed.'
					},
					warning: {
						title: 'Warning!',
						subtitle: 'Confirmation is required.'
					},
					info: {
						title: 'Info!',
						subtitle: 'Infomation.'
					},
					error: {
						title: 'Server Error occurred!',
						subtitle: 'Please contact the administrator.'
					},
				},
				//save success message.
				//exists default message.
				successSave: function(title = null, subtitle = null, callbackFnc = null) {
					this.swalFnc(title, subtitle, 'successSave', 'success', callbackFnc);
				},
				//error icon message.
				//exists default message.
				error: function(title = null, subtitle = null, callbackFnc = null) {
					this.swalFnc(title, subtitle, 'error', 'error', callbackFnc);
				},
				//success icon message.
				//exists default message.
				success: function(title = null, subtitle = null, callbackFnc = null) {
					this.swalFnc(title, subtitle, 'success', 'success', callbackFnc);
				},
				//warning icon message.
				//exists default message.
				warning: function(title = null, subtitle = null, callbackFnc = null) {
					this.swalFnc(title, subtitle, 'warning', 'warning', callbackFnc);
				},
				//info icon message.
				//exists default message.
				info: function(title = null, subtitle = null, callbackFnc = null) {
					this.swalFnc(title, subtitle, 'info', 'info', callbackFnc);
				},
				swalFnc: function(title, subtitle, message, icon, callbackFnc) {
					this.swal({
						title: title,
						subtitle: subtitle,
						message: message,
						icon: icon,
						callbackFnc: callbackFnc
					});
				},
				swal: function(params) {
					const self = this;
					let title = params.title;
					let subtitle = params.subtitle;
					let icon = params.icon;
					
					//default message.
					if (title == null) {
						title = self.message[params.message].title;
					}
					if (subtitle == null) {
						subtitle = self.message[params.message].subtitle;
					}
					if (icon == null) {
						icon = self.message[params.message].subtitle;
					}
					
					swal(title, subtitle, icon)
	           		.then(() => {
						if (params.callbackFnc != null && typeof params.callbackFnc == 'function') {
	                        params.callbackFnc();
	                    }
					});
				},
			},
			
			//confirm message.
			confirm: {
				//고정된 default 메시지가 필요한경우 추가하여 사용.
				message: {
					save : {
						title: 'Save!',
						subtitle: 'Do you want to save?'
					},
				},
				save: function(callbackFnc = null) {
					const self = this;
					this.swal(self.message.save.title, self.message.save.subtitle, callbackFnc);
				},
				swal: function(title, message, callbackFnc) {
					swal(title, message, "warning", {
	           		  buttons: true,
	           		  dangerMode: false,  //true : OK button color change to red.
	           		  //closeOnClickOutside : false
	           		})
	           		.then((isOk) => {
						if (callbackFnc != null && typeof callbackFnc == 'function') {
	                        callbackFnc(isOk);
	                    }
					});
				},
			},
		}, // msg {}
		
		/**
		 * 1.layer popup management.
		 * 2.window popup management.
		 */
		popup : {
			//swal layer popup
			layerOpt: {
				title: '',
				content: '',
				buttons: ['Cancel', {text: 'OK', className: 'cs-swal-button'}],
				closeOnClickOutside : false // true: Close on background click, false: Does not close when clicking on the background
			},
			layer_setTitle: function() {
				let title_ele = document.querySelector('[class="swal-title"]');
				title_ele.style = 'margin: 0px;font-size: 16px;box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.21);margin-bottom: 28px;';
			},
			layer: function(p, callbackFnc) {
				const self = this;
				
				let opt = Object.assign({}, self.layerOpt);	
				if (typeof p === 'object') {
					Object.assign(opt, p);
				} else {
					opt.content = p;
				}
				
				const ele = document.getElementById(opt.content);
				opt.content = ele;
				ele.style.display = '';  //show popup html.
            	
            	swal(opt)
           		.then((isOk) => {
					if (callbackFnc != null && typeof callbackFnc == 'function') {
                        callbackFnc(isOk);
                    }
				});
				
				if (opt.title != '') {
					self.layer_setTitle();
				}
			},
			
			//window popup
			winOpt: {
				url: '',
				name: '_blank',
				width: 500,
				height: 500,
				top: 100,
				left: 200,
				scrollbars: 'yes',
				location: 'no',
				toolbars: 'no',
				status: 'no',
				titlebar: 'no',
				menubar: 'no',
				resizable: 'no',
				getOptions: function() {
					let opt = `width=${this.width},height=${this.height},left=${this.left},top=${this.top},`
					        + `scrollbars=${this.scrollbars},location=${this.location},`
					        + `toolbars=${this.toolbars},status=${this.status},`
					        + `titlebar=${this.titlebar},menubar=${this.menubar},`
					        + `resizable=${this.resizable}`;
					return opt;
				}
			},
			win: function(p, callbackFnc) {
				const self = this;
				
				let opt = Object.assign({}, self.winOpt, p);
				opt.left = Math.round(window.screenX + (window.outerWidth/2) - (opt.width/2));
    			opt.top = Math.round(window.screenY + (window.outerHeight/2) - (opt.height/2));
				
				const popWin = window.open(opt.url, opt.name, opt.getOptions());
				
				if (callbackFnc != null && typeof callbackFnc == 'function') {
                    callbackFnc(popWin);
                }
			}
		} // popup {}
    }
    
    wg.f = g.function;
    
    console.log("=== wg.f (function) ===", wg.f); 
    
})(window.global, wg.t);
