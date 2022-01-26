package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.RequestDto.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.transformers.MedicineDtoTransformer;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.models.MedicineCategory;
import com.singh.astha.medicinereminder.repository.CategoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineCategoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.services.MedicineService;
import com.singh.astha.medicinereminder.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.SAME_MEDICINE_IS_ALREADY_EXIST);
        }
        Medicine savedMedicine = medicineRepository.save(medicine);
        return medicineDtoTransformer.convertMedicineToMedicineResponseDto(savedMedicine);
    }

    @Override
    public MedicineResponseDto updateMedicine(Long medicineId, MedicineRequestDto medicineRequestDto, Long userId) {
        Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndUserIdAndDeleted(medicineId, userId, false);
        if (optionalMedicine.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.MEDICINE_NOT_EXIST);
        }
        Medicine medicine = optionalMedicine.get();
       medicineDtoTransformer.copyProperties(medicine,medicineRequestDto);
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
