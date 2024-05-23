/**
 * 1. Grid별로 js파일을 생성하여 사용.
 *      - [html]/grid/*-grid.js
 * 2. html에 include하여 사용.
 * 3. Toust UI Grid의 API를 참고하여 기본 속성을 그대로 사용한다.
 *      - 참고 : https://github.com/nhn/tui.grid/blob/master/packages/toast-ui.grid/docs/ko/validation.md
 * 4. Grid 관련된 속성 지정 및 event 사용시 해당소스에 작성한다.
 */
;(function(g, trigger, fn, grid, co) {
    
    //해당 소스를 include한 html에 지정되어있는 Grid ID 정보.
    //필수!!
    let gridId = grid.id.grid;  // grid id.
    grid.init(gridId);
    
    //grid에 사용될 selectbox list data.
    /*grid[gridId].header = {
        SP99999 : 'title',
        SP99998 : 'contents',
        SP99997 : 'name',
        SP99996 : 'age',
        SP99995 : 'email',
    }
    grid[gridId].codeList = {
        useYn  : ['commonCode', {grpCd: 'CM000'}],
        useYn2 : ['commonCode', {grpCd: 'CM001'}],
    }*/
    
    grid[gridId].gridInitOptions = function(opt) {
    
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
                header: '상품명',
                name: 'itemName',
                align: 'left',
                editor: 'text',
            },
            {
                header: 'HS Code',
                name: 'hsCode',
                align: 'left',
                editor: 'text',
            },
            {
                header: '가격',
                name: 'amount',
                align: 'left',
                editor: 'text',
            },
            {
                header: '원산지 구분',
                name: 'origin',
                align: 'center',
                formatter: 'listItemText',
                editor: {
                    type: 'radio',
                    options: {
                        listItems: [
                            { text: '역내산', value: 'Y' },
                            { text: '역외산', value: 'N' },
                        ]
                    }
                },
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
                
            },
            //grid cell값 변경시 호출.
            afterChange : function(ev) {
                console.log(gridId + ' afterChange ==', ev);
                
                let rowData = grid[gridId].context.getRow(ev.changes[0].rowKey);
                console.log('rowData', rowData);
            },
            //grid check box check event 발생시 호출.
            check : function(ev) {
                console.log(gridId + ' check ==', ev);
                
                let rowData = grid[gridId].context.getRow(ev.rowKey);
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
                    //frozenCount: 1,
                    //frozenBorderWidth: 1,
                    minWidth: 100,
                    resizable: true
                },
                /*pageOptions : {
                    perPage: 10
                }*/
            },
            event : event
        }
        
        return Object.assign({}, gridOption, options);
    }
    
})(window.global, wg.t, wg.f, wg.gr, wg.c);