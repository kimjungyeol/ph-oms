/**
 * 1. Grid별로 js파일을 생성하여 사용.
 *      - [html]/grid/*-grid.js
 * 2. html에 include하여 사용.
 * 3. Toust UI Grid의 API를 참고하여 기본 속성을 그대로 사용한다.
 *      - 참고 : https://github.com/nhn/tui.grid/blob/master/packages/toast-ui.grid/docs/ko/validation.md
 * 4. Grid 관련된 속성 지정 및 event 사용시 해당소스에 작성한다.
 */
;(function(g, trigger, fn, grid, co) {
    
    grid.gridInitOptions = function(opt) {
			
	    let gridId = grid.id.grid;  // grid id.
	
	    let options = {
	        autoSearch : true,   // 자동조회 여부.
	        uriMap : {           // grid transaction uri.
	            search : '/sample/invoice/list/search',
	        }
	    }
	    
	    /**
	     * grid column info.  
	     */
	    let headerColumns = {
	        height: 80,
	        complexColumns: [
	            {
	                header: '인보이스',
	                name: 'invoice',
	                childNames: ['invoiceNo', 'invoiceDate'],
	            },
	            {
	                header: '품목',
	                name: 'item',
	                childNames: ['eee', 'fff'],
	            },
	        ]
	    };
	    
	    let gridColumns = [
	        {
	            header: '번호',
	            name: 'invoiceNo',
	            align: 'center',
	            //width: 250,
	            sortable: true,                // [선택] 컬럼의 정렬 여부
	            resizable: true,               // [선택] 컬럼의 리사이즈 여부 옵션
	            formatter: function(e){
	                let val = '';
	                if (e.value != null) {
	                    val = `<span style="cursor:pointer;"><u>${e.value}</u></span>`; 
	                }
	                return val;
	            }, 
	        },
	        {
	            header: '일자',
	            name: 'invoiceDate',
	            //sortingType: 'asc',
	            align: 'center',
	            sortable: true,
	            resizable: true,                // [선택] 컬럼의 리사이즈 여부 옵션
	            formatter: function(e) {
	                return e.value;
	            },
	            //editor: 'text',                 // [선택] 수정 옵션
	            // [선택] 필터 옵션
	            /*filter: {
	                type: 'text',
	                showApplyBtn: true,
	                showClearBtn: true,
	            },*/
	            // [선택] 유효성 검증 옵션
	            /*validation: {
	                required: true,
	                dataType: 'string',
	                regExp: g.regExp.string,
	                color: 'red'  
	            },*/
	        },
	        {
	            header: 'FTA',
	            name: 'ftaName',
	            align: 'center',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '상태',
	            name: 'status',
	            align: 'center',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '수하인',
	            name: 'aaa',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '목적국',
	            name: 'destinationCountry',
	            sortable: true,
	            resizable: true,
	        },
	        {
                header: '목적국코드',
                name: 'destinationCountryCode',
                sortable: true,
                resizable: true,
                hidden: true,
            },
	        {
	            header: '금액',
	            name: 'ccc',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '통화',
	            name: 'ddd',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '전체',
	            name: 'eee',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '품번누락',
	            name: 'fff',
	            sortable: true,
	            resizable: true,
	        },
	        {
	            header: '증명작성번호',
	            name: 'ggg',
	            sortable: true,
	            resizable: true,
	        },
	        
	    ];
	    
	    /**
	     * grid event 소스 적용.
	     * context.grid.js => gridLoad() event.
	     */
	    let event = {
	        //grid data 조회후 호출.
	        response : function(ev) {
	            console.log(gridId + ' response ==', ev);
	            
	            const {response} = ev.xhr;
	            const responseObj = JSON.parse(response);
	            
	            console.log('responseObj ==', responseObj);
	            
	        },
	        //grid cell click시 호출.
	        click : function(ev) {
	            if (ev.columnName == 'invoiceNo') {
	                let rowData = grid.context[gridId].getRow(ev.rowKey);
	                
	                if (rowData.invoiceNo != '' && rowData.invoiceNo != null) {
	                    fn.move.detail(co.moveMap.detail, rowData);    
	                }
	            }
	            
	            
	            //if (ev.columnName != 'title') { return; }
	            
	            //console.log(gridId + ' click ==', ev);
	            //let rowData = grid.context[gridId].getRow(ev.rowKey);
	            //console.log('rowData', rowData);
	            
	            //row checked 여부.
	            //let checked = rowData._attributes.checked;
	            //console.log('checked', checked);
	            
	            //if (rowData.title == '' || rowData.title == null) { return; }
	            
	            //이동하는 page의 storage 데이터를 세팅 후 이동.
	            //상세 page로 파라메터 전달 시 사용.
	            //fn.move.detail(co.moveMap.write, rowData);
	        },
	        //grid cell값 변경시 호출.
	        afterChange : function(ev) {
	            console.log(gridId + ' afterChange ==', ev);
	            
	            let rowData = grid.context[gridId].getRow(ev.changes[0].rowKey);
	            console.log('rowData', rowData);
	        },
	        //grid check box check event 발생시 호출.
	        check : function(ev) {
	            console.log(gridId + ' check ==', ev);
	            
	            let rowData = grid.context[gridId].getRow(ev.rowKey);
	            console.log('rowData', rowData);
	        }
	    }
	    
	    /**
	     * grid option.
	     */
	    let gridOption = {
	        option : {
	            rowHeaders: [
	                { type: 'checkbox' },
	                //{ type: 'rowNum', header: 'No.' }
	            ],
	            header: headerColumns,
	            columns : gridColumns,
	            /*columnOptions: {
	                frozenCount: 1,
	                frozenBorderWidth: 1,
	                minWidth: 100,
	                resizable: true
	            },*/
	            pageOptions : {
	                perPage: 10
	            },
	            width : 1200,
	        },
	        event : event
	    }
	    
	    return Object.assign({}, gridOption, options);
    }
    
})(window.global, wg.t, wg.f, wg.gr, wg.c);