;(function(g, trigger) {
    console.log('context.function load!!');
    
    function validation() {
        if (g.contants.uriMap.search == '') {
            return false;
        }
        return true;
    }
    
    g.function = {
        /**
         * transaction defulat api.
         *   - search, save
         */
        api : {
            search : function(params = {}, callbackFnc = null) {
                console.log('call -> ' + g.contants.uriMap.search);
                
                if (!validation()) {
                    return;
                }
                
                $.ajax({
                    url: g.contants.uriMap.search,
                    method: "post",
                    dataType: "json",
                    data: JSON.stringify(params),
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
            save : function(params = {}, callbackFnc = null) {
                console.log('call -> ' + g.contants.uriMap.save);
                
                $.ajax({
                    url: g.contants.uriMap.save,
                    method: "post",
                    dataType: "json",
                    data: JSON.stringify(params),
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
            saveForm : function() {
                // TODO 
            }
        },
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
        },
        movePage : function(uri, params = null) {
            console.log('call -> ' + uri);
            
            if (!uri.startsWith('/')) {
                uri = '/' + uri;
            }
            
            this.storage.removeParams(uri);
            
            window.location.href = uri;
        },
        moveDetailPage : function(uri, params = null) {
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
                
                this.storage.setMoveParams(uri, params);
            }
            
            window.location.href = uri;
        },
        movePageForm : function(uri, params = null) {
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
        /**
         * Setting data on the form.
         *  - Based on 'data' parameter key value.
         */
        setFormData : function(data = {}) {
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
        getFormData : function() {
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
            let formObj = document.querySelectorAll('[data-trigger="form"]');
            formObj.forEach((form) => {
                form.childNodes.forEach((fele) => {
                    getChildNodeData(fele);
                });
            });
            
            return formParams;
        }
    }
    
    wg.f = g.function;
    
    console.log("============= wg.f =============", wg.f); 
    
})(window.global, wg.t);
