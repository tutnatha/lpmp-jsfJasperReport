/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import modelo.DaftarhunianHdr;
//import javax.ws.rs.core.HttpHeaders;
import modelo.Persona;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
/**
 *
 * @author jumarome
 */
//@Named(value = "rptDaftarHunianHdrBean")  //error jika pakai ini..
//@RequestScoped
@ManagedBean
@javax.faces.bean.RequestScoped
public class RptDaftarHunianHdrBean implements Serializable{
    public String SERVICE_BASE_URI;
    private List<modelo.DaftarhunianHdr> daftarhunianHdrs;
    /**
     * Creates a new instance of PersonaBean
     */
    public RptDaftarHunianHdrBean() {
        super();
        FacesContext fc = FacesContext.getCurrentInstance();
        SERVICE_BASE_URI = fc.getExternalContext().getInitParameter("metadata.serviceBaseURI");

//        this.personas = new ArrayList<>();
//        this.daftarhunianHdrs = new ArrayList<>();
        daftarhunianHdrs = getAllListDaftarHunianHdr();
    }

    @PostConstruct
    public void init() {
        //Call Rest Web Services...
//        Calendar cal = Calendar.getInstance();
//        cal.set(1989, 10, 6);
//        Persona p = new Persona("Juan", "Romero", cal.getTime());
//        personas.add(p);
//        cal.set(1980, 6, 10);
//        p = new Persona("Jefferson", "Valdez", cal.getTime());
//        personas.add(p);
//        cal.set(1970, 10, 15);
//        p = new Persona("Angela", "Yance", cal.getTime());
//        personas.add(p);
//        cal.set(1978, 5, 13);
//        p = new Persona("Carlos", "Mera", cal.getTime());
//        personas.add(p);

//        daftarhunianHdrs = (List<DaftarhunianHdr>) getAllDaftarHunianHdr();
//        daftarhunianHdrs = getAllListDaftarHunianHdr();
    }

//    private List<Persona> personas;
    
    
    public List<modelo.DaftarhunianHdr> getDaftarhunianHdrs() {

        return daftarhunianHdrs;
    }

    public void setPersonas(List<modelo.DaftarhunianHdr> daftarhunianHdrs) {
        this.daftarhunianHdrs = daftarhunianHdrs;
    }

    public void exportarPDF() throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtUsuario", "Juan Romero");  //remark dulu
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/DaftarHunianHdr.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, 
                new JRBeanCollectionDataSource(this.getDaftarhunianHdrs()));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.pdf");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarXLS() throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtUsuario", "Juan Romero");
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/DaftarHunianHdr.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getDaftarhunianHdrs()));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.xls");
        ServletOutputStream stream = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarPPT() throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtUsuario", "Juan Romero");
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/DaftarHunianHdr.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getDaftarhunianHdrs()));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.ppt");
        ServletOutputStream stream = response.getOutputStream();
        JRPptxExporter exporter = new JRPptxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void exportarDOC() throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtUsuario", "Juan Romero");
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/DaftarHunianHdr.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getDaftarhunianHdrs()));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=jsfReporte.doc");
        ServletOutputStream stream = response.getOutputStream();
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
        exporter.exportReport();

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void verPDF() throws JRException, IOException {
         Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtUsuario", "Jefferson Valdez");
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/DaftarHunianHdr.jasper"));
        byte[] bytes = JasperRunManager.runReportToPdf(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getDaftarhunianHdrs()));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outStream = response.getOutputStream();
        outStream.write(bytes, 0, bytes.length);
        outStream.flush();
        outStream.close();
        FacesContext.getCurrentInstance().responseComplete();

    }

    public DataModel<modelo.DaftarhunianHdr> getAllDaftarHunianHdr(){
	HttpHeaders headers = getHeaders();
	RestTemplate restTemplate = new RestTemplate();
//	String url = "http://207.148.66.201:8080/user/daftarhunianHdrs";
        String url = SERVICE_BASE_URI+"user/daftarhunianHdrs";
	HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
	ResponseEntity<modelo.DaftarhunianHdr[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, modelo.DaftarhunianHdr[].class);
        modelo.DaftarhunianHdr[] hdrList = responseEntity.getBody();
	hdrs = new ArrayDataModel<modelo.DaftarhunianHdr>(hdrList);
	return hdrs;   	
    }
    
    private HttpHeaders getHeaders(){
	String credential="mukesh:m123";
	String encodedCredential = new String(Base64.encodeBase64(credential.getBytes()));
        HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.add("Authorization","Basic " + encodedCredential);
	return headers;
    }
    
    private DataModel<modelo.DaftarhunianHdr> hdrs;
    
    public List<modelo.DaftarhunianHdr> getAllListDaftarHunianHdr(){
	HttpHeaders headers = getHeaders();
	RestTemplate restTemplate = new RestTemplate();
//	String url = "http://207.148.66.201:8080/user/daftarhunianHdrs";
        String url = SERVICE_BASE_URI+"user/daftarhunianHdrs";
	HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
	ResponseEntity<modelo.DaftarhunianHdr[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, modelo.DaftarhunianHdr[].class);
        modelo.DaftarhunianHdr[] hdrList = responseEntity.getBody();
//	hdrs = new ArrayDataModel<modelo.DaftarhunianHdr>(hdrList);
//	return hdrs;
        List<modelo.DaftarhunianHdr> list = Arrays.asList(hdrList);
        
        return list;
    }

    //getters and setters
    public String getSERVICE_BASE_URI() {
        return SERVICE_BASE_URI;
    }

    public void setSERVICE_BASE_URI(String SERVICE_BASE_URI) {
        this.SERVICE_BASE_URI = SERVICE_BASE_URI;
    }

    public DataModel<DaftarhunianHdr> getHdrs() {
        return hdrs;
    }

    public void setHdrs(DataModel<DaftarhunianHdr> hdrs) {
        this.hdrs = hdrs;
    }
    //end getters and setters
    
}
