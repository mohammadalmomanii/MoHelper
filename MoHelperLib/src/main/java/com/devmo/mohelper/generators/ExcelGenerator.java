package com.devmo.mohelper.generators;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.devmo.mohelper.api.model.Data;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ExcelGenerator {
    private File filePath;
    private Workbook excelFile = new XSSFWorkbook();
    private int currentIndex = 0;

    public ExcelGenerator(String fileName) {

        filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);


    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void addText(Sheet sheet, int[] index, String text, short fontColor, boolean isBold, short bgColor) {
        Row headerRow;
        if (sheet.getRow(index[0]) == null)
            headerRow = sheet.createRow(index[0]);
        else headerRow = sheet.getRow(index[0]);
        Cell headerCell = headerRow.createCell(index[1]);


        Font headerFont = excelFile.createFont();
        headerCell.setCellValue(text);
        CellStyle headerStyle = excelFile.createCellStyle();
        headerFont.setBold(isBold);
        headerStyle.setFont(headerFont);

        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(bgColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCell.setCellStyle(headerStyle);
        headerFont.setColor(fontColor);

        if (sheet.getColumnWidth(index[1]) < headerCell.getStringCellValue().length() * 300)
            sheet.setColumnWidth(index[1], headerCell.getStringCellValue().length() * 300);
//        headerRow.setHeight(rowHeight);


    }

    public void addTextHeader(Sheet sheet, int[] index, String text, short fontColor, short fontSize, short bgColor) {
        CellStyle headerStyle = excelFile.createCellStyle();
        Font headerFont = excelFile.createFont();
        headerFont.setBold(true);
        headerFont.setColor(fontColor);
        headerFont.setFontHeightInPoints(fontSize); // Set the desired font size in points
        headerStyle.setFont(headerFont);

        headerStyle.setFillForegroundColor(bgColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);


        CellRangeAddress mergedRegion = new CellRangeAddress(index[0], index[1], index[2], index[3]);
        sheet.addMergedRegion(mergedRegion);

        Row mergedRow = sheet.getRow(index[0]);
        if (mergedRow == null) {
            mergedRow = sheet.createRow(index[0]);
        }
        Cell mergedCell = mergedRow.createCell(index[2]);
        mergedCell.setCellValue(text);
        mergedCell.setCellStyle(headerStyle);

        if (sheet.getColumnWidth(index[1]) < mergedCell.getStringCellValue().length() * 300)
            sheet.setColumnWidth(index[1], mergedCell.getStringCellValue().length() * 300);
//...

    }

    public void addImage(Sheet sheet, Drawable drawable, int[] index) {


        sheet.addMergedRegion(new CellRangeAddress(index[0], index[1], index[2], index[3]));


        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imageData = stream.toByteArray();

        int pictureIndex = excelFile.addPicture(imageData, Workbook.PICTURE_TYPE_JPEG);

        Drawing<?> drawing = sheet.createDrawingPatriarch();

        int imageRow = index[0]; // Row index where you want to insert the image (same as the starting row of the merge range)
        int imageColumn = index[2]; // Column index where you want to insert the image (same as the starting column of the merge range)
        int imageWidthUnits = 6 * 256; // Image width in units (1 unit = 1/256th of a character width)
        int imageHeightUnits = 6 * 256; // Image height in units (1 unit = 1/256th of a character height)
        int imageDxUnits = 0; // Offset of the image from the anchor cell in the x-direction
        int imageDyUnits = 0; // Offset of the image from the anchor cell in the y-direction
        ClientAnchor anchor = excelFile.getCreationHelper().createClientAnchor();
        anchor.setCol1(imageColumn);
        anchor.setRow1(imageRow);
        anchor.setRow2(index[1] + 1); // Adjust based on the number of merged rows
        anchor.setCol2(index[3] + 1); // Adjust based on the number of merged columns
        anchor.setDx1(imageDxUnits);
        anchor.setDy1(imageDyUnits);
        anchor.setDx2(imageDxUnits + imageWidthUnits);
        anchor.setDy2(imageDyUnits + imageHeightUnits);

// Create a picture with the anchor
        drawing.createPicture(anchor, pictureIndex);
//...

    }


    public void addSheet(String sheetName) {
        excelFile.createSheet(sheetName);
    }

    public Sheet getSheet(String sheetName) {
        return excelFile.getSheet(sheetName);
    }

    public void addKitchenTable(Data data, Sheet sheet, int[] index) {


        if (data.getProcessLevel() != null) {
            addTextHeader(sheet, new int[]{currentIndex, ++currentIndex, index[1], index[1] += 2}, data.getProcessLevel()
                    , IndexedColors.WHITE.getIndex(), (short) 16, IndexedColors.AQUA.getIndex());
            index[1] -= 2;
            currentIndex++;
        }
//        if (data.getId() != null) {
//            addText(sheet, new int[]{  currentIndex, index[1]++}, "Id", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{  currentIndex + 1, index[1] - 1}, data.getId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getMealId() != null) {
//            addText(sheet, new int[]{  currentIndex, index[1]++}, "MealId", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{  currentIndex + 1, index[1] - 1}, data.getMealId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getDateStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getCreateBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "CreateBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getCreateBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Shift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
//        if (data.getStatus() != null) {
//            addText(sheet, new int[]{  currentIndex, index[1]++}, "Status", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{  currentIndex + 1, index[1] - 1}, data.getStatus(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getProcess() != null) {
//            addText(sheet, new int[]{  currentIndex, index[1]++}, "Process", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{  currentIndex + 1, index[1] - 1}, data.getProcess(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getProcessLevelCode() != null) {
//            addText(sheet, new int[]{  currentIndex, index[1]++}, "ProcessLevelCode", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{  currentIndex + 1, index[1] - 1}, data.getProcessLevelCode(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getReviewNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMainNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MainNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMainNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReviewedBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewedBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewedBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateTimeStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductionNum() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductionNum", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductionNum(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Note", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getUserShiftEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShiftEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShiftEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getUserShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateTimeEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getItemName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ItemName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getItemName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSoakingTime() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SoakingTime", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSoakingTime(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSoakingTempWater() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SoakingTempWater", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSoakingTempWater(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductTexture() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductTexture", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductTexture(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getGX7_Conc() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "GX7_Conc", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getGX7_Conc(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPH_WashingWater() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PH_WashingWater", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPH_WashingWater(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDeliveredTo() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeliveredTo", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeliveredTo(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTempC() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TempC", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTempC(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getOilType() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "OilType", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getOilType(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getOilTemperature() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "OilTemperature", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getOilTemperature(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTpm() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Tpm", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTpm(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getCookingTemperature() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "CookingTemperature", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getCookingTemperature(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getCookingTime() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "CookingTime", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getCookingTime(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductTemperature() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductTemperature", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductTemperature(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLabelInformation() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LabelInformation", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLabelInformation(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getFoodAdditiveName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "FoodAdditiveName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getFoodAdditiveName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getWaterQuantity() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "WaterQuantity", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getWaterQuantity(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getWeightOfAdditive() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "WeightOfAdditive", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getWeightOfAdditive(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPercentage() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Percentage", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPercentage(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMoisture() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Moisture", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMoisture(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTotalSoilds() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TotalSoilds", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTotalSoilds(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPh() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Ph", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPh(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSalt() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Salt", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSalt(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getBrix() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Brix", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getBrix(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getAcidity() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Acidity", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAcidity(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getViscosityCP() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ViscosityCP", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getViscosityCP(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTaste() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Taste", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTaste(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getColor() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Color", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getColor(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTexture() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Texture", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTexture(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSmell() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Smell", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSmell(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getAppearance() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Appearance", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAppearance(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductTemperatureBefore() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductTemperatureBefore", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductTemperatureBefore(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductTemperatureAfte() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductTemperatureAfte", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductTemperatureAfte(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getChillersTemperatureC() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ChillersTemperatureC", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getChillersTemperatureC(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getHotHoldingMethod() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "HotHoldingMethod", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getHotHoldingMethod(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getQtyKg() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "QtyKg", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getQtyKg(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTrolliesOrContainers() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TrolliesOrContainers", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTrolliesOrContainers(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getFillingLineName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "FillingLineName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getFillingLineName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDeleteBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeleteBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeleteBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastUpdate() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastUpdate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastUpdate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastTransaction() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastTransaction", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastTransaction(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }

    }

    public void addProduction1Table(Data data, Sheet sheet, int[] index) {

        if (data.getProcessLevel() != null) {
            addTextHeader(sheet, new int[]{currentIndex, ++currentIndex, 0, 2}, data.getProcessLevel()
                    , IndexedColors.WHITE.getIndex(), (short) 16, IndexedColors.AQUA.getIndex());

            currentIndex++;
        }

//        if (data.getId() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "Id", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//
//        }
//        if (data.getMealId() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "MealId", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getMealId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//
//        }
        if (data.getDateStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getCreateBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "CreateBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getCreateBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Shift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
//        if (data.getStatus() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "Status", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getStatus(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//
//        }
//        if (data.getProcess() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "Process", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getProcess(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//
//        }

//        if (data.getProcessLevelCode() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "ProcessLevelCode", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getProcessLevelCode(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//
//        }
        if (data.getReviewNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getMainNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MainNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMainNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getReviewedBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewedBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewedBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getDateTimeStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getDateTimeEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getUserShiftEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShiftEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShiftEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getUserShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getItemName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ItemName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getItemName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getBatchNumber() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "BatchNumber", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getBatchNumber(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Note", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getThermoformWidthTop() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformWidthTop", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformWidthTop(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getThermoformWidthSup() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformWidthSup", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformWidthSup(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getThermoformBase() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformBase", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformBase(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getThermoformLength() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformLength", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformLength(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getThermoformThicknessTop() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformThicknessTop", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformThicknessTop(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getThermoformThicknessSup() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformThicknessSup", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformThicknessSup(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAluTrayWidth() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AluTrayWidth", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAluTrayWidth(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAluTrayBase() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AluTrayBase", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAluTrayBase(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAluTrayThickness() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AluTrayThickness", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAluTrayThickness(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAluTrayLength() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AluTrayLength", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAluTrayLength(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLidWidth() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LidWidth", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLidWidth(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLidBase() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LidBase", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLidBase(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLidThickness() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LidThickness", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLidThickness(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLidLength() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LidLength", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLidLength(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPouchWidth() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PouchWidth", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPouchWidth(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPouchBase() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PouchBase", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPouchBase(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPouchThickness() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PouchThickness", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPouchThickness(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPouchLength() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PouchLength", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPouchLength(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPackWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PackWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPackWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getMachinePart1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MachinePart1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMachinePart1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getReading1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Reading1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReading1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getResult1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Result1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getResult1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getMachinePart2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MachinePart2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMachinePart2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getReading2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Reading2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReading2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getResult2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Result2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getResult2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getDeliveredTo() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeliveredTo", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeliveredTo(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getSealingTemp1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTemp1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTemp1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getSealingTemp2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTemp2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTemp2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLeakTestFlow() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LeakTestFlow", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLeakTestFlow(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLeakTestMicron() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LeakTestMicron", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLeakTestMicron(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLeakTestExplosion() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LeakTestExplosion", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLeakTestExplosion(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getTestRodeType() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TestRodeType", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTestRodeType(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getTypeForeignBody() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TypeForeignBody", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTypeForeignBody(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getTestResults() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TestResults", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTestResults(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getNumberRejections() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "NumberRejections", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getNumberRejections(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getProductStandardWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductStandardWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductStandardWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getTestedSampleWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TestedSampleWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTestedSampleWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getDeviationAccuracy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeviationAccuracy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeviationAccuracy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAutoclaveNo() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AutoclaveNo", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAutoclaveNo(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAutoclaveMaxTempSterilization() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AutoclaveMaxTempSterilization", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAutoclaveMaxTempSterilization(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getMaxPressure() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MaxPressure", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMaxPressure(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAutoclaveTimeSterilization() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AutoclaveTimeSterilization", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAutoclaveTimeSterilization(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAutoclaveTimeCooling() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AutoclaveTimeCooling", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAutoclaveTimeCooling(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getAutoclaveTimeTotal() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "AutoclaveTimeTotal", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getAutoclaveTimeTotal(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getProductExternalDryness() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductExternalDryness", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductExternalDryness(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPrintingCheck() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PrintingCheck", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPrintingCheck(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getArea() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Area", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getArea(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getQuantity() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Quantity", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getQuantity(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getPackageType() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PackageType", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPackageType(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getFillingLineName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "FillingLineName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getFillingLineName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getDeleteBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeleteBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeleteBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLastUpdate() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastUpdate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastUpdate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }
        if (data.getLastTransaction() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastTransaction", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastTransaction(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());

        }


    }

    public void addProduction2Table(Data data, Sheet sheet, int[] index) {

        if (data.getProcessLevel() != null) {
            addTextHeader(sheet, new int[]{currentIndex, ++currentIndex, 0, 2}, data.getProcessLevel()
                    , IndexedColors.WHITE.getIndex(), (short) 16, IndexedColors.AQUA.getIndex());

            currentIndex++;
        }
//        if (data.getId() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "Id", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getMealId() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "MealId", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getMealId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getDateStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getCreateBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "CreateBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getCreateBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Shift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
//        if (data.getStatus() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "Status", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getStatus(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getProcess() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "Process", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getProcess(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }

//        if (data.getProcessLevelCode() != null) {
//            addText(sheet, new int[]{   currentIndex, index[1]++}, "ProcessLevelCode", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{   currentIndex + 1, index[1] - 1}, data.getProcessLevelCode(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getReviewNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMainNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MainNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMainNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReviewedBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewedBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewedBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getItemName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ItemName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getItemName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getBatchNumber() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "BatchNumber", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getBatchNumber(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateTimeStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateTimeEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductionNum() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductionNum", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductionNum(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getUserShiftEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShiftEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShiftEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getUserShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductQuality() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductQuality", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductQuality(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductActualTemp() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductActualTemp", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductActualTemp(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSeneorTemp() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SeneorTemp", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSeneorTemp(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getHygieneCheckNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "HygieneCheckNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getHygieneCheckNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Note", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getThermoformLength() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformLength", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformLength(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getThermoformWidthTop() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformWidthTop", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformWidthTop(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getThermoformWidthSup() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformWidthSup", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformWidthSup(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getThermoformBase() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformBase", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformBase(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getThermoformThicknessTop() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformThicknessTop", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformThicknessTop(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getThermoformThicknessSup() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ThermoformThicknessSup", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getThermoformThicknessSup(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMetalizedFilmlength() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MetalizedFilmlength", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMetalizedFilmlength(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMetalizedFilmWidth() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MetalizedFilmWidth", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMetalizedFilmWidth(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMetalizedFilmBase() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MetalizedFilmBase", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMetalizedFilmBase(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMetalizedFilmThickness() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MetalizedFilmThickness", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMetalizedFilmThickness(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMachinePart1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MachinePart1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMachinePart1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReading1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Reading1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReading1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReading2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Reading2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReading2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMachinePart2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MachinePart2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMachinePart2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getResult1() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Result1", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getResult1(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getResult2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Result2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getResult2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getFillingLineName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "FillingLineName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getFillingLineName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPackType() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PackType", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPackType(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPremixWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PremixWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPremixWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSugarWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SugarWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSugarWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTotalWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TotalWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTotalWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSugarQualityDiameter() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SugarQualityDiameter", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSugarQualityDiameter(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSealingTempUpper() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTempUpper", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTempUpper(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSealingTempLower() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTempLower", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTempLower(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSealingTemp2() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTemp2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTemp2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSealingTestExaminationResult() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTestExaminationResult", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTestExaminationResult(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductStandardWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductStandardWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductStandardWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTestedSampleWeight() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TestedSampleWeight", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTestedSampleWeight(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDeviationAccuracy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeviationAccuracy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeviationAccuracy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductExternalCondition() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductExternalCondition", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductExternalCondition(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getHeatStampCheck() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "HeatStampCheck", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getHeatStampCheck(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getQtyKg() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "QtyKg", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getQtyKg(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDeleteBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeleteBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeleteBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastUpdate() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastUpdate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastUpdate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastTransaction() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastTransaction", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastTransaction(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }

    }

    public void addPackagingTable(Data data, Sheet sheet, int[] index) {

        if (data.getProcessLevel() != null) {
            addTextHeader(sheet, new int[]{currentIndex, ++currentIndex, 0, 2}, data.getProcessLevel()
                    , IndexedColors.WHITE.getIndex(), (short) 16, IndexedColors.AQUA.getIndex());

            currentIndex++;
        }
//        if (data.getId() != null) {
//            addText(sheet, new int[]{currentIndex, index[1]}, "Id", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[] {currentIndex++, index[1] + 1}, data.getId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getMealId() != null) {
//            addText(sheet, new int[]{currentIndex, index[1]}, "MealId", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[] {currentIndex++, index[1] + 1}, data.getMealId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getDateStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getCreateBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "CreateBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getCreateBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getShift() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Shift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
//        if (data.getStatus() != null) {
//            addText(sheet, new int[]{currentIndex, index[1]}, "Status", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[] {currentIndex++, index[1] + 1}, data.getStatus(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getProcess() != null) {
//            addText(sheet, new int[]{currentIndex, index[1]}, "Process", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[] {currentIndex++, index[1] + 1}, data.getProcess(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }

//        if (data.getProcessLevelCode() != null) {
//            addText(sheet, new int[]{currentIndex, index[1]}, "ProcessLevelCode", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[] {currentIndex++, index[1] + 1}, data.getProcessLevelCode(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getReviewNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMainNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MainNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMainNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReviewedBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ReviewedBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getReviewedBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMealAccessoriesRationName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MealAccessoriesRationName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMealAccessoriesRationName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPackagingLineName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PackagingLineName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPackagingLineName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getMethodOfPackaging() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "MethodOfPackaging", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getMethodOfPackaging(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getTypeOfPack() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "TypeOfPack", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getTypeOfPack(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getWidth_mm() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Width_mm", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getWidth_mm(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLength_mm() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Length_mm", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLength_mm(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getHeight_mm() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Height_mm", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getHeight_mm(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getNote() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Note", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getItemName() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ItemName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getItemName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateTimeStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateTimeEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DateTimeEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDateTimeEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getBatch_number() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "Batch_number", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getBatch_number(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPackagingLabelType() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PackagingLabelType", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPackagingLabelType(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSealingTestResult() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "SealingTestResult", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getSealingTestResult(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPrintedDataCheck() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "PrintedDataCheck", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getPrintedDataCheck(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductexternalCondition() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "ProductexternalCondition", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getProductexternalCondition(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDeliveredTo() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeliveredTo", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeliveredTo(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getUserShiftStart() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShiftStart", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShiftStart(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getUserShiftEnd() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "UserShiftEnd", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getUserShiftEnd(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getQtyKg() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "QtyKg", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getQtyKg(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDeleteBy() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "DeleteBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getDeleteBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastUpdate() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastUpdate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastUpdate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastTransaction() != null) {
            addText(sheet, new int[]{currentIndex, index[1]}, "LastTransaction", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{currentIndex++, index[1] + 1}, data.getLastTransaction(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }


    }

    public void addHoldReleseTable(Data data, Sheet sheet, int[] index) {

//        if (data.getProcessLevel() != null) {
//            addTextHeader(sheet, new int[]{index[0], ++index[0], 0, 2}, data.getProcessLevel()
//                    , IndexedColors.WHITE.getIndex(), (short) 16, IndexedColors.AQUA.getIndex());
//
//            index[0]++;
//        }
//        if (data.getId() != null) {
//            addText(sheet, new int[]{index[0], index[1]++}, "id", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{index[0] + 1, index[1] - 1}, data.getId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
//        if (data.getMealId() != null) {
//            addText(sheet, new int[]{index[0], index[1]++}, "mealId", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
//            addText(sheet, new int[]{index[0] + 1, index[1] - 1}, data.getMealId(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
//        }
        if (data.getFormDate() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "formDate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getFormDate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSignatureOfQc() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "signatureOfQc", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getSignatureOfQc(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getSignatureOfQc2() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "signatureOfQc2", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getSignatureOfQc2(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getFinishedProductName() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "finishedProductName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getFinishedProductName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPrimaryPackageType() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "primaryPackageType", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getPrimaryPackageType(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getBrandName() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "brandName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getBrandName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getManufactureName() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "manufactureName", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getManufactureName(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getOrigin() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "origin", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getOrigin(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getProductionDate() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "productionDate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getProductionDate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getExpiryDate() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "expiryDate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getExpiryDate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getQuantity() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "quantity", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getQuantity(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getBatchNo() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "batchNo", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getBatchNo(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getStoragePlace() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "storagePlace", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getStoragePlace(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReasonForHoldOnProduct() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "reasonForHoldOnProduct", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getReasonForHoldOnProduct(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getPeriodOfHold() != null) {
            addText(sheet, new int[]{index[0], index[1]}, "periodOfHold", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 1}, data.getPeriodOfHold(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }

        index[0] = 8;
        index[1] = 0;
        if (data.getMicrobialTestsResultOn() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "microbialTestsResultOn", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getMicrobialTestsResultOn(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getIncubationTestsC() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "incubationTests", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getIncubationTestsC(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getIncubationTestsC() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "incubationTestsC", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getIncubationTestsC(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getOutsideLabResultDate() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "outsideLabResultDate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getOutsideLabResultDate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getQuantityOfReleasedFinishedProducts() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "quantityOfReleasedFinishedProducts", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getQuantityOfReleasedFinishedProducts(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getQuantityOfNonConfirmingFinishedProduct() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "quantityOfNonConfirmingFinishedProduct", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getQuantityOfNonConfirmingFinishedProduct(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getNotes() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "notes", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getNotes(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getActionTaken() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "actionTaken", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getActionTaken(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getDateOfTheActionTakenWasDone() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "dateOfTheActionTakenWasDone", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getDateOfTheActionTakenWasDone(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getStatus() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "status", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getStatus(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getShift() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "shift", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getShift(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReviewNote() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "reviewNote", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getReviewNote(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReviewedBy() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "reviewedBy", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getReviewedBy(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getReviewedDate() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "reviewedDate", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getReviewedDate(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }
        if (data.getLastTransaction() != null) {
            addText(sheet, new int[]{index[0], index[1] + 2}, "lastTransaction", IndexedColors.WHITE.getIndex(), true, IndexedColors.CORAL.getIndex());
            addText(sheet, new int[]{index[0]++, index[1] + 3}, data.getLastTransaction(), IndexedColors.BLACK.getIndex(), false, IndexedColors.WHITE.getIndex());
        }


    }


    public void createExcelFile() throws IOException {
        if (filePath.exists()) {
            filePath.delete();
        }
        filePath.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        excelFile.write(fileOutputStream);


        if (fileOutputStream != null) {
            fileOutputStream.flush();
            fileOutputStream.close();
        }
    }


}


