/**
 * Global Script.
 * author : kjy
 * 
 * define global page scope context
 */
var wg = {};
;(function(w) {
    w.global = {
        /* [상수] */
        constant : {
            SUPER_ADMIN_USER      : 'S',
            SYSTEM_ADMIN_USER     : 'A',
            COMPANY_ADMIN_USER    : 'C',
            COMPANY_GENERAL_USER  : 'U'
        },
        /* [정규식] */
        regExp : {
            number : /^[0-9]*$/,  // 숫자만 사용 가능.
            string : /^[a-z|A-Z|ㄱ-ㅎ|가-힣|0-9 ]*$/,  // 특수문자를 제외한 공백포함 사용 가능.
            email : /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i,  // email 형식만 사용 가능.
        },
        init : function() {
        }
    }; 
    
    
    
})(window);

console.log('==== Context Load Start ====');

document.writeln("<script type='text/javascript' src='/js/common/context.contants.js'></script>");
document.writeln("<script type='text/javascript' src='/js/common/context.function.js'></script>");
document.writeln("<script type='text/javascript' src='/js/common/context.component.js'></script>");
document.writeln("<script type='text/javascript' src='/js/common/context.grid.js'></script>");

document.addEventListener("DOMContentLoaded", function() {
	const g = window.global;
	
	//context init.
    g.contants.init();
    g.triggers.init();
    
    //tui datepicker load.
    g.component.datepicker.tui.load();
});
