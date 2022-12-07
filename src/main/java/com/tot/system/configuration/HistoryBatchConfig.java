package com.tot.system.configuration;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.tot.system.configuration.item_writer.HistoryItemWriter;
import com.tot.system.configuration.item_processor.HistoryItemProcessor;
import com.tot.system.model.History;
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
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
@EnableBatchProcessing
public class HistoryBatchConfig {
    public final HistoryItemProcessor historyItemProcessor;

    public final HistoryItemWriter historyItemWriter;

    public final StepBuilderFactory stepFactory;

    @Autowired
    public HistoryBatchConfig(HistoryItemProcessor historyItemProcessor,
                              HistoryItemWriter historyItemWriter,
                              StepBuilderFactory stepFactory) {
        this.historyItemProcessor = historyItemProcessor;
        this.historyItemWriter = historyItemWriter;
        this.stepFactory = stepFactory;
    }

    @Bean
    public Resource[] historyGetResources() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return resolver.getResources("/import_data/history_*.xml");
    }
    @Bean
    public StaxEventItemReader<History> historyItemReader() {
        StaxEventItemReader<History> reader = new StaxEventItemReader<>();
        reader.setFragmentRootElementName("row");
        Map<String, Class<History>> aliases = new HashMap<>();
        aliases.put("row", History.class);
        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAliases(aliases);
        marshaller.setConverters(new ObjectHistoryConverter());
        marshaller.setNameCoder(new NoNameCoder());
        reader.setUnmarshaller(marshaller);
        return reader;
    }

    @Bean
    public MultiResourceItemReader<History> historyMultiReader() throws IOException {
        MultiResourceItemReader<History> multiReader = new MultiResourceItemReader<>();
        multiReader.setResources(historyGetResources());
        multiReader.setDelegate(historyItemReader());
        return multiReader;
    }

    @Bean
    public Step historyStep() throws IOException {
        return stepFactory
                .get("historyStep")
                .<History, History> chunk(100)
                .reader(historyMultiReader())
                .processor(historyItemProcessor)
                .writer(historyItemWriter)
                .faultTolerant()
                .skipLimit(10)
                .skip(UnmarshallingFailureException.class)
                .build();
    }

    private static class ObjectHistoryConverter implements Converter {

        private static final AtomicLong id = new AtomicLong();

        @Override
        public boolean canConvert(Class type) {
            return type.equals(History.class);
        }

        @Override
        public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            String secId = reader.getAttribute("SECID");
            Date tradeDate = Date.valueOf(reader.getAttribute("TRADEDATE"));
            Integer numTrades = Integer.parseInt(reader.getAttribute("NUMTRADES"));
            String open = reader.getAttribute("OPEN");
            String close = reader.getAttribute("CLOSE");
            History history = new History();
            history.setId(id.incrementAndGet());
            Security security = new Security();
            security.setSecId(secId);
            history.setSecurity(security);
            history.setTradeDate(tradeDate);
            history.setNumTrades(numTrades);
            if (!open.isEmpty()) {
                history.setOpen(Double.parseDouble(open));
            } else {
                history.setOpen(0d);
            }
            if (!close.isEmpty()) {
                history.setClose(Double.parseDouble(close));
            } else {
                history.setClose(0d);
            }
            return history;
        }
    }
}

