package no.hvl.dat250.app.controllers;

import no.hvl.dat250.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/protected")
public class ProtectedController {

    @Autowired private SecurityService securityService;

    @GetMapping("/data")
    public Map<String, Object> getData() {
        String name = securityService.getUser().getName();
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("msg", "Hello " + name + ", you have accessed protected data from spring boot");
        return response;
    }

}
