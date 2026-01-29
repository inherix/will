package com.deft.will.configurations;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemplateRenderfactory{
    private final MustacheRender mustacheRender;
    private final ThymleafRender thymleafRender;
    private final String engine;

    public TemplateRenderfactory(@Qualifier("mustache")  MustacheRender mustacheRender, @Qualifier("thymeleaf")ThymleafRender thymleafRender,@Value("${template.engine}") String engine){
        this.mustacheRender=mustacheRender;
        this.thymleafRender=thymleafRender;
        this.engine=engine;
    }
    public TemplateRender getRenderer(){
        return "thymeleaf".equalsIgnoreCase(engine)?
                thymleafRender:mustacheRender;
    }
}
