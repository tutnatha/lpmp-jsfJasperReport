/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tutorialspoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author myssd
 */
public class CreateReport {
    public static void main(String[] args) {
//      String masterReportFileName = "C://tools/jasperreports-5.0.1/test"
//         + "/jasper_report_template.jrxml";     

      String masterReportFileName = "/Users/myssd/github.com/jumarome/" + 
              "jsfJasperReports/src/main/webapp"
              + "/jasper_report_template.jrxml";
      
//      String subReportFileName = "C://tools/jasperreports-5.0.1/test"
//         + "/AddressReport.jrxml";
      
      String subReportFileName = "/Users/myssd/github.com/jumarome/"+
              "jsfJasperReports/src/main/webapp"
         + "/address_report_template.jrxml";
      
//      String destFileName = "C://tools/jasperreports-5.0.1/test"
//         + "/jasper_report_template.JRprint";
			
      String destFileName = "/Users/myssd/github.com/jumarome/"+
              "jsfJasperReports/src/main/webapp"
         + "/jasper_report_template.JRprint";

      DataBeanList DataBeanList = new DataBeanList();
      ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
      JRBeanCollectionDataSource beanColDataSource = new 
         JRBeanCollectionDataSource(dataList);

      try {
         /* Compile the master and sub report */
         JasperReport jasperMasterReport = JasperCompileManager
            .compileReport(masterReportFileName);
         JasperReport jasperSubReport = JasperCompileManager
            .compileReport(subReportFileName);

         Map<String, Object> parameters = new HashMap<String, Object>();
         //Sub Report nya ditaruh di parameter
         parameters.put("subreportParameter", jasperSubReport);
         JasperFillManager.fillReportToFile(jasperMasterReport, 
            destFileName, parameters, beanColDataSource);
         
//         pdf();
            long start = System.currentTimeMillis();
//JasperExportManager.exportReportToPdfFile("build/reports/MasterReport.jrprint");
            JasperExportManager.exportReportToPdfFile(destFileName);
            System.err.println("PDF creation time : " 
                    + (System.currentTimeMillis() - start));
            
      } catch (JRException e) {

         e.printStackTrace();
      }
      System.out.println("Done filling!!! ...");
   }  

    public void pdf() throws JRException
    {
        long start = System.currentTimeMillis();
        JasperExportManager.exportReportToPdfFile("build/reports/MasterReport.jrprint");
        System.err.println("PDF creation time : " + 
                (System.currentTimeMillis() - start));
    }
}
