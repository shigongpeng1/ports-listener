package com.port.portlistener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/")
@Controller
public class PortListener {
    @SneakyThrows
    @RequestMapping(value={"/v1/*","/queryService*"})
    public void delPort(HttpServletRequest request, HttpServletResponse response){
        Enumeration<String> names = request.getParameterNames();
        String uri = request.getRequestURI();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        ObjectMapper jsonMapper = new ObjectMapper();
        Map result = new HashMap<String,String>(){{
            put("uri",uri);
        }};
        StringBuilder param = new StringBuilder();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            param.append(name).append("=").append(request.getParameter(name)).append("&");
        }
        result.put("param",param.toString());
        byte[] json = jsonMapper.writeValueAsBytes(result);
        response.getOutputStream().write(json);
    }
}
