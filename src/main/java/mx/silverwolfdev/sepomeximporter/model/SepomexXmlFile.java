package mx.silverwolfdev.sepomeximporter.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "NewDataSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class SepomexXmlFile {
    @XmlElement(name = "table", namespace="NewDataSet")
    private List<SepomexXmlEntry> entries;
}
