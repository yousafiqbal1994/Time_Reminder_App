package com.example.rafay.labreminder;

import java.nio.channels.Channel;

/**
 * Created by Rafay on 4/9/2016.
 */
public interface callback {

    void serviceSuccess(int value);

    void serviceFailure (Exception e);
}
