package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.error;
import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webank.servicemanagement.commons.AppProperties;
import com.webank.servicemanagement.commons.ApplicationConstants.ApiInfo;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.DoneServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.service.ServiceRequestService;

@RestController
@RequestMapping(ApiInfo.API_VERSION_V1 + ApiInfo.API_RESOURCE_SERVICE_REQUEST)
public class ServiceRequestController {
    @Autowired
    ServiceRequestService serviceRequestService;

    @Autowired
    AppProperties appProperties;

    @PostMapping
    public JsonResponse createServiceRequest(@RequestBody CreateServiceRequestRequest request) throws Exception {
        try {
            serviceRequestService.createNewServiceRequest(request);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return okay();
    }

    @PostMapping("/query")
    public JsonResponse queryServiceRequest(@RequestBody QueryRequest queryRequest, HttpServletRequest httpRequest)
            throws Exception {
        return okayWithData(serviceRequestService.queryServiceRequest(queryRequest));
    }

    @PostMapping(ApiInfo.CALLBACK_URL_OF_REPORT_SERVICE_REQUEST)
    public JsonResponse updateServiceRequest(@RequestBody DoneServiceRequestRequest request,
            HttpServletRequest httpRequest) throws Exception {
        try {
            serviceRequestService.doneServiceRequest(request);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return okay();
    }

    @PostMapping("/attach-file")
    public JsonResponse uploadServiceRequestAttachFile(@RequestParam(value = "file") MultipartFile attachFile)
            throws Exception {
        String attachFileId;
        try {
            attachFileId = serviceRequestService.uploadServiceRequestAttachFile(attachFile);
        } catch (Exception e) {
            return error(e.getMessage());
        }

        return okayWithData(attachFileId);
    }

    @GetMapping("/{service-request-id}/attach-file")
    public void downloadServiceRequestAttachFile(@PathVariable(value = "service-request-id") String serviceRequestId,
            HttpServletResponse response) throws Exception {
        if (serviceRequestId == null || serviceRequestId.isEmpty())
            throw new Exception("Invalid service-request-id: " + serviceRequestId);
        try {
            ServletOutputStream out = response.getOutputStream();
            DownloadAttachFileResponse attachFileInfo = serviceRequestService
                    .downloadServiceRequestAttachFile(serviceRequestId);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + attachFileInfo.getAttachFileName());
            response.setHeader("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            out.write(attachFileInfo.getFileByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            String errorMessage = String.format("Failed to download attach file(service request Id:%d) due to %s ",
                    serviceRequestId, e.getMessage());
            throw new Exception(errorMessage);
        }
    }

}
