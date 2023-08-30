package com.devmo.mohelper.api.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    String id;
    String page;

    String mealName;
    String type;
    List<Data> mealComponents;
    List<Data> rawMaterial;
    List<Data> holdRelese;
    List<Data> archiveData;
    Data mealProcess;
    String mealId;
    String itemId;
    String itemName;
    String productionDate;
    String expiryDate;
    String brandName;
    String signatureOfQc2;
    String note;
    String processLevelCode;
    String userShiftEnd;
    String process;
    String userId;
    String userName;
    String password;
    String averageWeight;
    String typeId;
    String typeName;
    String lastTransaction;
    String block;
    String mealType;
    String status;
    String createBy;
    String shift;
    String dateStart;
    String dateEnd;
    String deleteBy;
    String incubationTestsC;
    String processLevel;
    String reviewedBy;
    String dateTimeStart;
    String productionNum;
    String userShift;
    String dateTimeEnd;
    String soakingTime;
    String soakingTempWater;
    String productTexture;
    String GX7_Conc;
    String PH_WashingWater;
    String PressureMmHg;
    String coreProductTemperature;
    String rotationSpeed;
    String temperatureOfTumbler;
    String vaccumPressureValue;
    String deliveredTo;
    String tempC;
    String oilType;
    String oilTemperature;
    String tpm;
    String cookingTemperature;
    String cookingTime;
    String productTemperature;
    String labelInformation;
    String foodAdditiveName;
    String waterQuantity;
    String weightOfAdditive;
    String percentage;
    String moisture;
    String totalSoilds;
    String ph;
    String salt;
    String brix;
    String acidity;
    String typeForeignBody;
    String viscosityCP;
    String taste;
    String color;
    String texture;
    String smell;
    String area;
    String appearance;
    String productTemperatureBefore;
    String productTemperatureAfte;
    String chillersTemperatureC;
    String hotHoldingMethod;
    String qtyKg;
    String trolliesOrContainers;
    String fillingLineName;
    String batchNumber;
    String batchNumber2;
    String packageType;
    String thermoformWidthTop;
    String thermoformWidthSup;
    String thermoformBase;
    String thermoformLength;
    String thermoformThicknessTop;
    String thermoformThicknessSup;
    String aluTrayWidth;
    String aluTrayBase;
    String aluTrayThickness;
    String aluTrayLength;
    String lidWidth;
    String lidBase;
    String lidThickness;
    String lidLength;
    String pouchWidth;
    String pouchBase;
    String pouchThickness;
    String pouchLength;
    String packWeight;
    String machinePart1;
    String reading1;
    String result1;
    String machinePart2;
    String reading2;
    String result2;
    String sealingTempUpper;
    String sealingTempLower;
    String sealingTemp2;
    String sealingTemp1;
    String leakTestFlow;
    String leakTestMicron;
    String leakTestExplosion;
    String testRodeType;
    String testResults;
    String numberRejections;
    String productStandardWeight;
    String testedSampleWeight;
    String deviationAccuracy;
    String autoclaveNo;
    String autoclaveMaxTempSterilization;
    String maxPressure;
    String autoclaveTimeSterilization;
    String autoclaveTimeCooling;
    String autoclaveTimeTotal;
    String productExternalDryness;
    String printingCheck;
    String quantity;
    String productQuality;
    String productActualTemp;
    String seneorTemp;

    String metalizedFilmlength;
    String metalizedFilmWidth;
    String metalizedFilmBase;
    String metalizedFilmThickness;


    String imageUrl;
    String Reading2;
    String packType;


    String reviewNote;
    String mainNote;
    String premixWeight;
    String sugarWeight;
    String totalWeight;
    String sugarQualityDiameter;
    String sealingTestExaminationResult;
    String productExternalCondition;
    String heatStampCheck;
    String lastUpdate;
    String mealAccessoriesRationName;
    String packagingLineName;
    String methodOfPackaging;
    String typeOfPack;
    String width_mm;
    String length_mm;
    String height_mm;
    String batch_number;
    String packagingLabelType;
    String sealingTestResult;
    String hygieneCheckNote;
    String printedDataCheck;
    String productexternalCondition;
    String userShiftStart;

    String qcEnd;
    String qcReview;
    String shiftReview;
    String shiftEnd;
    String shiftStart;
    String dateReview;

    String formDate;
    String signatureOfQc;
    String finishedProductName;
    String primaryPackageType;
    String manufactureName;
    String origin;
    String batchNo;
    String storagePlace;
    String reasonForHoldOnProduct;
    String periodOfHold;
    String microbialTestsResultOn;
    String incubationTests;
    String outsideLabResultDate;
    String quantityOfReleasedFinishedProducts;
    String quantityOfNonConfirmingFinishedProduct;
    String notes;
    String actionTaken;
    String dateOfTheActionTakenWasDone;
    String reviewedDate;

    public List<Data> getArchiveData() {
        return archiveData;
    }

    public void setArchiveData(List<Data> archiveData) {
        this.archiveData = archiveData;
    }

    public String getIncubationTestsC() {
        return incubationTestsC;
    }

    public void setIncubationTestsC(String incubationTestsC) {
        this.incubationTestsC = incubationTestsC;
    }

    public String getFormDate() {
        return formDate;
    }

    public String getSignatureOfQc() {
        return signatureOfQc;
    }

    public String getFinishedProductName() {
        return finishedProductName;
    }

    public String getPrimaryPackageType() {
        return primaryPackageType;
    }

    public String getManufactureName() {
        return manufactureName;
    }

    public String getOrigin() {
        return origin;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public String getReasonForHoldOnProduct() {
        return reasonForHoldOnProduct;
    }

    public String getPeriodOfHold() {
        return periodOfHold;
    }

    public String getMicrobialTestsResultOn() {
        return microbialTestsResultOn;
    }

    public String getIncubationTests() {
        return incubationTests;
    }

    public String getOutsideLabResultDate() {
        return outsideLabResultDate;
    }

    public String getQuantityOfReleasedFinishedProducts() {
        return quantityOfReleasedFinishedProducts;
    }

    public String getQuantityOfNonConfirmingFinishedProduct() {
        return quantityOfNonConfirmingFinishedProduct;
    }

    public String getNotes() {
        return notes;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public String getDateOfTheActionTakenWasDone() {
        return dateOfTheActionTakenWasDone;
    }

    public String getReviewedDate() {
        return reviewedDate;
    }

    List<Data> Kitchen, Production1, Production2, Packaging;

    boolean open, swipe, copy;
    private int adapterType;
    private String poNumber;

    public Data() {
    }

    public String getProductQuality() {
        return productQuality;
    }

    public void setProductQuality(String productQuality) {
        this.productQuality = productQuality;
    }

    public String getHygieneCheckNote() {
        return hygieneCheckNote;
    }

    public void setHygieneCheckNote(String hygieneCheckNote) {
        this.hygieneCheckNote = hygieneCheckNote;
    }

    public String getProductActualTemp() {
        return productActualTemp;
    }

    public void setProductActualTemp(String productActualTemp) {
        this.productActualTemp = productActualTemp;
    }

    public List<Data> getKitchen() {
        return Kitchen;
    }

    public List<Data> getProduction1() {
        return Production1;
    }

    public List<Data> getProduction2() {
        return Production2;
    }

    public List<Data> getPackaging() {
        return Packaging;
    }

    public String getSeneorTemp() {
        return seneorTemp;
    }

    public void setSeneorTemp(String seneorTemp) {
        this.seneorTemp = seneorTemp;
    }

    public String getMetalizedFilmlength() {
        return metalizedFilmlength;
    }

    public void setMetalizedFilmlength(String metalizedFilmlength) {
        this.metalizedFilmlength = metalizedFilmlength;
    }

    public String getMetalizedFilmWidth() {
        return metalizedFilmWidth;
    }

    public void setMetalizedFilmWidth(String metalizedFilmWidth) {
        this.metalizedFilmWidth = metalizedFilmWidth;
    }

    public String getQcEnd() {
        return qcEnd;
    }

    public void setQcEnd(String qcEnd) {
        this.qcEnd = qcEnd;
    }

    public String getQcReview() {
        return qcReview;
    }

    public void setQcReview(String qcReview) {
        this.qcReview = qcReview;
    }

    public String getShiftReview() {
        return shiftReview;
    }

    public void setShiftReview(String shiftReview) {
        this.shiftReview = shiftReview;
    }

    public String getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(String shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public String getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(String shiftStart) {
        this.shiftStart = shiftStart;
    }

    public String getDateReview() {
        return dateReview;
    }

    public void setDateReview(String dateReview) {
        this.dateReview = dateReview;
    }

    public String getMetalizedFilmBase() {
        return metalizedFilmBase;
    }

    public void setMetalizedFilmBase(String metalizedFilmBase) {
        this.metalizedFilmBase = metalizedFilmBase;
    }

    public String getMetalizedFilmThickness() {
        return metalizedFilmThickness;
    }

    public void setMetalizedFilmThickness(String metalizedFilmThickness) {
        this.metalizedFilmThickness = metalizedFilmThickness;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getPremixWeight() {
        return premixWeight;
    }

    public void setPremixWeight(String premixWeight) {
        this.premixWeight = premixWeight;
    }

    public String getSugarWeight() {
        return sugarWeight;
    }

    public void setSugarWeight(String sugarWeight) {
        this.sugarWeight = sugarWeight;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getSugarQualityDiameter() {
        return sugarQualityDiameter;
    }

    public void setSugarQualityDiameter(String sugarQualityDiameter) {
        this.sugarQualityDiameter = sugarQualityDiameter;
    }

    public String getSealingTestExaminationResult() {
        return sealingTestExaminationResult;
    }

    public void setSealingTestExaminationResult(String sealingTestExaminationResult) {
        this.sealingTestExaminationResult = sealingTestExaminationResult;
    }

    public String getProductExternalCondition() {
        return productExternalCondition;
    }

    public void setProductExternalCondition(String productExternalCondition) {
        this.productExternalCondition = productExternalCondition;
    }

    public String getHeatStampCheck() {
        return heatStampCheck;
    }

    public void setHeatStampCheck(String heatStampCheck) {
        this.heatStampCheck = heatStampCheck;
    }

    public String getUserShiftEnd() {
        return userShiftEnd;
    }

    public void setUserShiftEnd(String userShiftEnd) {
        this.userShiftEnd = userShiftEnd;
    }

    public String getProcessLevel() {
        return processLevel;
    }

    public String getProcess() {
        return process;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getBatchNumber2() {
        return batchNumber2;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getThermoformWidthTop() {
        return thermoformWidthTop;
    }

    public void setThermoformWidthTop(String thermoformWidthTop) {
        this.thermoformWidthTop = thermoformWidthTop;
    }

    public String getThermoformWidthSup() {
        return thermoformWidthSup;
    }

    public void setThermoformWidthSup(String thermoformWidthSup) {
        this.thermoformWidthSup = thermoformWidthSup;
    }

    public String getThermoformBase() {
        return thermoformBase;
    }

    public void setThermoformBase(String thermoformBase) {
        this.thermoformBase = thermoformBase;
    }

    public String getThermoformLength() {
        return thermoformLength;
    }

    public void setThermoformLength(String thermoformLength) {
        this.thermoformLength = thermoformLength;
    }

    public String getThermoformThicknessTop() {
        return thermoformThicknessTop;
    }

    public void setThermoformThicknessTop(String thermoformThicknessTop) {
        this.thermoformThicknessTop = thermoformThicknessTop;
    }

    public List<Data> getHoldRelese() {
        return holdRelese;
    }

    public void setHoldRelese(List<Data> holdRelese) {
        this.holdRelese = holdRelese;
    }

    public String getThermoformThicknessSup() {
        return thermoformThicknessSup;
    }

    public void setThermoformThicknessSup(String thermoformThicknessSup) {
        this.thermoformThicknessSup = thermoformThicknessSup;
    }

    public String getAluTrayWidth() {
        return aluTrayWidth;
    }

    public void setAluTrayWidth(String aluTrayWidth) {
        this.aluTrayWidth = aluTrayWidth;
    }

    public String getAluTrayBase() {
        return aluTrayBase;
    }

    public void setAluTrayBase(String aluTrayBase) {
        this.aluTrayBase = aluTrayBase;
    }

    public String getAluTrayThickness() {
        return aluTrayThickness;
    }

    public void setAluTrayThickness(String aluTrayThickness) {
        this.aluTrayThickness = aluTrayThickness;
    }

    public String getAluTrayLength() {
        return aluTrayLength;
    }

    public void setAluTrayLength(String aluTrayLength) {
        this.aluTrayLength = aluTrayLength;
    }

    public String getLidWidth() {
        return lidWidth;
    }

    public void setLidWidth(String lidWidth) {
        this.lidWidth = lidWidth;
    }

    public String getLidBase() {
        return lidBase;
    }

    public void setLidBase(String lidBase) {
        this.lidBase = lidBase;
    }

    public String getLidThickness() {
        return lidThickness;
    }

    public void setLidThickness(String lidThickness) {
        this.lidThickness = lidThickness;
    }

    public String getLidLength() {
        return lidLength;
    }

    public void setLidLength(String lidLength) {
        this.lidLength = lidLength;
    }

    public String getPouchWidth() {
        return pouchWidth;
    }

    public void setPouchWidth(String pouchWidth) {
        this.pouchWidth = pouchWidth;
    }

    public String getPouchBase() {
        return pouchBase;
    }

    public void setPouchBase(String pouchBase) {
        this.pouchBase = pouchBase;
    }

    public String getPouchThickness() {
        return pouchThickness;
    }

    public void setPouchThickness(String pouchThickness) {
        this.pouchThickness = pouchThickness;
    }

    public String getPouchLength() {
        return pouchLength;
    }

    public void setPouchLength(String pouchLength) {
        this.pouchLength = pouchLength;
    }

    public String getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(String packWeight) {
        this.packWeight = packWeight;
    }

    public String getMachinePart1() {
        return machinePart1;
    }

    public void setMachinePart1(String machinePart1) {
        this.machinePart1 = machinePart1;
    }

    public String getReading1() {
        return reading1;
    }

    public void setReading1(String reading1) {
        this.reading1 = reading1;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public String getMachinePart2() {
        return machinePart2;
    }

    public void setMachinePart2(String machinePart2) {
        this.machinePart2 = machinePart2;
    }

    public String getReading2() {
        return reading2;
    }

    public void setReading2(String reading2) {
        this.reading2 = reading2;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }

    public String getSealingTempUpper() {
        return sealingTempUpper;
    }

    public void setSealingTempUpper(String sealingTempUpper) {
        this.sealingTempUpper = sealingTempUpper;
    }

    public String getSealingTempLower() {
        return sealingTempLower;
    }

    public void setSealingTempLower(String sealingTempLower) {
        this.sealingTempLower = sealingTempLower;
    }

    public String getSealingTemp1() {
        return sealingTemp1;
    }

    public void setSealingTemp1(String sealingTemp1) {
        this.sealingTemp1 = sealingTemp1;
    }

    public String getSealingTemp2() {
        return sealingTemp2;
    }

    public void setSealingTemp2(String sealingTemp2) {
        this.sealingTemp2 = sealingTemp2;
    }

    public String getLeakTestFlow() {
        return leakTestFlow;
    }

    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }

    public void setMainNote(String mainNote) {
        this.mainNote = mainNote;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public void setSignatureOfQc(String signatureOfQc) {
        this.signatureOfQc = signatureOfQc;
    }

    public void setFinishedProductName(String finishedProductName) {
        this.finishedProductName = finishedProductName;
    }

    public void setPrimaryPackageType(String primaryPackageType) {
        this.primaryPackageType = primaryPackageType;
    }

    public void setManufactureName(String manufactureName) {
        this.manufactureName = manufactureName;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public void setReasonForHoldOnProduct(String reasonForHoldOnProduct) {
        this.reasonForHoldOnProduct = reasonForHoldOnProduct;
    }

    public void setPeriodOfHold(String periodOfHold) {
        this.periodOfHold = periodOfHold;
    }

    public void setMicrobialTestsResultOn(String microbialTestsResultOn) {
        this.microbialTestsResultOn = microbialTestsResultOn;
    }

    public void setIncubationTests(String incubationTests) {
        this.incubationTests = incubationTests;
    }

    public void setOutsideLabResultDate(String outsideLabResultDate) {
        this.outsideLabResultDate = outsideLabResultDate;
    }

    public void setQuantityOfReleasedFinishedProducts(String quantityOfReleasedFinishedProducts) {
        this.quantityOfReleasedFinishedProducts = quantityOfReleasedFinishedProducts;
    }

    public void setQuantityOfNonConfirmingFinishedProduct(String quantityOfNonConfirmingFinishedProduct) {
        this.quantityOfNonConfirmingFinishedProduct = quantityOfNonConfirmingFinishedProduct;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public void setDateOfTheActionTakenWasDone(String dateOfTheActionTakenWasDone) {
        this.dateOfTheActionTakenWasDone = dateOfTheActionTakenWasDone;
    }

    public void setReviewedDate(String reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public void setLeakTestFlow(String leakTestFlow) {
        this.leakTestFlow = leakTestFlow;
    }

    public String getLeakTestMicron() {
        return leakTestMicron;
    }

    public void setLeakTestMicron(String leakTestMicron) {
        this.leakTestMicron = leakTestMicron;
    }

    public String getLeakTestExplosion() {
        return leakTestExplosion;
    }

    public void setLeakTestExplosion(String leakTestExplosion) {
        this.leakTestExplosion = leakTestExplosion;
    }

    public String getTestRodeType() {
        return testRodeType;
    }

    public void setTestRodeType(String testRodeType) {
        this.testRodeType = testRodeType;
    }

    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }

    public String getNumberRejections() {
        return numberRejections;
    }

    public void setNumberRejections(String numberRejections) {
        this.numberRejections = numberRejections;
    }

    public String getProductStandardWeight() {
        return productStandardWeight;
    }

    public void setProductStandardWeight(String productStandardWeight) {
        this.productStandardWeight = productStandardWeight;
    }

    public String getTestedSampleWeight() {
        return testedSampleWeight;
    }

    public void setTestedSampleWeight(String testedSampleWeight) {
        this.testedSampleWeight = testedSampleWeight;
    }

    public String getDeviationAccuracy() {
        return deviationAccuracy;
    }

    public void setDeviationAccuracy(String deviationAccuracy) {
        this.deviationAccuracy = deviationAccuracy;
    }

    public String getAutoclaveNo() {
        return autoclaveNo;
    }

    public void setAutoclaveNo(String autoclaveNo) {
        this.autoclaveNo = autoclaveNo;
    }

    public String getAutoclaveMaxTempSterilization() {
        return autoclaveMaxTempSterilization;
    }

    public void setAutoclaveMaxTempSterilization(String autoclaveMaxTempSterilization) {
        this.autoclaveMaxTempSterilization = autoclaveMaxTempSterilization;
    }

    public String getMaxPressure() {
        return maxPressure;
    }

    public void setMaxPressure(String maxPressure) {
        this.maxPressure = maxPressure;
    }

    public String getAutoclaveTimeSterilization() {
        return autoclaveTimeSterilization;
    }

    public void setAutoclaveTimeSterilization(String autoclaveTimeSterilization) {
        this.autoclaveTimeSterilization = autoclaveTimeSterilization;
    }

    public String getAutoclaveTimeCooling() {
        return autoclaveTimeCooling;
    }

    public void setAutoclaveTimeCooling(String autoclaveTimeCooling) {
        this.autoclaveTimeCooling = autoclaveTimeCooling;
    }

    public String getAutoclaveTimeTotal() {
        return autoclaveTimeTotal;
    }

    public void setAutoclaveTimeTotal(String autoclaveTimeTotal) {
        this.autoclaveTimeTotal = autoclaveTimeTotal;
    }

    public String getProductExternalDryness() {
        return productExternalDryness;
    }

    public void setProductExternalDryness(String productExternalDryness) {
        this.productExternalDryness = productExternalDryness;
    }

    public String getPrintingCheck() {
        return printingCheck;
    }

    public void setPrintingCheck(String printingCheck) {
        this.printingCheck = printingCheck;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMealAccessoriesRationName() {
        return mealAccessoriesRationName;
    }

    public void setMealAccessoriesRationName(String mealAccessoriesRationName) {
        this.mealAccessoriesRationName = mealAccessoriesRationName;
    }

    public String getPackagingLineName() {
        return packagingLineName;
    }

    public void setPackagingLineName(String packagingLineName) {
        this.packagingLineName = packagingLineName;
    }

    public String getMethodOfPackaging() {
        return methodOfPackaging;
    }

    public void setMethodOfPackaging(String methodOfPackaging) {
        this.methodOfPackaging = methodOfPackaging;
    }

    public String getTypeOfPack() {
        return typeOfPack;
    }

    public void setTypeOfPack(String typeOfPack) {
        this.typeOfPack = typeOfPack;
    }

    public String getWidth_mm() {
        return width_mm;
    }

    public void setWidth_mm(String width_mm) {
        this.width_mm = width_mm;
    }

    public String getLength_mm() {
        return length_mm;
    }

    public void setLength_mm(String length_mm) {
        this.length_mm = length_mm;
    }

    public String getHeight_mm() {
        return height_mm;
    }

    public void setHeight_mm(String height_mm) {
        this.height_mm = height_mm;
    }

    public String getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(String averageWeight) {
        this.averageWeight = averageWeight;
    }


    public String getBatch_number() {
        return batch_number;
    }

    public void setBatch_number(String batch_number) {
        this.batch_number = batch_number;
    }

    public String getPackagingLabelType() {
        return packagingLabelType;
    }

    public void setPackagingLabelType(String packagingLabelType) {
        this.packagingLabelType = packagingLabelType;
    }

    public String getSealingTestResult() {
        return sealingTestResult;
    }

    public void setSealingTestResult(String sealingTestResult) {
        this.sealingTestResult = sealingTestResult;
    }

    public String getPrintedDataCheck() {
        return printedDataCheck;
    }

    public void setPrintedDataCheck(String printedDataCheck) {
        this.printedDataCheck = printedDataCheck;
    }

    public String getProductexternalCondition() {
        return productexternalCondition;
    }

    public void setProductexternalCondition(String productexternalCondition) {
        this.productexternalCondition = productexternalCondition;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserShiftStart() {
        return userShiftStart;
    }

    public void setPressureMmHg(String pressureMmHg) {
        PressureMmHg = pressureMmHg;
    }

    public String getPressureMmHg() {
        return PressureMmHg;
    }

    public void setUserShiftStart(String userShiftStart) {
        this.userShiftStart = userShiftStart;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setProcessLevel(String processLevel) {
        this.processLevel = processLevel;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getProductionNum() {
        return productionNum;
    }

    public void setProductionNum(String productionNum) {
        this.productionNum = productionNum;
    }

    public String getUserShift() {
        return userShift;
    }

    public void setUserShift(String userShift) {
        this.userShift = userShift;
    }

    public String getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(String dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public String getSoakingTime() {
        return soakingTime;
    }

    public void setSoakingTime(String soakingTime) {
        this.soakingTime = soakingTime;
    }

    public String getSoakingTempWater() {
        return soakingTempWater;
    }

    public void setSoakingTempWater(String soakingTempWater) {
        this.soakingTempWater = soakingTempWater;
    }

    public String getProductTexture() {
        return productTexture;
    }

    public void setProductTexture(String productTexture) {
        this.productTexture = productTexture;
    }

    public String getGX7_Conc() {
        return GX7_Conc;
    }

    public void setGX7_Conc(String GX7_Conc) {
        this.GX7_Conc = GX7_Conc;
    }

    public String getPH_WashingWater() {
        return PH_WashingWater;
    }

    public void setPH_WashingWater(String PH_WashingWater) {
        this.PH_WashingWater = PH_WashingWater;
    }

    public String getDeliveredTo() {
        return deliveredTo;
    }

    public void setDeliveredTo(String deliveredTo) {
        this.deliveredTo = deliveredTo;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getOilType() {
        return oilType;
    }

    public void setOilType(String oilType) {
        this.oilType = oilType;
    }

    public String getOilTemperature() {
        return oilTemperature;
    }

    public void setOilTemperature(String oilTemperature) {
        this.oilTemperature = oilTemperature;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getSignatureOfQc2() {
        return signatureOfQc2;
    }

    public void setSignatureOfQc2(String signatureOfQc2) {
        this.signatureOfQc2 = signatureOfQc2;
    }

    public String getProcessLevelCode() {
        return processLevelCode;
    }

    public void setProcessLevelCode(String processLevelCode) {
        this.processLevelCode = processLevelCode;
    }

    public String getTpm() {
        return tpm;
    }

    public void setTpm(String tpm) {
        this.tpm = tpm;
    }

    public String getCookingTemperature() {
        return cookingTemperature;
    }

    public void setCookingTemperature(String cookingTemperature) {
        this.cookingTemperature = cookingTemperature;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getProductTemperature() {
        return productTemperature;
    }

    public void setProductTemperature(String productTemperature) {
        this.productTemperature = productTemperature;
    }

    public String getLabelInformation() {
        return labelInformation;
    }

    public void setLabelInformation(String labelInformation) {
        this.labelInformation = labelInformation;
    }

    public String getFoodAdditiveName() {
        return foodAdditiveName;
    }

    public void setFoodAdditiveName(String foodAdditiveName) {
        this.foodAdditiveName = foodAdditiveName;
    }

    public String getWaterQuantity() {
        return waterQuantity;
    }

    public void setWaterQuantity(String waterQuantity) {
        this.waterQuantity = waterQuantity;
    }

    public String getWeightOfAdditive() {
        return weightOfAdditive;
    }

    public void setWeightOfAdditive(String weightOfAdditive) {
        this.weightOfAdditive = weightOfAdditive;
    }

    public String getCoreProductTemperature() {
        return coreProductTemperature;
    }

    public void setCoreProductTemperature(String coreProductTemperature) {
        this.coreProductTemperature = coreProductTemperature;
    }

    public String getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(String rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public String getTemperatureOfTumbler() {
        return temperatureOfTumbler;
    }

    public void setTemperatureOfTumbler(String temperatureOfTumbler) {
        this.temperatureOfTumbler = temperatureOfTumbler;
    }

    public String getVaccumPressureValue() {
        return vaccumPressureValue;
    }

    public void setVaccumPressureValue(String vaccumPressureValue) {
        this.vaccumPressureValue = vaccumPressureValue;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getMoisture() {
        return moisture;
    }

    public void setMoisture(String moisture) {
        this.moisture = moisture;
    }

    public String getTotalSoilds() {
        return totalSoilds;
    }

    public void setTotalSoilds(String totalSoilds) {
        this.totalSoilds = totalSoilds;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getBrix() {
        return brix;
    }

    public void setBrix(String brix) {
        this.brix = brix;
    }

    public String getAcidity() {
        return acidity;
    }

    public void setAcidity(String acidity) {
        this.acidity = acidity;
    }

    public String getViscosityCP() {
        return viscosityCP;
    }

    public void setViscosityCP(String viscosityCP) {
        this.viscosityCP = viscosityCP;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getSmell() {
        return smell;
    }

    public String getTypeForeignBody() {
        return typeForeignBody;
    }

    public void setTypeForeignBody(String typeForeignBody) {
        this.typeForeignBody = typeForeignBody;
    }

    public void setSmell(String smell) {
        this.smell = smell;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getProductTemperatureBefore() {
        return productTemperatureBefore;
    }

    public void setProductTemperatureBefore(String productTemperatureBefore) {
        this.productTemperatureBefore = productTemperatureBefore;
    }

    public String getProductTemperatureAfte() {
        return productTemperatureAfte;
    }

    public void setProductTemperatureAfte(String productTemperatureAfte) {
        this.productTemperatureAfte = productTemperatureAfte;
    }

    public String getChillersTemperatureC() {
        return chillersTemperatureC;
    }

    public void setChillersTemperatureC(String chillersTemperatureC) {
        this.chillersTemperatureC = chillersTemperatureC;
    }

    public String getHotHoldingMethod() {
        return hotHoldingMethod;
    }

    public void setHotHoldingMethod(String hotHoldingMethod) {
        this.hotHoldingMethod = hotHoldingMethod;
    }

    public String getQtyKg() {
        return qtyKg;
    }

    public void setQtyKg(String qtyKg) {
        this.qtyKg = qtyKg;
    }

    public String getTrolliesOrContainers() {
        return trolliesOrContainers;
    }

    public void setTrolliesOrContainers(String trolliesOrContainers) {
        this.trolliesOrContainers = trolliesOrContainers;
    }

    public String getFillingLineName() {
        return fillingLineName;
    }

    public void setFillingLineName(String fillingLineName) {
        this.fillingLineName = fillingLineName;
    }

    public String getMealType() {
        return mealType;
    }

    public String getStatus() {
        return status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getShift() {
        return shift;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMealComponents(List<Data> mealComponents) {
        this.mealComponents = mealComponents;
    }

    public Data getMealProcess() {
        return mealProcess;
    }

    public void setMealProcess(Data mealProcess) {
        this.mealProcess = mealProcess;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setLastTransaction(String lastTransaction) {
        this.lastTransaction = lastTransaction;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setDeleteBy(String deleteBy) {
        this.deleteBy = deleteBy;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDeleteBy() {
        return deleteBy;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getLastTransaction() {
        return lastTransaction;
    }

    public String getBlock() {
        return block;
    }

    public Data(String mealName, String mealId) {
        this.itemName = mealName;
        this.mealId = mealId;
    }

    public boolean isCopy() {
        return copy;
    }

    public void setCopy(boolean copy) {
        this.copy = copy;
    }

    public Data copy() { //Get another instance of YourClass with the values like this!
        String json = new Gson().toJson(this);
        return new Gson().fromJson(json, Data.class);
    }


    public boolean isSwipe() {
        return swipe;
    }

    public void setSwipe(boolean swipe) {
        this.swipe = swipe;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getNote() {
        return note;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getId() {
        return id;
    }

    public String getMealName() {
        return mealName;
    }

    public String getType() {
        return type;
    }

    public List<Data> getMealComponents() {
        return mealComponents;
    }

    public String getMealId() {
        return mealId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setAdapterType(int adapterType) {
        this.adapterType = adapterType;
    }

    public int getAdapterType() {
        return adapterType;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public List<Data> getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(List<Data> rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public String getMainNote() {
        return mainNote;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
