package com.codechallenge.callapp.service;

import com.codechallenge.callapp.configuration.AppConfig;
import com.codechallenge.callapp.data.CallLog;
import com.codechallenge.callapp.dto.CallLogTemplateData;
import com.codechallenge.callapp.dto.Location;
import com.codechallenge.callapp.dto.TransferTypeTemplateData;
import com.codechallenge.callapp.repository.CallLogRepository;
import com.openhtmltopdf.outputdevice.helper.BaseRendererBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PDFFileGenerator implements FileGenerator {

    @Value("${calllog.template.file.path}")
    private String callLogTemplateFilePath;

    @Value("${config.template.file.path}")
    private String configTemplateFilePath;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private CallLogRepository callLogRepository;

    @Autowired
    private AppConfig appConfig;


    @Override
    public String getType() {
        return "PDF";
    }

    @Override
    public byte[] generateFile() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        StringWriter stringWriter = new StringWriter();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] result;
        File file = new File(callLogTemplateFilePath);

        try (InputStream inputStream = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            Template template = new Template("Call_details", content, configuration);

            Model model =  generateCallLogModel();
            template.process(model.asMap(), stringWriter);

            // Convert to PDF
            builder.useFastMode();
            builder.useDefaultPageSize(210, 297, BaseRendererBuilder.PageSizeUnits.MM);
            builder.defaultTextDirection(BaseRendererBuilder.TextDirection.LTR);

            builder.withHtmlContent(stringWriter.toString(), null);
            builder.toStream(outputStream);
            builder.run();
            result = outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Cannot generate call details statement. Error : {}", ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
        return result;
    }

    private Model generateCallLogModel() throws Exception {
        Model model = new ConcurrentModel();
        List<CallLog> callLogs = callLogRepository.findAll();

        List<CallLogTemplateData> tmlData = callLogs.stream()
                        .map(c -> {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
                            CallLogTemplateData cd = new CallLogTemplateData();
                            cd.setTransferTypeName(c.getTransferTypeName());
                            cd.setCreatedDateTime(dtf.format(c.getCreatedDateTime()));
                            cd.setLocationName(c.getTransactionLocation().getName());
                            cd.setLocationNumber(c.getTransactionLocation().getNumber());
                            cd.setLocationEnabled(c.getTransactionLocation().getEnabled() ? "Enabled" : "Not Enabled");
                            cd.setLocationWeight(String.valueOf(c.getTransactionLocation().getWeight()));
                            return cd;
                        }).collect(Collectors.toList());

        model.addAttribute("call_list", tmlData);
        return model;
    }

    @Override
    public byte[] getConfigs() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        StringWriter stringWriter = new StringWriter();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] result;
        File file = new File(configTemplateFilePath);

        try (InputStream inputStream = new FileInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            Template template = new Template("Call_details", content, configuration);

            Model model =  generateConfigModel();
            template.process(model.asMap(), stringWriter);

            // Convert to PDF
            builder.useFastMode();
            builder.useDefaultPageSize(210, 297, BaseRendererBuilder.PageSizeUnits.MM);
            builder.defaultTextDirection(BaseRendererBuilder.TextDirection.LTR);

            builder.withHtmlContent(stringWriter.toString(), null);
            builder.toStream(outputStream);
            builder.run();
            result = outputStream.toByteArray();

        } catch (Exception e) {
            log.error("Cannot generate call details statement. Error : {}", ExceptionUtils.getStackTrace(e));
            throw new Exception(e);
        }
        return result;
    }

    private Model generateConfigModel() throws Exception {
        Model model = new ConcurrentModel();
        Map<String, List<Location>> locationMap = appConfig.getLocations();
        List<TransferTypeTemplateData> config_list = new ArrayList<>();

        Iterator itr = locationMap.keySet().iterator();
        while(itr.hasNext()) {
            String key = (String) itr.next();
            List<Location> locations = locationMap.get(key);
            for (Location loc : locations) {
                TransferTypeTemplateData ttd = new TransferTypeTemplateData();
                ttd.setTransferTypeName(key);
                ttd.setLocationName(loc.getName());
                ttd.setLocationNumber(loc.getNumber());
                ttd.setLocationEnabled(loc.isEnabled() ? "Enabled" : "Not Enabled");
                ttd.setLocationWeight(String.valueOf(loc.getWeight()));
                config_list.add(ttd);
            }
        }

        model.addAttribute("config_list", config_list);
        return model;
    }
}
