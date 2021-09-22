package mx.silverwolfdev.sepomeximporter.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class SepomexXmlEntry implements Serializable {
    @XmlElement(name = "d_codigo", namespace="NewDataSet")
    private String postcode;

    @XmlElement(name = "d_asenta", namespace="NewDataSet")
    private String settlementName;

    @XmlElement(name = "d_tipo_asenta", namespace="NewDataSet")
    private String settlementType;

    @XmlElement(name = "D_mnpio", namespace="NewDataSet")
    private String municipality;

    @XmlElement(name = "d_estado", namespace="NewDataSet")
    private String state;

    @XmlElement(name = "d_ciudad", namespace="NewDataSet")
    private String city;

    @XmlElement(name = "d_CP", namespace="NewDataSet")
    private String postalAdminPostalCode;

    @XmlElement(name = "c_estado", namespace="NewDataSet")
    private String stateCode;

    @XmlElement(name = "c_oficina", namespace="NewDataSet")
    private String postalAdminOfficeCode;

    @XmlElement(name = "c_tipo_asenta", namespace="NewDataSet")
    private String settlementTypeCode;

    @XmlElement(name = "c_mnpio", namespace="NewDataSet")
    private String municipalityCode;

    @XmlElement(name = "id_asenta_cpcons", namespace="NewDataSet")
    private String settlementUniqueCode;

    @XmlElement(name = "d_zona", namespace="NewDataSet")
    private String settlementZone;

    @XmlElement(name = "c_cve_ciudad", namespace="NewDataSet")
    private String cityCode;
}
