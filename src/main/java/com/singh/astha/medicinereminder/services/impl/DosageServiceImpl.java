package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.DosageHistoryResponseDto;
import com.singh.astha.medicinereminder.dtos.ResponseDto.MedicineResponseDto;
import com.singh.astha.medicinereminder.dtos.transformers.DosageDtoTransformer;
import com.singh.astha.medicinereminder.dtos.transformers.MedicineDtoTransformer;
import com.singh.astha.medicinereminder.enums.DosageType;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import com.singh.astha.medicinereminder.models.DosageHistory;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.repository.DosageHistoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.services.DosageService;
import com.singh.astha.medicinereminder.utils.Constants;
import com.singh.astha.medicinereminder.utils.ErrorMessages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DosageServiceImpl implements DosageService {

    private final MedicineRepository medicineRepository;
    private final DosageDtoTransformer dosageDtoTransformer;
    private final DosageHistoryRepository dosageHistoryRepository;
    private final MedicineDtoTransformer medicineDtoTransformer;

    public DosageServiceImpl(MedicineRepository medicineRepository,
                             DosageDtoTransformer dosageDtoTransformer,
                             DosageHistoryRepository dosageHistoryRepository,
                             MedicineDtoTransformer medicineDtoTransformer) {
        this.medicineRepository = medicineRepository;
        this.dosageDtoTransformer = dosageDtoTransformer;
        this.dosageHistoryRepository = dosageHistoryRepository;
        this.medicineDtoTransformer = medicineDtoTransformer;
    }

    @Override
    public MedicineResponseDto updateDosage(Long userId, DosageHistoryRequestDto dosageHistoryRequestDto) {
        DosageHistory dosageHistory;
        try {
            dosageHistory = dosageDtoTransformer.convertDosageHistoryRequestDtoToDosageHistory(
                    dosageHistoryRequestDto, userId);
        } catch (Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.DOSAGE_TYPE_IS_NOT_CORRECT);
        }
        Optional<Medicine> optionalMedicine =
                medicineRepository.findByIdAndUserIdAndDeleted(dosageHistoryRequestDto.getMedicineId(), userId, false);
        if (optionalMedicine.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.MEDICINE_NOT_EXIST);
        }
        Medicine medicine = optionalMedicine.get();
        if (dosageHistory.getType() == DosageType.CONSUMPTION) {
            if (medicine.getCurrentDosage() < dosageHistoryRequestDto.getDosage()) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.MEDICINE_IS_NOT_SUFFICIENT);
            }
            medicine.setCurrentDosage(medicine.getCurrentDosage() - dosageHistory.getDosage());
        } else if (dosageHistory.getType() == DosageType.REFILL) {
            medicine.setCurrentDosage(medicine.getCurrentDosage() + dosageHistory.getDosage());
        }
        medicine = medicineRepository.save(medicine);
        dosageHistory.setMedicine(medicine);
        dosageHistoryRepository.save(dosageHistory);
        return medicineDtoTransformer.convertMedicineToMedicineResponseDto(medicine);
    }

    @Override
    public List<DosageHistoryResponseDto> getDosage(Long userId, Integer page, Integer pageSize, Long medicineId) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Constants.ID));
        Page<DosageHistory> dosageHistoryPage = dosageHistoryRepository.findByUserIdAndMedicineIdAndDeleted(pageRequest,
                userId, medicineId, false);

        Set<Long> medicineIds = dosageHistoryPage.stream().map(DosageHistory::getMedicineId)
                .collect(Collectors.toSet());

        List<Medicine> medicineList = medicineRepository.findAll(userId, medicineIds);
        Map<Long, MedicineResponseDto> medicineResponseDtoMap = medicineList.stream()
                .collect(Collectors.toMap(Medicine::getId, medicine -> {
                    MedicineResponseDto medicineResponseDto = medicineDtoTransformer.convertMedicineToMedicineResponseDto(
                            medicine);
                    medicineResponseDto.setDeleted(medicine.getDeleted());
                    return medicineResponseDto;
                }));

        return dosageHistoryPage.stream().map(dosage -> {
            MedicineResponseDto medicineResponseDto = medicineResponseDtoMap.get(dosage.getMedicineId());
            return dosageDtoTransformer.convertDosageHistoryToDosageHistoryResponseDto(dosage, medicineResponseDto);
        }).collect(Collectors.toList());
    }
}
