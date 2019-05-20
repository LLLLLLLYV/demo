package com.example.fanshe;


import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘艳伟
 * @since 2019-05-07
 */

public class EibCopyReg {

    @SopAnnotation(color="著作权人")
    private String authorNationality;

    @SopAnnotation(color="分类号")
    private String catnum;

    @SopAnnotation(color="全称")
    private String fullname;

    @SopAnnotation(color="企业Id")
    private Long incubateCompanyId;

    @SopAnnotation(color="企业名称")
    private String incubateCompanyName;

    @SopAnnotation(color="孵化器Id")
    private Long incubateId;


    public EibCopyReg(String authorNationality, String catnum, String fullname, Long incubateCompanyId, String incubateCompanyName, Long incubateId) {
        this.authorNationality = authorNationality;
        this.catnum = catnum;
        this.fullname = fullname;
        this.incubateCompanyId = incubateCompanyId;
        this.incubateCompanyName = incubateCompanyName;
        this.incubateId = incubateId;
    }
}
