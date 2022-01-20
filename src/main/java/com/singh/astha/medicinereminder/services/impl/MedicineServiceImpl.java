package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.transformers.MedicineDtoTransformer;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.models.MedicineCategory;
import com.singh.astha.medicinereminder.repository.CategoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineCategoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineDtoTransformer medicineDtoTransformer;
    private final CategoryRepository categoryRepository;
    private final MedicineCategoryRepository medicineCategoryRepository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository, MedicineDtoTransformer medicineDtoTransformer, CategoryRepository categoryRepository, MedicineCategoryRepository medicineCategoryRepository) {
        this.medicineRepository = medicineRepository;
        this.medicineDtoTransformer = medicineDtoTransformer;
        this.categoryRepository = categoryRepository;
        this.medicineCategoryRepository = medicineCategoryRepository;
    }

    @Override
    public MedicineResponseDto addMedicine(MedicineRequestDto medicineRequestDto, Long userId) {
        Medicine medicine = medicineDtoTransformer.convertMedicineRequestDtoToMedicine(medicineRequestDto, userId);
        Optional<Medicine> optionalMedicine = medicineRepository.findByName(medicineRequestDto.getName());
        if (optionalMedicine.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Same Medicine is already exist");
        }
        Medicine savedMedicine = medicineRepository.save(medicine);
        if (medicineRequestDto.getCategoryId() != null) {
            Optional<Category> categoryOptional =
                    categoryRepository.findByIdAndDeletedAndUserId(medicineRequestDto.getCategoryId(), false, userId);
            if (categoryOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category doesn't exist");
            }
            MedicineCategory medicineCategory = new MedicineCategory();
            medicineCategory.setMedicineId(savedMedicine.getId());
            medicineCategory.setCategoryId(medicineRequestDto.getCategoryId());
            medicineCategoryRepository.save(medicineCategory);
        }
        return medicineDtoTransformer.convertMedicineToMedicineResponseDto(savedMedicine);
    }

    @Override
    public MedicineResponseDto updateMedicine(Long medicineId, MedicineRequestDto medicineRequestDto, Long userId) {
        Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndDeletedAndUserId(medicineId, false, userId);
        if (optionalMedicine.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Medicine not exist");
        }
        Medicine medicine = medicineDtoTransformer.convertMedicineRequestDtoToMedicine(medicineRequestDto, userId);
        Medicine updatedMedicine = medicineRepository.save(medicine);
        return medicineDtoTransformer.convertMedicineToMedicineResponseDto(updatedMedicine);
    }

    @Override
    public List<MedicineResponseDto> listAllMedicine(Long userId) {
        List<Medicine> medicineOptional = medicineRepository.findByUserIdAndDeleted(userId, false);
        return medicineOptional.stream()
                .map(medicineDtoTransformer::convertMedicineToMedicineResponseDto)
                .collect(Collectors.toList());
    }
}
