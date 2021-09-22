package mx.silverwolfdev.sepomeximporter;

import lombok.extern.log4j.Log4j2;
import mx.silverwolfdev.sepomeximporter.model.SepomexXmlEntry;
import mx.silverwolfdev.sepomeximporter.model.SepomexXmlFile;
import mx.silverwolfdev.sepomeximporter.subscriber.TraceSubscriber;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@Log4j2
public class SepomexImporter {
    final private SubmissionPublisher<SepomexXmlEntry> publisher = new SubmissionPublisher<>();
    final private JAXBContext jaxbContext;
    final private Unmarshaller jaxbUnmarshaller;

    public static void main(String[] args) {
        assert args != null;
        if (args.length < 1) {
            System.out.println("No arguments found, xml source file required");
        }
        SepomexImporter importer = null;
        try {
            importer = new SepomexImporter();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        importer.importFiles(Arrays.asList(args));
    }

    public SepomexImporter() throws JAXBException {
        jaxbContext = JAXBContext.newInstance(SepomexXmlFile.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        if (log.isTraceEnabled()) {
            subscribe(new TraceSubscriber());
        }
    }

    public List<String> importFiles(List<String> files) {
        final var failedFiles = new ArrayList<String>();
        files.forEach(fileName -> {
            try {
                importFile(fileName);
            } catch (JAXBException e) {
                log.error("Error parsing file {}, ignoring file", fileName);
                failedFiles.add(fileName);
            } catch (IOException e) {
                log.error("Invalid file {} found, ignoring file", fileName);
                failedFiles.add(fileName);
            }
        });
        publisher.close();
        return failedFiles;
    }

    public void importFile(final String fileName) throws JAXBException, IOException {
        final var file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File is not a usable file");
        }
        final var xmlFile = loadXMLFile(fileName);
        xmlFile.getEntries().forEach(publisher::submit);
    }

    public void subscribe(Flow.Subscriber<SepomexXmlEntry> subscriber) {
        publisher.subscribe(subscriber);
    }

    private SepomexXmlFile loadXMLFile(String fileName) throws JAXBException {
        final var xmlFile = new File(fileName);
        return (SepomexXmlFile) jaxbUnmarshaller.unmarshal(xmlFile);
    }
}