package com.mebitech.web.controller;

import com.mebitech.core.api.log.IOperationLogService;
import com.mebitech.core.api.rest.IResponseFactory;
import com.mebitech.core.api.rest.enums.RestResponseStatus;
import com.mebitech.core.api.rest.responses.IRestResponse;
import com.mebitech.persistence.filter.FilterAndPagerImpl;
import com.mebitech.tgs.core.api.rest.processors.IDosyaRequestProcessor;
import com.mebitech.tgs.persistence.entities.DosyaImpl;
import com.mebitech.web.controller.utils.AController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping("/resources/dosyas")
public class DosyaResourceController extends AController {
    private static Logger logger = LoggerFactory.getLogger(DosyaResourceController.class);
    @Autowired
    private IResponseFactory responseFactory;
    @Autowired
    private IDosyaRequestProcessor dosyaProcessor;
    @Autowired
    private IOperationLogService logService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse list(HttpServletRequest request)
            throws UnsupportedEncodingException {
        FilterAndPagerImpl filterAndPager = getFilters(request);
        IRestResponse restResponse = dosyaProcessor.findByFilters(filterAndPager);
        return restResponse;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public IRestResponse add(@RequestBody String json) {
        try {
            DosyaImpl obj = objectMapper.readValue(json, DosyaImpl.class);

            String excelFilePath = obj.getAdi();
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue());
                            break;
                    }
                    System.out.print(" - ");
                }
                System.out.println();
            }

            workbook.close();
            inputStream.close();

            obj.setCreateDate(new Date());
            return dosyaProcessor.add(obj);
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public IRestResponse update(@RequestBody String json) {
        try {
            DosyaImpl obj = objectMapper.readValue(json, DosyaImpl.class);
            return dosyaProcessor.update(obj);
        } catch (Exception ex) {
            return responseFactory.createResponse(RestResponseStatus.ERROR, "Hata : " + ex.getMessage());
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public IRestResponse delete(@PathVariable("id") Long id) {
        return dosyaProcessor.delete(id);
    }

    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse getProperties() {
        return dosyaProcessor.getProperties();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public IRestResponse findById(@PathVariable("id") Long id) {
        return dosyaProcessor.findById(id);
    }

}