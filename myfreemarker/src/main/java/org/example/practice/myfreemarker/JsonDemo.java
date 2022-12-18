package org.example.practice.myfreemarker;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDemo {

    public static void main(String[] args) throws IOException, TemplateException {

        Configuration cfg = new Configuration(new Version("2.3.31"));

        cfg.setClassForTemplateLoading(FreeMarkerConsoleEx.class, "/");
        cfg.setDefaultEncoding("UTF-8");

        List<Grad> gradList = Grad.getList();

        String source = """
                {
                  "value":[
                  <#list gradList as gra>
                      <#if  gra.id == "999990006">
                          {
                          "key":"父级${gra.code}",
                          "value":"${gra.value}"
                          }
                      <#elseif gra.id?starts_with("999990006")>
                          {
                          "key":"下属分级${gra.code}_${gradMap[gra.id?substring("999990006"?length)]}",
                          "value":"${gra.value}"
                          }
                      </#if>
                      <#sep>, </#sep>
                  </#list>
                  ]
                  }           
                """;
        String source1 = """
                {
                  "value":[
                  <#list gradList as gra>
                      <#if  gra.id == "999990006">
                          {
                          "key":"父级${gra.code}",
                          "value":""
                          }
                      <#elseif gra.id?starts_with("999990006")>
                          {
                          "key":"下属分级${gra.code}_${gradMap[gra.id?substring("999990006"?length)]}",
                          "value":"${gra.value}"
                          }
                      </#if>
                      <#sep>, </#sep>
                  </#list>
                  ]
                  }           
                """;
        Template template = new Template("test.ftl", "{\"cofig\":["+source+","+source1+"]}", cfg);
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("msg", "Today is a beautiful day");
        templateData.put("gradList", gradList);
        Map<String, Object> map = new HashMap<>();
        map.put("001", "A");
        map.put("002", "B");

        templateData.put("gradMap", map);
        try (StringWriter out = new StringWriter()) {

            template.process(templateData, out);
            String jsonStr = out.getBuffer().toString();
            System.out.println(jsonStr);
            ObjectMapper mapper = new ObjectMapper();
            Object map1 = mapper.readValue(jsonStr,Map.class);
            System.out.println(map1);
            out.flush();
        }
    }
}
