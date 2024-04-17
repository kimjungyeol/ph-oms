;(function(g) {
    console.log('context.contants load!!');
    
    /**
     * contents object.
     */
    g.contants = {
        name    : 'data-contants',
        context : {},
        uriMap : {
            search : '',
            save : '',
            delete : ''
        },
        initParams : {},
        init : function() {
            this.context = document.querySelector(`[${this.name}]`);
            
            this.init_dom();
        },
        init_dom : function() {
            const self = this;
            
            //input autocomplete off.
            const inputObjArr = self.context.querySelectorAll('input');
            inputObjArr.forEach((ele) => {
                ele.setAttribute('autocomplete', 'off');
            });
        },
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
        setChilds : function() {
            const self = this;
            
            //append trigger.button object.
            self.appendObject('button', '[id]');
            
            //append trigger.form object.
            //appendObject('form', '[id][name]');
        },
    }
    
    wg.c = g.contants;
    wg.t = g.triggers.childs;
    
    console.log("============= wg.t =============", wg.t);
    
})(window.global);
