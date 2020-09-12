package com.springbootslsdemo;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.internal.servlet.AwsLambdaServletContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SpringLambdaHandler implements RequestStreamHandler {

    private AwsLambdaServletContainerHandler handler;
    public SpringLambdaHandler() throws ContainerInitializationException {
        handler = new SpringBootProxyHandlerBuilder()
                .springBootApplication(Application.class)
                .defaultProxy()
                .asyncInit()
                .buildAndInitialize();
    }
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {

        handler.proxyStream(inputStream, outputStream, context);
    }
}