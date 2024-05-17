;(function(g) {
    /**
	 * Multilingual apply.
	 */
    g.i18n = {
		word : {
			name : 'data-i18n-w',
			uri : '/i18n/word',
		},
		message : {
			name : 'data-i18n-m',
			uri : '/i18n/message',
		},
		wordList : {
			uri: '/i18n/word/list'
		},
		defaultWord : {
			uri : '/i18n/default/word/list'
		},
		defaultMessage : {
			uri : '/i18n/default/message/list'
		},
		cache : {
			word : '/cache/clear/i18n/word',
			message : '/cache/clear/i18n/message'
		},
		apply : function() {
			this.htmlI18n('word');
			this.htmlI18n('message');
		},
		htmlI18n : function(target) {
			const self = this;
			const name = self[target].name;
			const eleArr = document.querySelectorAll(`[${name}]`);
			
            if (eleArr.length == 0) { return; }
            
            eleArr.forEach((ele) => {
                self.get(target, {code: ele.getAttribute(`${name}`)}, function(res) {
					if (res.result && res.data != null) {
						ele.innerHTML = res.data.name;
					}
				});
            });
		},
		cacheClear : function(target) {
			const self = this;
			
			console.log(target, self.cache[target]);
			
			$.ajax({
				url: self.cache[target],
                method: "get",
                dataType: "json",
                contentType: 'application/x-www-form-urlencoded',
                data: {}
            })
            .done(function (response, textStatus, xhr) {
            })
            .fail(function(data, textStatus, errorThrown) {
                console.log("cacheClear", "server Error occurred!", data);
            });
		},
		get : function(target, params = {}, callbackFnc = null) {
			const self = this;
			params.lang = global.lang;  //current selectd language code.
			
			let options = {
				url: self[target].uri,
                method: "get",
                dataType: "json",
                contentType: 'application/x-www-form-urlencoded',
                data: params
			}
			
			if (options.url.indexOf('list') > -1) {
				options.method = 'post';
				options.data = JSON.stringify(params);
			}
			
			$.ajax(options)
            .done(function (response, textStatus, xhr) {
                if (callbackFnc != null && typeof callbackFnc == 'function') {
                    callbackFnc(response);
                }
            })
            .fail(function(data, textStatus, errorThrown) {
                console.log("server Error occurred!", data);
            });
		},
		getWord : function(code) {
			const self = this;
			return new Promise((resolve) => {
				let isRtn = false;
				
				if (code == null || code == undefined) {
					resolve(code);
					isRtn = true;
				}
				
				//return word default storage.
				const storageData = sessionStorage.getItem('defaultWord');
				if (storageData != null) {
					const dataList = JSON.parse(storageData);
					dataList.forEach(function(map) {
						if (map.code == code) {
							resolve(map.name);
							isRtn = true;
						}
					});
				}
				if (isRtn) {
					return;
				}
				
				//return word db search.
				self.get('word', {code: code}, function(response) {
					if (response.data == null) {
						resolve(code);
					} else {
						resolve(response.data.name);
					}
				});
			});
		},
		getMessage : function(code) {
			const self = this;
			return new Promise((resolve) => {
				let isRtn = false;
				
				if (code == null || code == undefined) {
					resolve(code);
					return;
				}
				
				//return message default storage.
				const storageData = sessionStorage.getItem('defaultMessage');
				if (storageData != null) {
					const dataList = JSON.parse(storageData);
					dataList.forEach(function(map) {
						if (map.code == code) {
							resolve(map.name);
							isRtn = true;
						}
					});
				}
				if (isRtn) {
					return;
				}
				
				//return message db search.
				self.get('message', {code: code}, function(response) {
					if (response.data == null) {
						resolve(code);
					} else {
						resolve(response.data.name);
					}
				});
			});
		},
		setDefaultI18n : function(target) {
			const self = this;
			return new Promise((resolve) => {
				if (target == null || target == undefined) {
					resolve(null);
					return;
				}
				if (target == 'word') {
					target = 'defaultWord';
				} else if (target == 'message') {
					target = 'defaultMessage';
				}
				
				const storageData = sessionStorage.getItem(target);
                if (storageData != null) {
					resolve(null);
					return;
				}
                
				self.get(target, {}, function(response) {
					if (response.dataList == null) {
						resolve(null);
					} else {
						sessionStorage.setItem(target, JSON.stringify(response.dataList));
						resolve(null);
					}
				});
			});
		},
		getWordList : function(params = {}, callbackFnc = null) {
			const self = this;
			self.get('wordList', {i18n: params}, callbackFnc);
		},
    }
    
    wg.i18n = g.i18n;
    
    console.log("=== wg.i18n (i18n) ===", wg.i18n); 
    
})(window.global);
