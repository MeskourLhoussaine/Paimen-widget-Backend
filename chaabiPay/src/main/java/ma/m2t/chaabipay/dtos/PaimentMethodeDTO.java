package ma.m2t.chaabipay.dtos;

import lombok.Data;

@Data
public class PaimentMethodeDTO {
    private Long id;
    private String name;
    private String description;
    private String iconUrl;
}
