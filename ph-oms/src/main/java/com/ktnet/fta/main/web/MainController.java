package com.ktnet.fta.main.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Main root path.
     * 
     * @param Map RequestBody
     * @return ResponseBody Map
     * @throws Exception
     * 
     *                   <pre>
     *      Date          Person in Charge                  Comment
     *  ============  =======================    ===========================
     *  2024. 03. 13.      Jung Yeol KIM                initial version
     *                   </pre>
     */
    @GetMapping("/")
    public String index(HttpServletRequest req, Model model) throws Exception {
        logger.debug("index");
        return "index";
    }

    @GetMapping("/error/{page}")
    public String error(HttpServletRequest req, @PathVariable("page") String page) throws Exception {
        logger.debug("error page move = {}", page);
        return "error/" + page;
    }

    /**
     * move method.
     * 
     * @param req
     * @param page1
     * @param page2
     * @param page3
     * @return
     * @throws Exception
     */
    @GetMapping({ "/contents/{page1}/", "/contents/{page1}/{page2}", "/contents/{page1}/{page2}/{page3}" })
    public String pageMove(HttpServletRequest req, @PathVariable("page1") String page1,
            @PathVariable(name = "page2", required = false) String page2,
            @PathVariable(name = "page3", required = false) String page3) throws Exception {

        String prefixUrl = "contents";
        String returnUrl = prefixUrl + "/" + page1;
        if (page2 != null) {
            returnUrl += "/" + page2;
        }
        if (page3 != null) {
            returnUrl += "/" + page3;
        }

        logger.debug("page move => {}", returnUrl);
        return returnUrl;
    }

    /**
     * sample move method.
     * 
     * @param req
     * @param page1
     * @param page2
     * @param page3
     * @return
     * @throws Exception
     */
    @GetMapping({ "/sample/contents/{page1}", "/sample/contents/{page1}/{page2}",
            "/sample/contents/{page1}/{page2}/{page3}" })
    public String samplePageMove(HttpServletRequest req, @PathVariable("page1") String page1,
            @PathVariable(name = "page2", required = false) String page2,
            @PathVariable(name = "page3", required = false) String page3) throws Exception {

        String prefixUrl = "sample/contents";
        String returnUrl = prefixUrl + "/" + page1;
        if (page2 != null) {
            returnUrl += "/" + page2;
        }
        if (page3 != null) {
            returnUrl += "/" + page3;
        }

        logger.debug("page move => {}", returnUrl);
        return returnUrl;
    }

}