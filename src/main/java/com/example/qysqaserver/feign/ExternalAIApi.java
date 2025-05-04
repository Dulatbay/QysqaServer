package com.example.qysqaserver.feign;


import com.example.qysqaserver.entities.topic.components.base.BaseNode;
import com.example.qysqaserver.feign.dto.UploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(name = "${application.external-ai-api.name:file-api}", url = "${application.external-ai-api.url:http://127.0.0.1:8080/api/files}")
public interface ExternalAIApi {
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/process-pdf/",
            produces = "*/*",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<BaseNode> uploadFiles(@RequestBody HashMap<String, String> body);
}
