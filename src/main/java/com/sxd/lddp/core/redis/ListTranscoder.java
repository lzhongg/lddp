package com.sxd.lddp.core.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j;

/**
 * Case 1. Jedis does not support cache the Object directly, the Objects needed
 * to be serialized and de-serialized
 * 
 * Case 2. coming very soon...
 */
@Log4j
public class ListTranscoder<M extends Serializable> extends SerializeTranscoder
{

    @SuppressWarnings("unchecked")
    public List<M> deserialize(byte[] in)
    {
        List<M> list = new ArrayList<M>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try
        {
            if (in != null)
            {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                
                while (true)
                {
                    try{
                        M m = (M) is.readObject();
                        list.add(m);
                    }catch(EOFException ee){
                        break;
                    }
                }
                is.close();
                bis.close();
            }
        }catch(EOFException e){
            e.printStackTrace();
        }
        catch (IOException e)
        {
            log.error(String.format(
                    "Caught IOException decoding %d bytes of data",
                    in == null ? 0 : in.length)
                    + e);
        }
        catch (ClassNotFoundException e)
        {
            log.error(String.format("Caught CNFE decoding %d bytes of data",
                    in == null ? 0 : in.length) + e);
        }
        finally
        {
            close(is);
            close(bis);
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public byte[] serialize(Object value)
    {
        if (value == null)
            throw new NullPointerException("Can't serialize null");

        List<M> values = (List<M>) value;

        byte[] results = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;

        try
        {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (M m : values)
            {
                os.writeObject(m);
            }

            // os.writeObject(null);
            os.close();
            bos.close();
            results = bos.toByteArray();
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("Non-serializable object", e);
        }
        finally
        {
            close(os);
            close(bos);
        }

        return results;
    }

}
