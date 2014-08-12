package com.eits.freemr.configuration.rest.api.amqp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import lombok.Setter;

import org.axonframework.eventhandling.amqp.EventPublicationFailedException;
import org.axonframework.eventhandling.io.EventMessageReader;
import org.axonframework.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

public class XStreamConverter extends AbstractMessageConverter {

    @Setter
    private Serializer serializer;

    @Override
    public Message createMessage(Object object, MessageProperties messageProperties) {
        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        MessageProperties messageProperties = message.getMessageProperties();
        if (messageProperties == null) {
            throw new MessageConversionException("Cannot decode a message with no properties!");
        }

        byte[] body = message.getBody();

        try {
            EventMessageReader in = new EventMessageReader(new DataInputStream(new ByteArrayInputStream(body)), serializer);
            return in.readEventMessage().getPayload();
        } catch (IOException e) {
            throw new EventPublicationFailedException("Failed to deserialize an EventMessage", e);
        }
    }
}