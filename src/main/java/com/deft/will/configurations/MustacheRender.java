package com.deft.will.configurations;

import com.deft.will.models.PdfRequest;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.stereotype.Component;


import java.io.StringReader;
import java.io.StringWriter;

@Component("mustache")

public class MustacheRender implements  TemplateRender {


    private static final MustacheFactory MF =new DefaultMustacheFactory();

    @Override
    public String render(String template, PdfRequest data){
        Mustache mustache=MF.compile(new StringReader(template), "template");
        StringWriter sw=new StringWriter();
        mustache.execute(sw, data);
        return sw.toString();

    }
}
