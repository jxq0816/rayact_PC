package com.bra.modules.mechanism.web;

import com.bra.common.upload.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaobin on 15/4/14.
 */
@Controller
@RequestMapping("/mechanism/token")
public class TokenController {

    static final String FILE_NAME_FIELD = "name";
    static final String FILE_SIZE_FIELD = "size";
    public static final String TOKEN_FIELD = "token";
    public static final String SUCCESS = "success";
    static final String MESSAGE = "message";

    @RequestMapping(value = "/file", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> execute(HttpServletRequest req,
                                       HttpServletResponse res) throws IOException {
        String name = req.getParameter(FILE_NAME_FIELD);
        String size = req.getParameter(FILE_SIZE_FIELD);
        String token = generateToken(name, size);
        Map<String, Object> json = new HashMap<String, Object>();
        json.put(TOKEN_FIELD, token);
        json.put(SUCCESS, true);
        json.put(MESSAGE, "");
        return json;
    }

    private String generateToken(String name, String size) throws IOException {
        if (name == null || size == null)
            return "";
        int code = name.hashCode();
        try {
            String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_"
                    + size.trim();
            /** TODO: store your token, here just create a file */
            fileRepository.storeToken(token);

            return token;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Autowired
    private FileRepository fileRepository;

}