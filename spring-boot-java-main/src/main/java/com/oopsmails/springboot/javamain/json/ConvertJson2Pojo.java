package com.oopsmails.springboot.javamain.json;

import com.sun.codemodel.JCodeModel;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ConvertJson2Pojo {
    public static String PROJECT_ROOT = "/home/albert/Documents/github/springboot-dev-repo/spring-boot-java-main";
    public static String GEN_SOURCE_DIR = "/src/main/java";
    public static String GEN_PACKAGE_NAME = "com.oopsmails.springboot.javamain.json.gen";
    public static String JSON_FILE_DIR = "/src/main/resources/jsonrepo/";
    public static String JSON_OBJECT_FILE = "jsonObject01.json";
    public static String JSON_SCHEMA_FILE = "jsonSchema0101.json";

    public static void main(String[] args) {
//        File inputJson = new File("." + File.separator + "input.json");
        File inputJson = new File(PROJECT_ROOT + JSON_FILE_DIR + JSON_OBJECT_FILE); // JSON_OBJECT_FILE, JSON_SCHEMA_FILE
//        File outputPojoDirectory = new File("." + File.separator + "convertedPojo");
        File outputPojoDirectory = new File(PROJECT_ROOT + GEN_SOURCE_DIR);
        outputPojoDirectory.mkdirs();
        try {
            new ConvertJson2Pojo().convert2JSON(inputJson.toURI().toURL(), outputPojoDirectory, GEN_PACKAGE_NAME, inputJson.getName().replace(".json", ""));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Encountered issue while converting to pojo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void convert2JSON(URL inputJson, File outputPojoDirectory, String packageName, String className) throws IOException {
        JCodeModel codeModel = new JCodeModel();
        URL source = inputJson;
        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() { // set config option by overriding method
                return true;
            }

            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(codeModel, className, packageName, source);
        codeModel.build(outputPojoDirectory);
    }
}
