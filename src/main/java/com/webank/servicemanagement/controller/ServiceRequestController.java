package com.webank.servicemanagement.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.webank.servicemanagement.commons.AppProperties;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.dto.CompletedServiceRequestRequest;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.service.ServiceRequestService;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

	private final static long ATTACH_FILE_MAX_SIZE = 16 * 1024 * 1024;

	@Autowired
	ServiceRequestService serviceRequestService;
	@Autowired
	EntityRepository entityRepository;
	@Autowired
	AppProperties appProperties;

	@PostMapping("/create")
	public JsonResponse createServiceRequest(@RequestBody CreateServiceRequestRequest request,
			HttpServletRequest httpRequest) throws Exception {
		serviceRequestService.createNewServiceRequest(httpRequest.getHeader("Current_User"), request);
		return okay();
	}

	@GetMapping("/retrieve")
	public JsonResponse getAllServiceRequest(HttpServletRequest httpRequest) {
		return okayWithData(serviceRequestService.getAllServiceRequest());
	}

	@PostMapping("/query")
	public JsonResponse queryServiceRequest(@RequestBody QueryRequest queryRequest, HttpServletRequest httpRequest)
			throws Exception {
		return okayWithData(entityRepository.query(ServiceRequest.class, queryRequest));
	}

	@PutMapping("/{service-request-id}/update")
	public JsonResponse updateServiceRequest(@PathVariable(value = "service-request-id") int serviceRequestId,
			@RequestBody CompletedServiceRequestRequest request, HttpServletRequest httpRequest) throws Exception {
		serviceRequestService.completedServiceRequest(serviceRequestId, request);
		return okay();
	}

	@PostMapping("/{service-request-id}/attach-file/upload")
	public JsonResponse uploadServiceRequestAttachFile(@RequestParam(value = "file") MultipartFile attachFile)
			throws Exception {
		if (attachFile == null || attachFile.isEmpty())
			throw new IllegalArgumentException("File is required.");
		if (attachFile.getSize() > ATTACH_FILE_MAX_SIZE)
			throw new IllegalArgumentException("File greater than 16Mb are not supported");

		int attachFileId = serviceRequestService.uploadServiceRequestAttachFile(attachFile.getInputStream(),
				attachFile.getOriginalFilename());
		return okayWithData(attachFileId);
	}

	@GetMapping("/{service-request-id}/attach-file/download")
	public void downloadServiceRequestAttachFile(@PathVariable(value = "service-request-id") int serviceRequestId,
			HttpServletResponse response) throws Exception {
		if (serviceRequestId <= 0)
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
			out.write(attachFileInfo.getFileResponseEntity().getBody());
			out.flush();
			out.close();
		} catch (Exception e) {
			String errorMessage = String.format("Failed to download attach file(service request Id:%d) due to %s ",
					serviceRequestId, e.getMessage());
			throw new Exception(errorMessage);
		}

	}

}
