;(function(g, trigger, fn, grid, co) {
    
    let gridId = grid.id.sampleGrid;  // grid id.
    
    console.log('===== sampleGrid.js =====', gridId);
    
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
                type: 'radio',
                options: {
                    listItems:
                        [
                            {text: '예', value: "Y"},
                            {text: '아니오', value: "N"},
                        ]
                },
            },
        },
        {
            header: 'NameEng',
            name: 'nameEng',
            width: 400,
            align: "center",
            sortable: true,
            ellipsis: true,
            // hidden 값을 주어 사용자에게 해당 컬럼을 숨길 수 있다.
            //hidden: 1,
        }
    ];
    
    /**
     * event object.
     * context.grid.js => gridLoad() event.
     */
    let event = {
        response : function(ev) {
            console.log(gridId + ' response ==', ev);
            
            const {response} = ev.xhr;
            const responseObj = JSON.parse(response);
            
            console.log('responseObj ==', responseObj);
            
        },
        click : function(ev) {
            if (ev.columnName != 'title') { return; }
            
            //console.log(gridId + ' click ==', ev);
            let rowData = grid.context[gridId].getRow(ev.rowKey);
            //console.log('rowData', rowData);
            
            //row checked 여부.
            //let checked = rowData._attributes.checked;
            //console.log('checked', checked);
            
            if (rowData.title == '' || rowData.title == null) { return; }
            
            fn.movePage(co.moveMap.write, rowData);  //page move.
        },
        afterChange : function(ev) {
            console.log(gridId + ' afterChange ==', ev);
            
            let rowData = grid.context[gridId].getRow(ev.changes[0].rowKey);
            console.log('rowData', rowData);
        },
        check : function(ev) {
            console.log(gridId + ' check ==', ev);
            
            let rowData = grid.context[gridId].getRow(ev.rowKey);
            console.log('rowData', rowData);
        }
    }
    
    /**
     * grid option.
     */
    grid.wrapper[gridId] = {
        autoSearch : true,   // 자동조회 여부.
        uriMap : {           // grid transaction uri.
            search : '/sample/board/list/search',
            save   : '/sample/board/list/save',
        },
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
    
})(window.global, wg.t, wg.f, wg.gr, wg.c);