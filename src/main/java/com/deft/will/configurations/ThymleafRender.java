package com.deft.will.configurations;

import com.deft.will.models.PdfRequest;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Component("thymeleaf")
public class ThymleafRender  implements  TemplateRender{
    private final TemplateEngine templateEngine;

    public ThymleafRender(TemplateEngine templateEngine){
        this.templateEngine=templateEngine;
        this.templateEngine.setTemplateResolver(stringTemplateResolver());
    }

    private ITemplateResolver stringTemplateResolver(){
        StringTemplateResolver resolver=new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        return resolver;
    }

    @Override
    public String render(String template, PdfRequest data){
        Context context = new Context();
        context.setVariable("providerName", data.getProviderName());
        context.setVariable("providerAddress", data.getProviderAddress());
        context.setVariable("providerContact", data.getProviderContact());
        context.setVariable("beneficiaries", data.getBeneficiaries());
        context.setVariable("specialInstructions", data.getSpecialInstructions());
        return templateEngine.process(template, context);
    }
}
