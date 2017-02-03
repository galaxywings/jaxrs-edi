package com.wjelogistics.edi.esb;

import org.milyn.edi.unedifact.d93a.D93AInterchangeFactory;
import org.milyn.edi.unedifact.d93a.INVOIC.Invoic;
import org.milyn.edisax.model.internal.Delimiters;
import org.milyn.smooks.edi.unedifact.model.UNEdifactInterchange;
import org.milyn.smooks.edi.unedifact.model.r41.UNB41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactInterchange41;
import org.milyn.smooks.edi.unedifact.model.r41.UNEdifactMessage41;
import org.milyn.smooks.edi.unedifact.model.r41.types.DateTime;
import org.milyn.smooks.edi.unedifact.model.r41.types.Party;
import org.milyn.smooks.edi.unedifact.model.r41.types.SyntaxIdentifier;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class Test {

    @org.junit.Test
    public void test() {
        String src = "step1,step2";
        System.out.println(src.split(",", 2)[0]);
        System.out.println(src.split(",", 2)[1]);
    }

    public static void main(String[] args) throws Exception{
        // Create an instance of the EJC generated factory class... cache this and reuse !!!
        D93AInterchangeFactory factory = D93AInterchangeFactory.getInstance();

        // Deserialize the UN/EDIFACT interchange stream to Java...
        InputStream stream = new FileInputStream("esb/samples/INVOIC.edi");

        /*------------------------------------------
        Read the interchange to Java Objects...
        -------------------------------------------*/
        UNEdifactInterchange interchange;
        try {
            interchange = factory.fromUNEdifact(stream);

            // Need to test which interchange syntax version.  Supports v4.1 at the moment...
            if (interchange instanceof UNEdifactInterchange41) {
                UNEdifactInterchange41 interchange41 = (UNEdifactInterchange41) interchange;

                System.out.println("\nJava Object Values:");
                System.out.println("\tInterchange Sender ID: " + interchange41.getInterchangeHeader().getSender().getId());

                for (UNEdifactMessage41 messageWithControlSegments : interchange41.getMessages()) {
                    // Process the messages...

                    System.out.println("\tMessage Name: " + messageWithControlSegments.getMessageHeader().getMessageIdentifier().getId());

                    Object messageObj = messageWithControlSegments.getMessage();
                    if (messageObj instanceof Invoic) {
                        Invoic invoice = (Invoic) messageObj;
                        System.out.println(invoice.getSegmentGroup2().get(0).getNameAndAddress().getPartyName());

//                        System.out.println("\tParty Name: " + invoice.getSegmentGroup2().get(0).getNADNameAndAddress().getC080PartyName().getE30361PartyName());
                    }
                }
            }
        } finally {
            stream.close();
        }

        /*-----------------------------------
        Write interchange to Stdout...
        ------------------------------------*/
        StringWriter ediOutStream = new StringWriter();

        factory.toUNEdifact(interchange, ediOutStream);

        System.out.println("\n\nSerialized Interchanged:");
        System.out.println("\t" + ediOutStream);

        /* modify in-memory*/
        UNEdifactInterchange41 interchange41 = (UNEdifactInterchange41) interchange;
        List<UNEdifactMessage41> messageList = interchange41.getMessages();
        UNEdifactMessage41 myMessage =  messageList.get(0);
        Invoic myInvoice = (Invoic) myMessage.getMessage();
//        No output validation under the segment level !
//        myInvoice.getSegmentGroup2().get(0).getNADNameAndAddress().getC080PartyName().setE30361PartyName("Lunatech 1234567890 1234567890 1234567890 A");


        /*-----------------------------------
        Write interchange to Stdout...
        ------------------------------------*/
        StringWriter ediOutStream2 = new StringWriter();

        factory.toUNEdifact(interchange, ediOutStream2);

        System.out.println("\n\nModified Serialized Interchange:");
        System.out.println("\t" + ediOutStream2);


        System.out.println("\n\n**** RUN INSIDE YOUR IDE... Set a breakpoint in the example.Main Class... inspect values etc !!\n");


        UNEdifactMessage41 ourMessage = new UNEdifactMessage41();
        UNB41 unb41 = new UNB41();
        Party recipientParty = new Party();
        recipientParty.setId("收件人");
        unb41.setRecipient(recipientParty);
        Party senderParty = new Party();
        senderParty.setId("发件人");
        unb41.setSender(senderParty);
        DateTime messageDate = new DateTime();
        messageDate.setDate("111111");
        messageDate.setTime("1140");
        unb41.setDate(messageDate);
        unb41.setControlRef("control-ref");
        SyntaxIdentifier syntaxIdentifier = new SyntaxIdentifier();
        syntaxIdentifier.setId("UNOA");
        syntaxIdentifier.setVersionNum("2");
        unb41.setSyntaxIdentifier(syntaxIdentifier);
        ourMessage.setInterchangeHeader(unb41);
        Writer writer = new StringWriter();
        Delimiters delimiters = new Delimiters();
        delimiters.setSegment("'");
        delimiters.setField("+");
        delimiters.setComponent(":");
        unb41.write(writer, delimiters);
        System.out.println(writer.toString());
    }

}
