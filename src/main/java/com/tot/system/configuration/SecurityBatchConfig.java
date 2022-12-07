package com.tot.system.configuration;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.tot.system.configuration.item_processor.SecurityItemProcessor;
import com.tot.system.configuration.item_writer.SecurityItemWriter;
import com.tot.system.model.Security;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class SecurityBatchConfig {
    public final SecurityItemProcessor securityItemProcessor;

    public final SecurityItemWriter securityItemWriter;

    public final StepBuilderFactory stepFactory;

    @Autowired
    public SecurityBatchConfig(SecurityItemProcessor securityItemProcessor,
                               SecurityItemWriter securityItemWriter,
                               StepBuilderFactory stepFactory) {
        this.securityItemProcessor = securityItemProcessor;
        this.securityItemWriter = securityItemWriter;
        this.stepFactory = stepFactory;
    }

    @Bean
    public Resource[] securityGetResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("/import_data/securities_*.xml");
    }

    @Bean
    public StaxEventItemReader<Security> securityReader() {
        StaxEventItemReader<Security> reader = new StaxEventItemReader<>();
        reader.setFragmentRootElementName("row");
        Map<String, Class<Security>> aliases = new HashMap<>();
        aliases.put("row", Security.class);
        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.setConverters(new SecurityConverter());
        marshaller.setNameCoder(new NoNameCoder());
        reader.setUnmarshaller(marshaller);
        return reader;
    }

    @Bean
    public MultiResourceItemReader<Security> securityMultiReader() throws IOException {
        MultiResourceItemReader<Security> multiReader = new MultiResourceItemReader<>();
        multiReader.setResources(securityGetResources());
        multiReader.setDelegate(securityReader());
        return multiReader;
    }

    @Bean
    public Step securityStep() throws IOException {
        return stepFactory
                .get("securityStep")
                .<Security, Security> chunk(100)
                .reader(securityMultiReader())
                .processor(securityItemProcessor)
                .writer(securityItemWriter)
                .build();
    }

    private static class SecurityConverter implements Converter {
        @Override
        public boolean canConvert(Class type) {
            return type.equals(Security.class);
        }

        @Override
        public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Long id = Long.parseLong(reader.getAttribute("id"));
            String secId = reader.getAttribute("secid");
            String regNumber = reader.getAttribute("regnumber");
            String name = reader.getAttribute("name");
            String emitentTitle = reader.getAttribute("emitent_title");
            Security security = new Security();
            security.setId(id);
            security.setSecId(secId);
            security.setRegNumber(regNumber);
            security.setName(name);
            security.setEmitentTitle(emitentTitle);
            return security;
        }
    }
}
