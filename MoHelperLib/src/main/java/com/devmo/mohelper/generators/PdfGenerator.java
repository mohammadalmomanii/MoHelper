package com.devmo.mohelper.generators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.devmo.mohelper.R;
import com.devmo.mohelper.api.model.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PdfGenerator {
    private static File filePath;
    private static Context context;
    private PdfDocument document;
    private Data rawMaterial, processData, holdReleseData;
    private String fileName;
    private List<Page> pageList; // Added to store the added pages


    public PdfGenerator(Context context, String fileName, Data rawMaterial, Data processData,Data holdReleseData) {
        filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        this.context = context;
        this.fileName = rawMaterial.getMealName() + " " + (rawMaterial.getBatchNumber() != null ? rawMaterial.getBatchNumber() : rawMaterial.getBatchNumber2() != null ? rawMaterial.getBatchNumber2() : "");
        this.rawMaterial = rawMaterial;
        this.processData = processData;
        this.holdReleseData = holdReleseData;
        document = new PdfDocument();
        pageList = new ArrayList<>();
        generatePdf();

    }

    public void generatePdf() {
        // Create three pages


        addPage("Hold & Relese");

        addHoldRelease(holdReleseData, new AtomicInteger(130),new AtomicInteger(160));
        closePage();
        addPage("Row Material");
        addRowMaterial(rawMaterial.getRawMaterial());
        closePage();
        if (rawMaterial.getMealType().equals("1")) {
            addPage("Kitchen");
            addKitchenProcess(processData.getKitchen());
            closePage();
            addPage("Production1");
            addProduction1Process(processData.getProduction1());
            closePage();
        } else {
            addPage("Production2");
            addProduction2Process(processData.getProduction2());
            closePage();
        }
        addPage("Packaging");
        addPackagingProcess(processData.getPackaging());
        closePage();
        savePdf();
    }

    private void addKitchenProcess(List<Data> list) {
        AtomicInteger x = new AtomicInteger(130);
        AtomicInteger y = new AtomicInteger(160);
        list.forEach(data -> {
            addKitchenTable(data, x, y);
            x.addAndGet(5);
            y.addAndGet(5);
        });
    }

    public void addKitchenTable(Data data, AtomicInteger x, AtomicInteger y) {

        if (data.getProcessLevel() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_2, 30, x.addAndGet(30), 530, y.addAndGet(30));
            addText(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevel(), 14, Color.WHITE, true, 80, y.get() - 10);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Id", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Kitchen");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getMealId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "MealId", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMealId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Kitchen");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getDateStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "DateStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getCreateBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "CreateBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getCreateBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Shift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getStatus() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Status", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getStatus() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Kitchen");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcess() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Process", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcess() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Kitchen");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcessLevelCode() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ProcessLevelCode", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevelCode() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Kitchen");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getReviewNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ReviewNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMainNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "MainNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMainNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewedBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ReviewedBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewedBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "DateTimeStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductionNum() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ProductionNum", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductionNum() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Note", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShiftEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "UserShiftEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShiftEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "UserShift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "DateTimeEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getItemName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ItemName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getItemName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSoakingTime() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "SoakingTime", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSoakingTime() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSoakingTempWater() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "SoakingTempWater", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSoakingTempWater() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductTexture() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ProductTexture", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductTexture() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getGX7_Conc() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "GX7_Conc", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getGX7_Conc() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPH_WashingWater() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "PH_WashingWater", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPH_WashingWater() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeliveredTo() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "DeliveredTo", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeliveredTo() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTempC() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "TempC", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTempC() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getOilType() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "OilType", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getOilType() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getOilTemperature() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "OilTemperature", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getOilTemperature() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTpm() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Tpm", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTpm() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getCookingTemperature() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "CookingTemperature", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getCookingTemperature() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getCookingTime() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "CookingTime", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getCookingTime() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductTemperature() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ProductTemperature", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductTemperature() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLabelInformation() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "LabelInformation", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLabelInformation() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getFoodAdditiveName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "FoodAdditiveName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getFoodAdditiveName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getWaterQuantity() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "WaterQuantity", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getWaterQuantity() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getWeightOfAdditive() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "WeightOfAdditive", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getWeightOfAdditive() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPercentage() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Percentage", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPercentage() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMoisture() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Moisture", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMoisture() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTotalSoilds() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "TotalSoilds", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTotalSoilds() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPh() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Ph", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPh() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSalt() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Salt", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSalt() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBrix() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Brix", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBrix() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAcidity() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Acidity", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAcidity() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getViscosityCP() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ViscosityCP", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getViscosityCP() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTaste() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Taste", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTaste() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getColor() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Color", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getColor() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTexture() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Texture", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTexture() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSmell() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Smell", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSmell() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAppearance() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "Appearance", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAppearance() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductTemperatureBefore() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ProductTemperatureBefore", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductTemperatureBefore() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductTemperatureAfte() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ProductTemperatureAfte", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductTemperatureAfte() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getChillersTemperatureC() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ChillersTemperatureC", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getChillersTemperatureC() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getHotHoldingMethod() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "HotHoldingMethod", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getHotHoldingMethod() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQtyKg() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "QtyKg", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQtyKg() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTrolliesOrContainers() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "TrolliesOrContainers", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTrolliesOrContainers() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getFillingLineName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "FillingLineName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getFillingLineName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeleteBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "DeleteBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeleteBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastUpdate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "LastUpdate", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastUpdate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastTransaction() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "LastTransaction", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastTransaction() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }

    }

    private void addProduction1Process(List<Data> list) {
        AtomicInteger x = new AtomicInteger(130);
        AtomicInteger y = new AtomicInteger(160);
        list.forEach(data -> {
            addProduction1Table(data, x, y);
            x.addAndGet(5);
            y.addAndGet(5);
        });
    }

    public void addProduction1Table(Data data, AtomicInteger x, AtomicInteger y) {

        if (data.getProcessLevel() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 30, x.addAndGet(30), 530, y.addAndGet(30));
            addText(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevel(), 14, Color.WHITE, true, 80, y.get() - 10);
            if (x.get() > 700) {
                closePage();
                addPage("Kitchen");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "id", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production1");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getMealId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mealId", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMealId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production1");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getDateStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getCreateBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "createBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getCreateBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "shift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getStatus() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "status", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getStatus() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production1");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcess() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "process", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcess() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production1");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcessLevelCode() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "processLevelCode", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevelCode() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production1");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getReviewNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMainNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mainNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMainNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewedBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewedBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewedBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateTimeStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateTimeEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShiftEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "userShiftEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShiftEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "userShift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getItemName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "itemName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getItemName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBatchNumber() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "batchNumber", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBatchNumber() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "note", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformWidthTop() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformWidthTop", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformWidthTop() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformWidthSup() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformWidthSup", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformWidthSup() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformBase() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ThermoformBase", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformBase() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformLength() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ThermoformLength", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformLength() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformThicknessTop() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_2, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ThermoformThicknessTop", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformThicknessTop() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformThicknessSup() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "ThermoformThicknessSup", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformThicknessSup() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAluTrayWidth() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "aluTrayWidth", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAluTrayWidth() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAluTrayBase() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "aluTrayBase", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAluTrayBase() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAluTrayThickness() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "aluTrayThickness", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAluTrayThickness() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAluTrayLength() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "aluTrayLength", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAluTrayLength() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLidWidth() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lidWidth", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLidWidth() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLidBase() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lidBase", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLidBase() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLidThickness() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lidThickness", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLidThickness() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLidLength() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lidLength", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLidLength() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPouchWidth() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "pouchWidth", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPouchWidth() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPouchBase() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "pouchBase", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPouchBase() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPouchThickness() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "pouchThickness", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPouchThickness() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPouchLength() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "pouchLength", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPouchLength() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPackWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "packWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPackWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMachinePart1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "machinePart1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMachinePart1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReading1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reading1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReading1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getResult1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "result1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getResult1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMachinePart2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "machinePart2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMachinePart2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReading2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reading2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReading2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getResult2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "result2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getResult2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeliveredTo() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deliveredTo", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeliveredTo() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTemp1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTemp1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTemp1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTemp2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTemp2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTemp2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLeakTestFlow() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "leakTestFlow", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLeakTestFlow() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLeakTestMicron() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "leakTestMicron", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLeakTestMicron() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLeakTestExplosion() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "leakTestExplosion", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLeakTestExplosion() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTestRodeType() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "testRodeType", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTestRodeType() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTypeForeignBody() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "typeForeignBody", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTypeForeignBody() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTestResults() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "testResults", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTestResults() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getNumberRejections() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "numberRejections", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getNumberRejections() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductStandardWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productStandardWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductStandardWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTestedSampleWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "testedSampleWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTestedSampleWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeviationAccuracy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deviationAccuracy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeviationAccuracy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAutoclaveNo() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "autoclaveNo", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAutoclaveNo() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAutoclaveMaxTempSterilization() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "autoclaveMaxTempSterilization", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAutoclaveMaxTempSterilization() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMaxPressure() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "maxPressure", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMaxPressure() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAutoclaveTimeSterilization() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "autoclaveTimeSterilization", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAutoclaveTimeSterilization() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAutoclaveTimeCooling() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "autoclaveTimeCooling", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAutoclaveTimeCooling() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getAutoclaveTimeTotal() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "autoclaveTimeTotal", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getAutoclaveTimeTotal() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductExternalDryness() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productExternalDryness", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductExternalDryness() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPrintingCheck() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "printingCheck", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPrintingCheck() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getArea() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "area", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getArea() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQuantity() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "quantity", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQuantity() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPackageType() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "packageType", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPackageType() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getFillingLineName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "fillingLineName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getFillingLineName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeleteBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deleteBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeleteBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastUpdate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lastUpdate", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastUpdate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastTransaction() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lastTransaction", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastTransaction() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production1");
                x.set(130);
                y.set(160);
            }
        }


    }


    private void addProduction2Process(List<Data> list) {
        AtomicInteger x = new AtomicInteger(130);
        AtomicInteger y = new AtomicInteger(160);
        list.forEach(data -> {
            addProduction2Table(data, x, y);
            x.addAndGet(5);
            y.addAndGet(5);
        });
    }

    public void addProduction2Table(Data data, AtomicInteger x, AtomicInteger y) {

        if (data.getProcessLevel() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 30, x.addAndGet(30), 530, y.addAndGet(30));
            addText(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevel(), 14, Color.WHITE, true, 80, y.get() - 10);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "id", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production2");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getMealId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mealId", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMealId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production2");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getDateStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getCreateBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "createBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getCreateBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "shift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getStatus() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "status", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getStatus() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production2");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcess() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "process", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcess() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production2");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcessLevelCode() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "processLevelCode", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevelCode() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Production2");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getReviewNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMainNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mainNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMainNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewedBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewedBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewedBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getItemName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "itemName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getItemName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBatchNumber() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "batchNumber", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBatchNumber() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateTimeStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateTimeEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductionNum() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productionNum", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductionNum() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShiftEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "userShiftEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShiftEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "userShift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductQuality() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productQuality", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductQuality() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductActualTemp() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productActualTemp", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductActualTemp() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSeneorTemp() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "seneorTemp", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSeneorTemp() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getHygieneCheckNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "hygieneCheckNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getHygieneCheckNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "note", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformLength() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformLength", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformLength() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformWidthTop() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformWidthTop", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformWidthTop() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformWidthSup() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformWidthSup", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformWidthSup() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformBase() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformBase", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformBase() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformThicknessTop() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformThicknessTop", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformThicknessTop() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getThermoformThicknessSup() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "thermoformThicknessSup", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getThermoformThicknessSup() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMetalizedFilmlength() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "metalizedFilmlength", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMetalizedFilmlength() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMetalizedFilmWidth() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "metalizedFilmWidth", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMetalizedFilmWidth() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMetalizedFilmBase() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "metalizedFilmBase", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMetalizedFilmBase() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMetalizedFilmThickness() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "metalizedFilmThickness", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMetalizedFilmThickness() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMachinePart1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "machinePart1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMachinePart1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReading1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reading1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReading1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReading2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reading2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReading2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMachinePart2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "machinePart2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMachinePart2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getResult1() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "result1", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getResult1() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getResult2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "result2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getResult2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getFillingLineName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "fillingLineName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getFillingLineName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPackType() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "packType", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPackType() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPremixWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "premixWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPremixWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSugarWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sugarWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSugarWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTotalWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "totalWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTotalWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSugarQualityDiameter() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sugarQualityDiameter", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSugarQualityDiameter() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTempUpper() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTempUpper", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTempUpper() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTempLower() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTempLower", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTempLower() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTemp2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTemp2", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTemp2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTestExaminationResult() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTestExaminationResult", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTestExaminationResult() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductStandardWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productStandardWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductStandardWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTestedSampleWeight() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "testedSampleWeight", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTestedSampleWeight() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeviationAccuracy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deviationAccuracy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeviationAccuracy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductExternalCondition() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productExternalCondition", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductExternalCondition() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getHeatStampCheck() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "heatStampCheck", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getHeatStampCheck() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQtyKg() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "qtyKg", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQtyKg() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeleteBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deleteBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeleteBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastUpdate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lastUpdate", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastUpdate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastTransaction() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lastTransaction", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastTransaction() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastTransaction() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "LastTransaction", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastTransaction() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Production2");
                x.set(130);
                y.set(160);
            }
        }

    }


    private void addPackagingProcess(List<Data> list) {
        AtomicInteger x = new AtomicInteger(130);
        AtomicInteger y = new AtomicInteger(160);
        list.forEach(data -> {
            addPackagingTable(data, x, y);
            x.addAndGet(5);
            y.addAndGet(5);
        });
    }

    public void addPackagingTable(Data data, AtomicInteger x, AtomicInteger y) {

        if (data.getProcessLevel() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 30, x.addAndGet(30), 530, y.addAndGet(30));
            addText(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevel(), 14, Color.WHITE, true, 80, y.get() - 10);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "id", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Packaging");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getMealId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mealId", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMealId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Packaging");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getDateStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getCreateBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "createBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getCreateBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "shift", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getStatus() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "status", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getStatus() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Packaging");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcess() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "process", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcess() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Packaging");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getProcessLevelCode() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "processLevelCode", Color.WHITE, 12, 12, true, 80, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProcessLevelCode() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Packaging");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getReviewNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMainNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mainNote", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMainNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewedBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewedBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewedBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMealAccessoriesRationName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "mealAccessoriesRationName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMealAccessoriesRationName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPackagingLineName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "packagingLineName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPackagingLineName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMethodOfPackaging() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "methodOfPackaging", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMethodOfPackaging() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getTypeOfPack() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "typeOfPack", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getTypeOfPack() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getWidth_mm() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "width_mm", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getWidth_mm() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLength_mm() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "length_mm", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLength_mm() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getHeight_mm() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "height_mm", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getHeight_mm() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "note", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getItemName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "itemName", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getItemName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateTimeStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateTimeEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "dateTimeEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateTimeEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBatch_number() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "batch_number", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBatch_number() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPackagingLabelType() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "packagingLabelType", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPackagingLabelType() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSealingTestResult() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "sealingTestResult", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSealingTestResult() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPrintedDataCheck() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "printedDataCheck", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPrintedDataCheck() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductexternalCondition() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "productexternalCondition", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductexternalCondition() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeliveredTo() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deliveredTo", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeliveredTo() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShiftStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "userShiftStart", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShiftStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getUserShiftEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "userShiftEnd", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getUserShiftEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQtyKg() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "qtyKg", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQtyKg() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDeleteBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "deleteBy", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDeleteBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastUpdate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lastUpdate", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastUpdate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastTransaction() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "lastTransaction", Color.WHITE, 12, 12, true, 80, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastTransaction() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Packaging");
                x.set(130);
                y.set(160);
            }
        }

    }


    public Page getPage(int index) {
        return pageList.get(index);
    }

    void closePage() {
        document.finishPage(getPage(pageList.size() - 1));
    }

    void addPage(String title) {
        PageInfo pageInfo = getPageInfo(); // Change this method based on your desired page size
        Page page = document.startPage(pageInfo);
        pageList.add(page);

        Canvas canvas = getPage(pageList.size() - 1).getCanvas();

        // Draw background drawable
        drawBackground(canvas);

        // Add text
        addText(canvas, "MRE REPORT", 18, Color.BLACK, true, 365, 60);
        addText(canvas, title, 14, Color.BLACK, false, 370, 75);
        addText(canvas, (rawMaterial.getBatchNumber() != null ? rawMaterial.getBatchNumber() : rawMaterial.getBatchNumber2() != null ? rawMaterial.getBatchNumber2() : ""), 20, Color.WHITE, true, 50, 80);
        addText(canvas, rawMaterial.getMealName(), 14, Color.WHITE, true, 50, 100);
        addText(canvas, "- " + pageList.size() + " -", 12, Color.WHITE, false, 285, 830);

        // Add image
        addImage(canvas, R.drawable.shape_rounded_2, 520, 780, 590, 820);
    }

    void addRowMaterial(List<Data> list) {

        addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_2, 0, 140, 842, 170);

        addText(pageList.get(pageList.size() - 1).getCanvas(), "Item Name", 14, Color.WHITE, true, 20, 160);
        addText(pageList.get(pageList.size() - 1).getCanvas(), "Brand Name", 14, Color.WHITE, true, 150, 160);
        addText(pageList.get(pageList.size() - 1).getCanvas(), "Date Start", 14, Color.WHITE, true, 270, 160);
        addText(pageList.get(pageList.size() - 1).getCanvas(), "Date End", 14, Color.WHITE, true, 390, 160);
        addText(pageList.get(pageList.size() - 1).getCanvas(), "Note", 14, Color.WHITE, true, 510, 160);

        int z = 0;

        for (int i = 0; i < list.size(); i++) {
            if ((i + 1) % 21 == 0) {
                closePage();
                addPage("Row Material");
                addText(pageList.get(pageList.size() - 1).getCanvas(), "Item Name", 14, Color.WHITE, true, 20, 160);
                addText(pageList.get(pageList.size() - 1).getCanvas(), "Brand Name", 14, Color.WHITE, true, 150, 160);
                addText(pageList.get(pageList.size() - 1).getCanvas(), "Date Start", 14, Color.WHITE, true, 270, 160);
                addText(pageList.get(pageList.size() - 1).getCanvas(), "Date End", 14, Color.WHITE, true, 390, 160);
                addText(pageList.get(pageList.size() - 1).getCanvas(), "Note", 14, Color.WHITE, true, 510, 160);
                z += 20;
            }
            int x = 170 + ((i - z) * 30), y = 200 + ((i - z) * 30);
            String itemName = list.get(i).getItemName();
            String brandName = list.get(i).getBrandName();
            String productionDate = list.get(i).getProductionDate();
            String expiryDate = list.get(i).getExpiryDate();
            String note = list.get(i).getNote();
            if (i % 2 == 0)
                addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_2, 0, x, 842, y);
            else
                addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_2, 0, x, 842, y);
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), itemName, Color.BLACK, 10, 12, true, 20, y);
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), brandName, Color.BLACK, 10, 12, false, 150, y);
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), productionDate, Color.BLACK, 10, 12, false, 270, y);
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), expiryDate, Color.BLACK, 10, 12, false, 390, y);
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), note, Color.BLACK, 10, 12, false, 510, y);
        }


    }

    void addHoldRelease(Data data,AtomicInteger x, AtomicInteger y) {


        if (data.getMealName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 30, x.addAndGet(30), 530, y.addAndGet(30));
            addText(pageList.get(pageList.size() - 1).getCanvas(), data.getMealName(), 14, Color.WHITE, true, 80, y.get() - 10);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateStart() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "date Start", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateStart() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateEnd() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "date End", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateEnd() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBatchNumber() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "batch Number", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBatchNumber() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "id", Color.WHITE, 11, 18, true, 60, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Hold & Release");
//                x.set(130);
//                y.set(160);
//            }
//        }
//        if (data.getMealId() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "meal Id", Color.WHITE, 11, 18, true, 60, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMealId() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Hold & Release");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getFormDate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "form Date", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getFormDate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSignatureOfQc2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "signature Of Qc", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSignatureOfQc2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getSignatureOfQc2() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "signature Of Qc2", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getSignatureOfQc2() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getFinishedProductName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "finished Product Name", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getFinishedProductName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPrimaryPackageType() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "primary Package Type", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPrimaryPackageType() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBrandName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "brand Name", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBrandName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getManufactureName() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "manufacture Name", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getManufactureName() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getOrigin() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "origin", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getOrigin() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getProductionDate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "production Date", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getProductionDate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getExpiryDate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "expiry Date", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getExpiryDate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQuantity() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "quantity", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQuantity() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getBatchNo() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "batch No", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getBatchNo() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getStoragePlace() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "storage Place", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getStoragePlace() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReasonForHoldOnProduct() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reason For Hold On Product", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReasonForHoldOnProduct() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getPeriodOfHold() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "period Of Hold", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getPeriodOfHold() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getMicrobialTestsResultOn() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "microbial Tests Result On", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getMicrobialTestsResultOn() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getIncubationTests() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "incubation Tests", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getIncubationTests() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getIncubationTestsC() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "incubation Tests C", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getIncubationTestsC() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getOutsideLabResultDate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "outside LabResult Date", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getOutsideLabResultDate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQuantityOfReleasedFinishedProducts() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "quantity Of Released Finished Products", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQuantityOfReleasedFinishedProducts() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getQuantityOfNonConfirmingFinishedProduct() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "quantity Of NonConfirming Finished Product", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getQuantityOfNonConfirmingFinishedProduct() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getNotes() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "notes", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getNotes() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getActionTaken() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "action Taken", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getActionTaken() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getDateOfTheActionTakenWasDone() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "date Of The Action Taken Was Done", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getDateOfTheActionTakenWasDone() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
//        if (data.getStatus() != null) {
//            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "status", Color.WHITE, 11, 18, true, 60, y.get());
//            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getStatus() + "", Color.BLACK, 10, 40, false, 210, y.get());
//            x.addAndGet(2);
//            y.addAndGet(2);
//            if (x.get() > 700) {
//                closePage();
//                addPage("Hold & Release");
//                x.set(130);
//                y.set(160);
//            }
//        }
        if (data.getShift() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "shift", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getShift() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewNote() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "review Note", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewNote() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewedBy() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewed By", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewedBy() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getReviewedDate() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "reviewed Date", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getReviewedDate() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }
        if (data.getLastTransaction() != null) {
            addImage(pageList.get(pageList.size() - 1).getCanvas(), R.drawable.shape_rounded_6, 50, x.addAndGet(30), 510, y.addAndGet(30));
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), "last Transaction", Color.WHITE, 11, 18, true, 60, y.get());
            textWrapper(pageList.get(pageList.size() - 1).getCanvas(), data.getLastTransaction() + "", Color.BLACK, 10, 40, false, 210, y.get());
            x.addAndGet(2);
            y.addAndGet(2);
            if (x.get() > 700) {
                closePage();
                addPage("Hold & Release");
                x.set(130);
                y.set(160);
            }
        }


    }

    private void textWrapper(Canvas canvas, String text, int textColor, int textSize, int wrapFrom, boolean isBold, int x, int y) {
        if (text.length() > wrapFrom && text.contains(" ")) {
            int space = text.indexOf(" ", wrapFrom - 4);
            addText(canvas, text.substring(0, space + 1)
                    , textSize, textColor, isBold, x, y - 18);
            addText(canvas, text.substring(space + 1)
                    , textSize, textColor, isBold, x, y - 5);
        } else
            addText(canvas, text
                    , textSize, textColor, isBold, x, y - 10);
    }

    private PageInfo getPageInfo() {
        // Change the page size based on your requirement
        return new PageInfo.Builder(595, 842, 1)
                .setContentRect(null)
                .create();
    }

    private void drawBackground(Canvas canvas) {
        Drawable drawable = context.getDrawable(R.drawable.shape_rounded_2);

        if (drawable != null) {
            drawable.setBounds(0, 0, getPageInfo().getPageWidth(), getPageInfo().getPageHeight());
            drawable.draw(canvas);
        }
    }

    private void addText(Canvas canvas, String text, int textSize, int textColor, boolean isBold, float x, float y) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setFakeBoldText(isBold);
        textPaint.setTextSize(textSize);

        canvas.drawText(text, x, y, textPaint);
    }

    private void addImage(Canvas canvas, int drawableResId, int left, int top, int right, int bottom) {
        // Load the image drawable
        Drawable drawable = context.getDrawable(drawableResId);

        if (drawable != null) {
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(canvas);
        }
    }

    private void addTextInBottomCenter(Canvas canvas, String text, int textSize, int textColor
            , Paint.Align align, float x, float y) {
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(align);

        StaticLayout staticLayout = new StaticLayout(text, textPaint, (int) x * 2, Layout.Alignment.ALIGN_CENTER, 1.0f, 0, false);
        canvas.save();
        canvas.translate(x, y - staticLayout.getHeight());
        staticLayout.draw(canvas);
        canvas.restore();
    }

    private void savePdf() {
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();
    }
}
