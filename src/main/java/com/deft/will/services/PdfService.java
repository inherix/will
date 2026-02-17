package com.deft.will.services;


import com.deft.will.configurations.PdfGenerator;
import com.deft.will.configurations.TemplateRenderfactory;
import com.deft.will.models.PdfRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PdfService {
    @Value("${template.engine}")
    private String engine;
     S3AsyncClient s3AsyncClient;
    private final PdfGenerator pdfGenerator;
    private final String bucket;
    private final TemplateRenderfactory templateRenderfactory;

    public PdfService(@Value("${aws.s3.bucket}") String bucket, S3AsyncClient s3AsyncClient, PdfGenerator pdfGenerator, TemplateRenderfactory templateRenderfactory){
        this.s3AsyncClient=s3AsyncClient;
        this.bucket = bucket;
        this.pdfGenerator=pdfGenerator;
        this.templateRenderfactory=templateRenderfactory;
    }


    public Mono<byte[]> fetchTemplate( String key){
        String pathPrefix = switch (engine.toLowerCase()) {
            case "mustache" -> "mustache/";
            case "thymeleaf" -> "thymeleaf/";
            default -> "";
        };
        String s3Key = pathPrefix + key;
        GetObjectRequest request=GetObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build();
        return Mono.fromFuture(
                s3AsyncClient.getObject(
                        request, AsyncResponseTransformer.toBytes()
                )
        ).map(ResponseBytes::asByteArray);
    }

    public Mono<List<byte[]>> fetchAllTemplates( List<String> keys){

        return Flux.fromIterable(keys)
                .concatMap(this::fetchTemplate)
                .collectList();
    }

    public Mono<byte[]> generatePdf(List<String> keys, PdfRequest data){

        return fetchAllTemplates(keys).map(
                bytesList ->
                        bytesList.stream()
                                .map(b -> new String(b, StandardCharsets.UTF_8))
                                .map(html-> templateRenderfactory.getRenderer().render(html, data))
                                .toList()
        )
                .flatMap(
                        htmlList->Mono.fromCallable(()->
                            pdfGenerator.mergeAndCreatePdf(htmlList)
                        ).subscribeOn(Schedulers.boundedElastic())
                );
    }

    public Mono<Void> uploadPdf(byte[] pdfBytes, String key){
        PutObjectRequest putObjectRequest= PutObjectRequest.builder()
                .bucket(bucket).key(key).contentType("application/pdf").build();

        return Mono.fromFuture(
                s3AsyncClient.putObject(
                        putObjectRequest, AsyncRequestBody.fromBytes(pdfBytes)
                )
        ).then();
    }


    public Mono<String> processWillPdf(List<String> templateKeys, PdfRequest data)
    {
          return generatePdf(templateKeys, data).flatMap(
                  pdfBytes->uploadPdf( pdfBytes, "output/will"+ System.currentTimeMillis()+".pdf")

          ).then(Mono.just("PDF created and uploaded successfully!"));
    }


}
