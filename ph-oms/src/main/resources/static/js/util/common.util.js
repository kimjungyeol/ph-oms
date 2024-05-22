//parameter date value - ISO8601('YMD')
//return CurrentLang date.
function getISO8601ToCurrentLang(date = "", sep = "") {
    const lang = window.global.lang.toLowerCase();
    let rtnDate = "";

    if (date != "" && date != null && date.length >= 8) {
	    date = date.replace(/[^0-9]/g, "");
	    
        let y = date.substring(0, 4);
        let m = date.substring(4, 6);
        let d = date.substring(6, 8);
        let t = "";

        if (date.length > 8) {
            t = date.substring(8);
        }

        rtnDate = y + sep + m + sep + d + t;
        if (lang == "en") {
            rtnDate = m + sep + d + sep + y + t;
        }
    }

    return rtnDate;
}
//return ISO8601('YMD')
function getCurrentLangToISO8601(date = "", sep = "") {
    if (date != "" && date != null && date.length == 8) {
		date = date.replace(/[^0-9]/g, "");
        lang = window.global.lang.toLowerCase();
        
        let y = "";
        let m = "";
        let d = "";
        if (lang == "ko") {
            y = date.substring(0, 4);
            m = date.substring(4, 6);
            d = date.substring(6, 8);
        } else if (lang == "en") {
            m = date.substring(0, 2);
            d = date.substring(2, 4);
            y = date.substring(4, 8);
        }
        date =  y + sep + m + sep + d;
    }
    return date;
}