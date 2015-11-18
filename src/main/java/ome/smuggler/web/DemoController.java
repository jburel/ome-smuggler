package ome.smuggler.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import ome.smuggler.core.service.ImportRequestor;
import ome.smuggler.core.types.ImportId;
import ome.smuggler.core.types.ImportInput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController  // includes @ResponseBody: return vals bound to response body.
@RequestMapping("/demo")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class DemoController {

    @Autowired
    private ImportRequestor service;
    
    private ImportInput buildInput(ImportRequest requestInput, 
                                   ImportRequestValidator validator) {
        return new ImportInput(
                validator.getEmail(), 
                validator.getTarget(), 
                validator.getOmero(), 
                validator.getSession())
            .setName(requestInput.name)
            .setDescription(requestInput.description)
            .setDatasetId(validator.getDatasetId())
            .setScreenId(validator.getScreenId())
            .addTextAnnotations(validator.getTextAnnotations().stream())
            .addAnnotationIds(validator.getAnnotationIds().stream());
    }
    
    private ImportResponse responseBody(ImportId task) {
        ImportResponse responseBody = new ImportResponse();
        responseBody.statusUri = "http://localhost:8080/ome/import/" + task.id();  
        return responseBody;
    }
    
    @RequestMapping(method = GET)
    public String triggerImport(@RequestParam(value="file") String file) {
        ImportRequest r = new ImportRequest();
        r.experimenterEmail = "andrea.falconi@igh.cnrs.fr";
        r.omeroHost = "localhost";
        r.omeroPort = "4064";
        r.sessionKey = "k";
        r.targetUri = file;
        
        ImportRequestValidator validator = new ImportRequestValidator();
        return validator
                .validate(r)
                .map(x -> buildInput(r, validator))
                .map(service::enqueue)
                .map(taskId -> responseBody(taskId))
                .getRight()
                .statusUri;
    }
    
}
