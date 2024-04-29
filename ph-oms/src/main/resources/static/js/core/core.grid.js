/**
 * Toast Ui Grid 
 * https://ui.toast.com/tui-grid
 */
;(function(g) {
	
    function optValidation(id, opt) {
        if (id == null || id == undefined) {
            console.log('empty id.');
            return false;
        }
        if (document.getElementById(id) == null) {
            console.log('not exists grid element.');
            return false;
        }
        if (opt == null) {
            console.log('option is null.');
            return false;
        }
        
        if (opt.columns == null || (opt.columns != null && opt.columns.length == 0)) {
            console.log('empty columns.');
            return false;
        }
        
        return true;
    }
    
    g.grid = {
        context: {},    // grid object context in view page.
        id : {},        // grid object id in view page.
        wrapper : {},   // grid object option in view page.
        option : {      // grid object default option.
            el : {},
            data : {},
            scrollX : true,
            scrollY : false,
            rowHeight: 'auto',
            data: [],
            columns: [],
            width : 900,
            minRowHeight : 35,
            columnOptions : {
                frozenCount: 0,
                frozenBorderWidth: 1,
                minWidth: 100,
                resizable: true
            },
            rowHeaders : [
                 { type : 'rowNum', header : 'No.' },
            ],
        },
        
        /**
		 * render
         *   - grid init.
         */
        render : async function(gridId, params = {}, apiOpt = null) {
            const self = this;
            
            const prKey = [];
            const prArr = [];
            
            //selectbox obj.
			Object.keys(self.codeList).forEach(function(key) {
				prArr.push(self.codeList[key]);
				prKey.push(key);
			});
			
			Promise.all(prArr).then((res) => {
                res.map((m, idx) => {
					if (m.result) {
						self.code.listItems[prKey[idx]] = m.dataList;
					} else {
						self.code.listItems[prKey[idx]] = [];
					}
                });
            })
            .then(() => {
                
                g.grid.wrapper[gridId] = g.grid.gridInitOptions({
					codes: self.code.listItems
				});
                
                let global_option = JSON.parse(JSON.stringify(self.option));             // default global option.
	            let loadGrid_wrapper = Object.assign({}, self.wrapper[gridId], apiOpt);  // current load grid option.
	            let render_option = Object.assign({}, global_option, loadGrid_wrapper.option, self.dataSource(params, loadGrid_wrapper));
	            
            	self.gridLoadProc(gridId, render_option);
            });
        },
        /**
		 * render
         *   - grid load proc.
         */
        gridLoadProc : function(id, render_option) {
            const self = this;
            
            self.load(id, render_option);
            
            function callbackProc(ev, type) {
                if (self.wrapper[id].event != undefined && typeof self.wrapper[id].event[type] == 'function') {
                    self.wrapper[id].event[type](ev);
                }
            }
            
            // response event.
            self.context[id].on('response', (ev) => self.responseProc(ev, function(resObj) {
                callbackProc(ev, 'response');
            }));
            
            //checkbox check event.
            self.context[id].on('check', ev => {
                callbackProc(ev, 'check');
            });
            
            //checkbox uncheck event.
            self.context[id].on('uncheck', ev => {
                callbackProc(ev, 'uncheck');
            });
            
            //cell click event.
            self.context[id].on('click', ev => {
                callbackProc(ev, 'click');
            });
            
            //cell value change.
            self.context[id].on('afterChange', ev => {
                callbackProc(ev, 'afterChange');
            });
        },
        /**
		 * render
         *   - grid load.
         */
        load : function(id, opt = null) {
            const self = this;
            
            // validation check.
            if (!optValidation(id, opt)) { return; }
            
            // if exists grid initialize.
            if (self.context[id] != undefined) {
                document.getElementById(id).innerHTML = ''; 
            }
            
            // dom settings where grid will be created.
            opt.el = document.getElementById(id);
            
            // tui grid object create.
            const TuiGrid = tui.Grid;
            
            // set grid theme.
            self.applyGridTheme();
            
            // create TuiGrid.
            self.context[id] = new TuiGrid(opt);
        },
        /**
         * apply grid theme
         *  - 'default', 'striped', 'clean'.
         *  - https://nhn.github.io/tui.grid/latest/Grid#applyTheme
         */
        applyGridTheme : function() {
            //tui.Grid.applyTheme('striped');
            tui.Grid.applyTheme('defualt', {
                cell: {
                      normal: {  // default cell color.
                          background: '#fbfbfb',
                          border: '#e0e0e0'
                      },
                      editable: {  // edit cell color.
                          background: '#fffdeb'
                      },
                      //disabled: {  //disabled cell color.
                      //    text: '#b0b0b0'
                      //},
                      //header: {  // header color.
                      //    background: '#a5a5a5',
                      //},
                      //required: {  // 필수 입력 cell color.
                      //    background: '#f2f9ff',
                      //}
                      //rowHeader: {  // 기본 설정 header color.
                      //    background: '#f2f9ff'
                      //},
                      //selectedHeader: {  //header 선택 시.
                      //    background: '#d8d8d8'
                      //},
                      //evenRow: {   // 짝수행 line .
                      //    background: '#edecec'
                      //},
                      //focused: {  //선택된 cell outline color.
                      //    border: '#51abf7'
                      //},
                }
            });
        },
        /**
         * grid transaction info.
         */
        dataSource : function(gridParams = {}, opt) {
            // auto search - default valuse is true.
            let autoSearch = opt.autoSearch == undefined ? true : opt.autoSearch;
            
            return {data : {
                initialRequest: autoSearch,          // [Essential] 초기 데이터 조회를 위한 readData API 요청 여부
                api: {
                    hideLoadingBar: false,           // [Options] 로딩바 숨김 여부
                    readData: {
                        url: opt.uriMap.search,
                        method: 'GET',
                        initParams: gridParams       // [Option] Query String으로 보낼 데이터
                    },
                    //modifyData: { url: opt.uriMap.save, method: 'POST' },
                },
            }}
        },
        /**
         * grid callback function after transaction.
         */
        responseProc : function(ev, callbackFnc = null) {
            const {response} = ev.xhr;
            let responseObj = null;
            
            try {
				responseObj = JSON.parse(response);
			} catch(e) {}
            
            /*
            if (responseObj.result) {
                const {result, data: {contents}} = responseObj;
                
                if (contents != undefined && contents.length > 0) {
                    if (contents.type === "insert") {
                        alert("Registration has been completed.");
                    }
                    if (contents.type === "update") {
                        alert("The modification has been completed.");
                    }
                } else {
                    console.log("Grid search success.");
                }
            } else {
                alert("This has not been processed.\n Please contact the administrator.")
            }
            */
		   
            //console.log('result : ', responseObj.result, " data: ", responseObj.data);
            
            if (responseObj != null && callbackFnc != null && typeof callbackFnc == 'function') {
                callbackFnc(responseObj);
            }
        },
        
        /**
         * transaction external call function.
         */
        api : {
            search : function(gridId, gridParams) {
                //readData(pageNo-Number, parameter-Object)
                g.grid.context[gridId].readData(1, gridParams);
            },
        },
        
        /**
		 * grid selectbox data.
		 */
		codeList: [],
        code : {
			listItems: {},
			uri: {
				commonCode: '/common/code/grid/search'
			},
			search: async function(u, params) {
				const code = this;
				
				return new Promise((resolve) => {
					$.ajax({
	                    url: code.uri[u],
	                    method: "post",
	                    dataType: "json",
	                    contentType: 'application/x-www-form-urlencoded',
	                    data: JSON.stringify(params),
	                })
	                .done(function (response, textStatus, xhr) {
	                    if (response.status == 500) {
							return;
						}
						
			            resolve(response);
	                })
	                .fail(function(data, textStatus, errorThrown) {
						resolve({
							result: false
						});
	                });
				});
			}
		}
    }
    
    wg.gr = g.grid;
    
    console.log("=== wg.gr (grid) ===", wg.gr); 
    
})(window.global);
