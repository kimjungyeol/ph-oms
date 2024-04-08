;(function(g, co, fn, grid) {
    let gridId = grid.id.testGrid;
    
    console.log('===== testGrid.js =====', gridId);
    
    let gridColumns = [
        {
            header: 'Name',
            name: 'name',
            sortingType: 'asc',
            align: "center",
            sortable: true,
            resizable: true,
            editor: 'text',
        },
        {
            header: 'Age',
            name: 'age',
            align: "center",
            sortable: true,
            resizable: true,
            editor: 'text',
            align: 'center',
        },
        {
            header: 'Email',
            name: 'email',
            width: 200,
            align: "center",
            editor: 'text',
        },
        {
            header: 'UseYn',
            name: 'useYn',
            sortable: true,
            resizable: true,
            align: 'center',
            filter: 'select',
            formatter: 'listItemText',
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
            formatter: function(e){
                return `<a href="javascript:void(0)">${e.value} ~~</a>`; 
            }, 
        }
    ];
    
    let event = {
    }
    
    grid.wrapper[gridId] = {
        autoSearch : true,
        uriMap : {
            search : '/sample/board/list2/search',
        },
        option : {
            columns : gridColumns,
            pageOptions : {
                perPage: 10
            }
        },
        event : event
    }
    
})(window.global, wg.c, wg.f, wg.gr);