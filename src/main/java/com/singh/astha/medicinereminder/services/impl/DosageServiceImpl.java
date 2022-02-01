package com.singh.astha.medicinereminder.services.impl;

import com.singh.astha.medicinereminder.dtos.RequestDto.DosageHistoryRequestDto;
import com.singh.astha.medicinereminder.dtos.transformers.DosageDtoTransformer;
import com.singh.astha.medicinereminder.enums.DosageType;
import com.singh.astha.medicinereminder.exceptions.ResponseException;
import com.singh.astha.medicinereminder.models.DosageHistory;
import com.singh.astha.medicinereminder.models.Medicine;
import com.singh.astha.medicinereminder.repository.DosageHistoryRepository;
import com.singh.astha.medicinereminder.repository.MedicineRepository;
import com.singh.astha.medicinereminder.services.DosageService;
import com.singh.astha.medicinereminder.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DosageServiceImpl implements DosageService {

    private final MedicineRepository medicineRepository;
    private final DosageDtoTransformer dosageDtoTransformer;
    private final DosageHistoryRepository dosageHistoryRepository;

    public DosageServiceImpl(MedicineRepository medicineRepository,
                             DosageDtoTransformer dosageDtoTransformer,
                             DosageHistoryRepository dosageHistoryRepository) {
        this.medicineRepository = medicineRepository;
        this.dosageDtoTransformer = dosageDtoTransformer;
        this.dosageHistoryRepository = dosageHistoryRepository;
    }

    @Override
    public void consumeMedicine(Long userId, DosageHistoryRequestDto dosageHistoryRequestDto) {
        DosageHistory dosageHistory = dosageDtoTransformer.convertDosageHistoryRequestDtoToDosageHistory(
                dosageHistoryRequestDto, userId);
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
    }
}
