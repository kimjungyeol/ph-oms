;(function(g, trigger, fn, grid, co) {
    
    grid.gridInitOptions = function(opt) {
		
	    //해당 소스를 include한 html에 지정되어있는 Grid ID 정보.
	    //필수!!
	    let gridId = grid.id.grid;  // grid id.
	
	    let options = {
	        autoSearch : false,   // 자동조회 여부.
	        uriMap : {           // grid transaction uri.
	            //search : '/sample/invoice/detail/search',
	        }
	    }
	    
	    /**
	     * grid column info.  
	     */
	    let gridColumns = [
	        {
	            header: '품번',
	            name: 'aaa',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '품명',
	            name: 'bbb',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '우선활용추천',
	            name: 'ccc',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '생산관리번호',
	            name: 'ddd',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: 'HS 코드',
	            name: 'eee',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '원산지',
	            name: 'fff',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '수량',
	            name: 'ggg',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '단위',
	            name: 'hhh',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '금액',
	            name: 'iii',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '통화',
	            name: 'jjj',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '수출신고번호',
	            name: 'kkk',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '증명작성번호',
	            name: 'lll',
	            align: 'center',
	            resizable: true,
	        },
	        {
	            header: '우선정보활용',
	            name: 'mmm',
	            align: 'center',
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
	            ],
	            columns : gridColumns,
	            /*pageOptions : {
	                perPage: 10
	            },*/
	            width : 'auto',
	        },
	        event : event
	    }
	    
	    return Object.assign({}, gridOption, options);
	}
    
})(window.global, wg.t, wg.f, wg.gr, wg.c);