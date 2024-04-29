/**
 * 1. Grid별로 js파일을 생성하여 사용.
 * 		- [html]/grid/*-grid.js
 * 2. html에 include하여 사용.
 * 3. Toust UI Grid의 API를 참고하여 기본 속성을 그대로 사용한다.
 * 		- 참고 : https://github.com/nhn/tui.grid/blob/master/packages/toast-ui.grid/docs/ko/validation.md
 * 4. Grid 관련된 속성 지정 및 event 사용시 해당소스에 작성한다.
 */
;(function(g, trigger, fn, grid, co) {
	
	//grid에 사용될 selectbox list data.
	grid.codeList = {
		useYn  : ['commonCode', {grpCd: 'CM000'}],
		useYn2 : ['commonCode', {grpCd: 'CM001'}],
	}
    
    grid.gridInitOptions = function(opt) {
		//해당 소스를 include한 html에 지정되어있는 Grid ID 정보.
		//필수!!
	    let gridId = grid.id.grid;  // grid id.
	
	    let options = {
			autoSearch : true,   // 자동조회 여부.
	        uriMap : {           // grid transaction uri.
	            search : '/sample/board/list/search',
	        }
	    }
	    
		/**
	     * grid column info.  
	     */ 
		let gridColumns = [
	        {
	            header: 'Title',
	            name: 'title',
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
	            header: 'Contents',
	            name: 'contents',
	            width: 250,
	            sortable: true,                // [선택] 컬럼의 정렬 여부
	            resizable: true,               // [선택] 컬럼의 리사이즈 여부 옵션
	        },
	        {
	            header: 'Name',
	            name: 'name',
	            sortingType: 'asc',
	            align: "center",
	            sortable: true,
	            resizable: true,                // [선택] 컬럼의 리사이즈 여부 옵션
	            editor: 'text',                 // [선택] 수정 옵션
	            // [선택] 필터 옵션
	            filter: {
	                type: 'text',
	                showApplyBtn: true,
	                showClearBtn: true,
	            },
	            // [선택] 유효성 검증 옵션
	            validation: {
	                required: true,
	                dataType: 'string',
	                regExp: g.regExp.string,
	                color: 'red'
	            },
	        },
	        {
	            header: 'Age',
	            name: 'age',
	            align: "center",
	            sortable: true,                // [선택] 컬럼의 정렬 여부
	            resizable: true,               // [선택] 컬럼의 리사이즈 여부 옵션
	            editor: 'text',                // [선택] 수정 옵션
	            align: 'center',               // [선택] 텍스트 정렬 옵션
	            // [선택] 필터 옵션
	            filter: {
	                type: 'text',
	                showApplyBtn: true,
	                showClearBtn: true
	            },
	            // [선택] 유효성 검증 옵션
	            validation: {
	                unique: true,               // [선택] 유일성 체크 확인 옵션
	                dataType: 'number',         // [선택] 데이터 타입 체크 옵션
	                required: true,             // [선택] 필수 여부 체크 옵션
	                min: 1,                     // [선택] 최소값
	                max: 100,                   // [선택] 최대값
	            }
	        },
	        {
	            header: 'Email',
	            name: 'email',
	            width: 200,
	            align: "center",
	            editor: 'text',                 // [선택] 수정 옵션
	            // [선택] 필터 옵션
	            filter: {
	                type: 'text',
	                showApplyBtn: true,
	                showClearBtn: true
	            },
	            // [선택] 유효성 검증 옵션
	            validation: {
	                unique: true,               // [선택] Grid Row Data중 유일성 체크 확인 옵션
	                required: true,
	                dataType: 'string',
	                regExp: g.regExp.email,
	            },
	        },
	        {
	            header: 'UseYn',
	            name: 'useYn',
	            sortable: true,                 // [선택] 컬럼의 정렬 여부
	            resizable: true,                // [선택] 컬럼의 리사이즈 여부 옵션
	            align: 'center',                // [선택] 텍스트 정렬 옵션
	            filter: 'select',               // [선택] 필터 옵션
	            formatter: 'listItemText',      // [선택] select box 옵션
	            editor: {
	                type: 'select',				//radio, select
	                options: {
	                    listItems: opt.codeList.useYn
	                    //listItems: opt.codeList.useYn2
	                },
	            },
	        },
	        {
	            header: 'NameEng',
	            name: 'nameEng',
	            width: 200,
	            align: "center",
	            sortable: true,
	            ellipsis: true,
	            // hidden 값을 주어 사용자에게 해당 컬럼을 숨길 수 있다.
	            //hidden: 1,
	        },
	        {
		      header: 'Price',
		      name: 'price',
		      editor: 'text',
		      align: "center",
		      width: 100,
		      validation: {
		        min: 10000,
		        max: 20000,
		        regExp: g.regExp.number,
		      }
		    },
		    {
	          header: 'DatePicker',
	          name: 'dateymd',
	          editor: {
	            type: 'datePicker',
	            options: {
	                format: 'yyyy-MM-dd'
	            }
	          }
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
	            if (ev.columnName != 'title') { return; }
	            
	            //console.log(gridId + ' click ==', ev);
	            let rowData = grid.context[gridId].getRow(ev.rowKey);
	            //console.log('rowData', rowData);
	            
	            //row checked 여부.
	            //let checked = rowData._attributes.checked;
	            //console.log('checked', checked);
	            
	            if (rowData.title == '' || rowData.title == null) { return; }
	            
	            //이동하는 page의 storage 데이터를 세팅 후 이동.
	            //상세 page로 파라메터 전달 시 사용.
	            fn.move.detail(co.moveMap.write, rowData);
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
	                { type: 'rowNum', header: 'No.' }
	            ],
	            columns : gridColumns,
	            columnOptions: {
	                frozenCount: 1,
	                frozenBorderWidth: 1,
	                minWidth: 100,
	                resizable: true
	            },
	            pageOptions : {
	                perPage: 10
	            }
	        },
	        event : event
	    }
	    
	    return Object.assign({}, gridOption, options);
	}
	
})(window.global, wg.t, wg.f, wg.gr, wg.c);