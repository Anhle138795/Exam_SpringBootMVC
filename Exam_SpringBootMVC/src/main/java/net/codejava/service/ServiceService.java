package net.codejava.service;

import net.codejava.model.Services;
import net.codejava.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public Services getServiceById(int id) {
        return serviceRepository.findById(id);
    }

    public boolean addService(Services service) {
        if (serviceRepository.existsByName(service.getServiceName())) {
            return false; // Indicates duplicate
        }
        serviceRepository.save(service);
        return true;
    }

    public void updateService(Services service) {
        serviceRepository.update(service);
    }

    public void deleteService(int id) {
        serviceRepository.deleteById(id);
    }
}
