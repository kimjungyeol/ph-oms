;(function(g) {
    /**
     * contents object.
     */
    g.contents = {
        name    : 'data-contents',
        context : [],
        uriMap : {
            search : '',
            save : '',
            delete : ''
        },
        initParams : {},
        init : function() {
            this.context = document.querySelectorAll(`[${this.name}]`);
            
            this.init_dom();
        },
        init_dom : function() {
            const self = this;
            
            if (this.context.length > 0) {
	            //input autocomplete off.
	            const inputObjArr = self.context[0].querySelectorAll('input');
	            inputObjArr.forEach((ele) => {
	                ele.setAttribute('autocomplete', 'off');
	            });
			}
        },
        /**
		 * js file to apply to current page. 
		 */
        require: function(t) {
			if (t == undefined) { return; }
			
			function writeln(path) {
				document.writeln(`<script type='text/javascript' src='${path}'></script>`);
			}
			
			if (typeof t === 'object' && Array.isArray(t)) {
				t.forEach(function(path) {
					writeln(path);
				});
			} else if (typeof t === 'string') {
					writeln(t);
			}
		}
    }
    
    /**
     * triggers object.
     * tag appended attribute.
     */
    g.triggers = {
        name   : 'data-trigger',
        childs : {
            button : {},
            form : {}
        },
        init : function() {
            const self = this;
            
            self.setChilds();
        },
        setChilds : function() {
            const self = this;
            
            //append trigger.button object.
            self.appendObject('button', '[id]');
            
            //append trigger.form object.
            //appendObject('form', '[id][name]');
        },
        appendObject: function(type, target) {
			const self = this;
            const warpper = document.querySelectorAll(`[${self.name}="${type}"]`);
            let childsObj = {};
            
            if (warpper.length == 0) {
				return;
			}
            
            warpper.forEach((ele) => {
                let ObjEle = ele.querySelectorAll(target);
                ObjEle.forEach((ele) => {
                    let id = ele.getAttribute("id");
                    let name = ele.getAttribute("name");
                    
                    if ((id == '' || id == null) && (name == '' || name == null)) {
                        return; //continue.
                    }
                    
                    childsObj[id] = ele;
                });
            });
            
            Object.assign(self.childs[type], childsObj);
            //console.log('self.childs', self.childs);
        },
    }
    
    wg.c = g.contents;
    wg.t = g.triggers.childs;
    
    console.log("=== wg.c (contents) ===", wg.c);
    console.log("=== wg.t (triggers) ===", wg.t);
    
})(window.global);
