package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.RequestDto.MedicineRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.CategoryResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.transformers.CategoryDtoTransformer;
import com.singh.astha.medicinereminder.dtos.transformers.MedicineDtoTransformer;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.models.MedicineCategory;
import com.singh.astha.medicinereminder.repository.CategoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineCategoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.services.MedicineService;
import com.singh.astha.medicinereminder.utils.Constants;
import com.singh.astha.medicinereminder.utils.ErrorMessages;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final MedicineDtoTransformer medicineDtoTransformer;
    private final CategoryRepository categoryRepository;
    private final MedicineCategoryRepository medicineCategoryRepository;
    private final CategoryDtoTransformer categoryDtoTransformer;

    @Autowired
    public MedicineServiceImpl(MedicineRepository medicineRepository, MedicineDtoTransformer medicineDtoTransformer,
                               CategoryRepository categoryRepository,
                               MedicineCategoryRepository medicineCategoryRepository,
                               CategoryDtoTransformer categoryDtoTransformer) {
        this.medicineRepository = medicineRepository;
        this.medicineDtoTransformer = medicineDtoTransformer;
        this.categoryRepository = categoryRepository;
        this.medicineCategoryRepository = medicineCategoryRepository;
        this.categoryDtoTransformer = categoryDtoTransformer;
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
        medicineDtoTransformer.copyProperties(medicine, medicineRequestDto);
        Medicine updatedMedicine = medicineRepository.save(medicine);
        return medicineDtoTransformer.convertMedicineToMedicineResponseDto(updatedMedicine);
    }

    @Override
    public List<MedicineResponseDto> listAllMedicine(Long userId, Integer page, Integer pageSize,
                                                     Long categoryId) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Constants.ID));
        Page<Medicine> medicinePage = medicineRepository.findAll(pageRequest, userId, categoryId, false);
        return medicinePage.stream()
                .map(medicineDtoTransformer::convertMedicineToMedicineResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCategoryOfMedicine(Long userId, Long medicineId, Set<Long> categoriesId) {
        Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndUserIdAndDeleted(medicineId, userId, false);
        if (optionalMedicine.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.MEDICINE_NOT_EXIST);
        }

        List<Category> allCategories = categoryRepository.findAll(userId, categoriesId, false);
        if (allCategories.size() != categoriesId.size()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.ALL_CATEGORY_IDS_ARE_NOT_FOUND);
        }

        List<MedicineCategory> medicineCategoryList = medicineCategoryRepository.findByMedicineId(medicineId);
        for (MedicineCategory medicineCategory : medicineCategoryList) {
            if (categoriesId.contains(medicineCategory.getCategory().getId())) {
                if (Boolean.TRUE.equals(medicineCategory.getDeleted())) {
                    medicineCategory.setDeleted(false);
                }
                categoriesId.remove(medicineCategory.getCategory().getId());
            } else {
                medicineCategory.setDeleted(true);
            }
        }
        categoriesId.forEach(id -> {
            MedicineCategory medicineCategory = new MedicineCategory();
            Medicine medicine = new Medicine();
            medicine.setId(medicineId);

            medicineCategory.setMedicine(medicine);
            Category category = new Category();
            category.setId(id);
            medicineCategory.setCategory(category);
            medicineCategoryList.add(medicineCategory);
        });
        medicineCategoryRepository.saveAll(medicineCategoryList);

    }

    @Override
    public MedicineResponseDto getMedicine(Long userId, Long medicineId, boolean fetchCategories) {
        Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndUserIdAndDeleted(medicineId, userId, false);
        if (optionalMedicine.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.MEDICINE_NOT_EXIST);
        }

        Medicine medicine = optionalMedicine.get();
        MedicineResponseDto medicineResponseDto = medicineDtoTransformer.convertMedicineToMedicineResponseDto(medicine);
        if (fetchCategories) {
            List<Category> categories = categoryRepository.findByMedicineId(medicineId, userId, false);
            List<CategoryResponseDto> categoryResponseDtos = categories.stream()
                    .map(categoryDtoTransformer::convertCategoryToCategoryResponseDto)
                    .collect(Collectors.toList());
            medicineResponseDto.setCategories(categoryResponseDtos);
        }
        return medicineResponseDto;
    }

    @Override
    public void deleteMedicine(Long medicineId, Long userId) {
        Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndUserIdAndDeleted(medicineId, userId, false);
        if (optionalMedicine.isEmpty()) {
            return;
        }
        Medicine medicine = optionalMedicine.get();
        JSONObject meta = medicine.getMeta() == null ? new JSONObject() : new JSONObject(medicine.getMeta());
        meta.put(Constants.NAME, medicine.getName());
        medicine.setName(UUID.randomUUID().toString());
        medicine.setMeta(meta.toString());
        medicine.setDeleted(true);
        medicineRepository.save(medicine);
        List<MedicineCategory> medicineCategoryList = medicineCategoryRepository.findByMedicineId(medicineId);
        medicineCategoryList.forEach(medicineCategory -> medicineCategory.setDeleted(true));
        medicineCategoryRepository.saveAll(medicineCategoryList);
    }

    @Override
    public List<String> searchMedicine(Long userId, String medicineName) {
        List<String> medicine = medicineRepository.searchMedicine(userId, medicineName);
        return medicine;
    }

}
