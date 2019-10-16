package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.ServiceCatalogue;

public class UpdateServiceCatalogueRequest {
	private String name;
	private String description;
	private String status;

	public static ServiceCatalogue toDomain(UpdateServiceCatalogueRequest updateServiceCatalogueRequest,
			ServiceCatalogue existedServiceCatalogue) {
		ServiceCatalogue serviceCatalogue = existedServiceCatalogue;
		if (serviceCatalogue == null) {
			serviceCatalogue = new ServiceCatalogue();
		}

		if (updateServiceCatalogueRequest.getName() != null) {
			serviceCatalogue.setName(updateServiceCatalogueRequest.getName());
		}

		if (updateServiceCatalogueRequest.getDescription() != null) {
			serviceCatalogue.setDescription(updateServiceCatalogueRequest.getDescription());
		}

		if (updateServiceCatalogueRequest.getStatus() != null) {
			serviceCatalogue.setStatus(updateServiceCatalogueRequest.getStatus());
		}

		return serviceCatalogue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
