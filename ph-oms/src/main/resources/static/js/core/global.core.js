/**
 * Global Script.
 * author : kjy
 * 
 * define global page scope context
 */
var wg = {};
;(function(w) {
    w.global = {
		//상수
        constant : {
            SUPER_ADMIN_USER      : 'S',
            SYSTEM_ADMIN_USER     : 'A',
            COMPANY_ADMIN_USER    : 'C',
            COMPANY_GENERAL_USER  : 'U'
        },
        //정규식
        regExp : {
            number : /^[0-9]*$/,   // 숫자만.
            eng : /^[a-z|A-Z]*$/,  // 영문만.
            string : /^[a-z|A-Z|ㄱ-ㅎ|가-힣|0-9 ]*$/,  // 특수문자를 제외한 공백포함.
            telno : /^[0-9]{8,13}$/, // 전화번호 숫자만 8~13자리.
            mobile : /^\d{3}-\d{3,4}-\d{4}$/,  //휴대폰 번호만.
            email : /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/,  // email 형식.
            dateKo : /^\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/,  //포맷 yyyy-mm-dd
            dateEn : /^(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-\d{4}$/,  //포맷 mm-dd-yyyy
            pw : /(?=.*[a-zA-ZS])(?=.*?[#?!@$%^&*-]).{6,24}/,  // 문자와 특수문자 조합의 6~24 자리.
            pw2 : /(?=.*\d)(?=.*[a-zA-ZS]).{8,}/,  // 문자, 숫자 1개이상 포함, 8자리 이상.
        },
        //global scope object init.
        init : function() {
			
			//context init.
		    this.contents.init();
		    this.triggers.init();
		    
		    //file area render.
			this.component.file.render();
		    //tui datepicker render.
		    this.component.datepicker.tui.render();
        },
    }; 
    
    //wg[contents, triggers, function, component, grid]
	const js = [
		{src : '/js/core/core.contents.js'},
		{src : '/js/core/core.function.js'},
		{src : '/js/core/core.component.js'}, 
		{src : '/js/core/core.grid.js'},
	];
	js.forEach(function(obj) {
		document.writeln(`<script type='text/javascript' src='${obj.src}'></script>`);
	});
	
})(window);

console.log('==== Context Load Start ==== wg[contents, triggers, function, component, grid]');

document.addEventListener("DOMContentLoaded", function() {
	const g = window.global;
    g.init();
});
