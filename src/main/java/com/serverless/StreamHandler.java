package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;

public class StreamHandler implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("US-ASCII"))));
        try {
            HashMap event = gson.fromJson(reader, HashMap.class);
            logger.log("STREAM TYPE: " + inputStream.getClass().toString());
            logger.log("EVENT TYPE: " + event.getClass().toString());

            //? enable this if you want to see the full event
//            logger.log("\n EVENT \n");
//            logger.log(gson.toJson(event));

            //? create Api Gateway expected response object
            Response responseBody = new Response("Go Serverless v1.x! Your function executed successfully!", event);
            ApiGatewayResponse apigRes = ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(responseBody)
                    .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                    .build();

            writer.write(gson.toJson(apigRes));
            if (writer.checkError()) {
                logger.log("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.log(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
}
