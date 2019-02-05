package com.acc.awssns;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private Environment environment;

    public int sendMail() {

        //Sender,Receiver and Carbon Copy  informations

        String TO="sendingTO@example.com";
        String FROM="sendingFROM@example.com";
        String CC="carboncopy@example.com";

        //mail subject,mail body and signature  informations

        String SUBJECT="SUBJECT OF MAIL";
        String MAILBODY="Hello ! I am coming via AWS SNS";
        final String TEXTBODY = "This email was sent through Amazon SES "
                + "using the AWS SDK for Java.";

        //mail send response

        SendEmailResult sendEmailResult = null;


        //AWS Account Credentials

        BasicAWSCredentials credentials = new BasicAWSCredentials(environment.getProperty("accessKey"),environment.getProperty("secretKey") );

        try {

            //Define a client who want to use email service with mail attributes

            AmazonSimpleEmailService client =
  AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))                           // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_WEST_2).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(TO).withCcAddresses(CC))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(MAILBODY))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(TEXTBODY)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(FROM);

                    // Comment or remove the next line if you are not using a
                    // configuration set
                    //   .withConfigurationSetName(CONFIGSET)

             sendEmailResult = client.sendEmail(request);
        }
        catch (Exception e){
            e.printStackTrace();
        }




        return sendEmailResult.getSdkHttpMetadata().getHttpStatusCode();

    }
}
