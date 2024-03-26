package ma.m2t.chaabipay.metier;

import java.util.Date;

public interface IAbstract {
    Long getId();

    void setDeleteDate(Date deleteDate);

    Date getDeleteDate();

    void setUpdateDate(Date updateDate);
}

