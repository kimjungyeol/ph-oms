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
        setChilds : function() {
            const self = this;
            
            function appendObject(type, target) {
                const warpper = document.querySelectorAll(`[${self.name}="${type}"]`);
                let childsObj = {};
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
            }
            
            //append trigger.button object.
            appendObject('button', '[id]');
            
            //append trigger.form object.
            //appendObject('form', '[id][name]');
        },
    }
    
    wg.c = g.contants;
    wg.t = g.triggers.childs;
    
    console.log("============= wg.t =============", wg.t); 
    
})(window.global);
