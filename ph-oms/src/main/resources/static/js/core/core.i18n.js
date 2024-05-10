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
		messageList : {
			uri : '/i18n/message/list'
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
					if (res.result) {
						ele.innerHTML = res.data.name;
					}
				});
            });
		},
		get : function(target, params = {}, callbackFnc = null) {
			const self = this;
			params.lang = g.lang;  //current selectd language code.
			
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
		getWordList : function(params = {}, callbackFnc = null) {
			const self = this;
			self.get('wordList', {i18n: params}, callbackFnc);
		}
    }
    
    wg.i18n = g.i18n;
    
    console.log("=== wg.i18n (i18n) ===", wg.i18n); 
    
})(window.global);
