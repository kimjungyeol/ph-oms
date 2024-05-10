;(function(g, co, fn, grid) {
	
    let gridId = grid.id.grid2;
    grid.init(gridId);
	
	grid[gridId].gridInitOptions = function() {
	    
	    let options = {
			autoSearch : true,   // 자동조회 여부.
	        uriMap : {           // grid transaction uri.
	            search : '/sample/board/list2/search',
	        }
	    }
	    
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
	    
	    let gridOption = {
	        option : {
	            columns : gridColumns,
	            pageOptions : {
	                perPage: 10
	            }
	        },
	        event : event
	    }
	    
	    return Object.assign({}, gridOption, options);
	}
})(window.global, wg.c, wg.f, wg.gr);