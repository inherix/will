package com.deft.will.configurations;

import com.deft.will.models.PdfRequest;

public interface TemplateRender {
    public String render(String template, PdfRequest data);
}

